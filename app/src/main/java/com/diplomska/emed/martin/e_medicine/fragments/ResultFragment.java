package com.diplomska.emed.martin.e_medicine.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.adapter.PillsAdapter;
import com.diplomska.emed.martin.e_medicine.interfaces.onPillIdTaskHandler;
import com.diplomska.emed.martin.e_medicine.models.PillModel;
import com.diplomska.emed.martin.e_medicine.task.PillIdTask;

import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Martin on 06-Jul-15.
 */
public class ResultFragment extends Fragment implements onPillIdTaskHandler {

    private RecyclerView recPills;
    private LinearLayoutManager manager;
    private PillsAdapter adapter;

    private ProgressDialog pDialog;
    private TextView txtNoRes;

    public ResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pill_identify_result_fragment, container, false);

        txtNoRes = (TextView) v.findViewById(R.id.txtNoResult);
        recPills = (RecyclerView) v.findViewById(R.id.lstPills);
        recPills.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        recPills.setLayoutManager(manager);

        Bundle args=getArguments();
        String url=args.getString("url");

        if (url != null) {
            try {
                new PillIdTask(this).execute(url);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return v;
    }

    @Override
    public void onPillIdStarted() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getString(R.string.loading_db));
        pDialog.show();
    }

    @Override
    public void onPillIdResult(List<PillModel> pills) {
        adapter = new PillsAdapter(pills);
        recPills.setAdapter(adapter);
        pDialog.dismiss();
    }

    @Override
    public void onPillIdNoResults() {
        recPills.setVisibility(View.GONE);
        txtNoRes.setVisibility(View.VISIBLE);
        pDialog.dismiss();
    }

    @Override
    public void onPillIdError(String errMsg) {
        Toast.makeText(getActivity().getApplicationContext(), errMsg, Toast.LENGTH_LONG).show();
        pDialog.dismiss();
    }
}
