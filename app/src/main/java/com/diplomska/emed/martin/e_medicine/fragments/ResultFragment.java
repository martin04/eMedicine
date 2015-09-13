package com.diplomska.emed.martin.e_medicine.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.PillIdActivity;
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

    private ConnectivityManager cm;
    private NetworkInfo ni;

    private boolean dataFilled;

    public ResultFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pill_identify_result_fragment, container, false);

        setHasOptionsMenu(true);

        txtNoRes = (TextView) v.findViewById(R.id.txtNoResult);
        recPills = (RecyclerView) v.findViewById(R.id.lstPills);
        recPills.setHasFixedSize(true);
        manager = new LinearLayoutManager(getActivity());
        recPills.setLayoutManager(manager);

        Bundle args = getArguments();
        String url = args.getString("url");

        dataFilled = false;

        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean isConnected = ni != null && ni.isConnectedOrConnecting();
        if (!isConnected) {
            dataFilled = false;
            connectionDialog();
        } else {
            if (url != null) {
                try {
                    dataFilled = true;
                    new PillIdTask(this).execute(url);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getFragmentManager().popBackStack();
        return true;
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

    private void connectionDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.connection_title)
                .setMessage(R.string.connection_message)
                .setNegativeButton(R.string.connection_cancel_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getFragmentManager().popBackStack();
                        //getFragmentManager().beginTransaction().replace(android.R.id.content,new OptionsFragment()).commit();
                    }
                })
                .setPositiveButton(R.string.connection_ok_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                        //startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        startActivity(new Intent(Settings.ACTION_SETTINGS));

                    }
                })
                .create()
                .show();
    }
}
