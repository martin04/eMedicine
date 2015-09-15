package com.diplomska.emed.martin.e_medicine.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.diplomska.emed.martin.e_medicine.R;
import com.diplomska.emed.martin.e_medicine.holders.DrugNameViewHolder;
import com.diplomska.emed.martin.e_medicine.interfaces.onAlarmCreated;
import com.diplomska.emed.martin.e_medicine.models.Drug;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Martin on 26-Jun-15.
 */
public class DrugNameAdapter extends RecyclerView.Adapter<DrugNameViewHolder> implements onAlarmCreated {


    List<Drug> drugsDataSet;
    private int startHour;
    private int startMinutes;

    public DrugNameAdapter(List<Drug> drugs) {
        drugsDataSet = drugs;
    }

    @Override
    public DrugNameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new DrugNameViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(DrugNameViewHolder holder, int position) {
        Drug d = drugsDataSet.get(position);
        String drugName = "";

        //holder.genericName.setText(d.getGeneric_name());
        holder.genericName.setVisibility(View.GONE);
        if (TextUtils.isEmpty(d.getGeneric_name())) {
            drugName = d.getLatin_name();
            String[] names = drugName.split(",");
            holder.latinName.setText(names[0]);
            holder.genericName.setText(d.getLatin_name());
        } else {
            drugName = d.getGeneric_name();
            String[] names = drugName.split(",");
            holder.latinName.setText(names[0]);
            holder.genericName.setText(d.getGeneric_name());
        }

        holder.drugCode.setText(d.getCode());
    }

    @Override
    public int getItemCount() {
        return drugsDataSet.size();
    }

    public void sort(Comparator<Drug> comparator) {
        Collections.sort(drugsDataSet, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public void createAlarm(Context ctx, String name) {

        final String drugName = name;
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.set_reminder_layout);
        final EditText editTimesADay = (EditText) dialog.findViewById(R.id.editTimes);
        final EditText editNumberOfPills = (EditText) dialog.findViewById(R.id.editNumberPills);
        final TimePicker startTime = (TimePicker) dialog.findViewById(R.id.startTimePicker);
        startTime.setIs24HourView(true);
        startTime.setVisibility(View.GONE);
        final CheckBox cbStartTime = (CheckBox) dialog.findViewById(R.id.checkTimePicker);
        Button btnSet = (Button) dialog.findViewById(R.id.btnSet);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        startHour = -1;
        startMinutes = -1;

        startTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(view.getVisibility() == View.VISIBLE) {
                    startHour = hourOfDay;
                    startMinutes = minute;
                }
            }
        });
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTimesADay.getText().toString())) {
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.enter_number_of_times), Toast.LENGTH_LONG).show();
                } else {
                    //VIDI VO SHARED PREFERENCES ZACUVAJ KOLKU APCINJA OD LEKARSTVO I TAKA KE VCITUVAS
                    //DOPOLNITELNO NA PUKANJE NA ALARM DA SE ODZEMA PO EDNO
                    int times = Integer.parseInt(editTimesADay.getText().toString());
                    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);

                    if (startHour != -1) {
                        intent.putExtra(AlarmClock.EXTRA_MESSAGE, drugName + " pill No. 1");
                        intent.putExtra(AlarmClock.EXTRA_HOUR, startHour);
                        intent.putExtra(AlarmClock.EXTRA_MINUTES, startMinutes);
                        v.getContext().startActivity(intent);
                        for (int i = 1; i < times; i++) {
                            int hours = (startHour + (24 / times) * i);
                            if (hours >= 24) {
                                hours -= 24;
                            }
                            intent.putExtra(AlarmClock.EXTRA_MESSAGE, drugName + " pill No." + (i + 1));
                            intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
                            intent.putExtra(AlarmClock.EXTRA_MINUTES, startMinutes);
                            v.getContext().startActivity(intent);
                        }
                    } else {
                        intent.putExtra(AlarmClock.EXTRA_MESSAGE, drugName + " pill No. 1");
                        intent.putExtra(AlarmClock.EXTRA_HOUR, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                        intent.putExtra(AlarmClock.EXTRA_MINUTES, Calendar.getInstance().get(Calendar.MINUTE));
                        v.getContext().startActivity(intent);
                        for (int i = 1; i < times; i++) {
                            int hours = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + (24 / times) * i);
                            if (hours >= 24) {
                                hours -= 24;
                            }
                            intent.putExtra(AlarmClock.EXTRA_MESSAGE, drugName + " pill No." + (i + 1));
                            intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
                            intent.putExtra(AlarmClock.EXTRA_MINUTES, Calendar.getInstance().get(Calendar.MINUTE));
                            v.getContext().startActivity(intent);
                        }
                    }
                    dialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cbStartTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    startTime.setVisibility(View.GONE);
                    startHour = -1;
                    startMinutes = -1;
                } else {
                    startTime.setVisibility(View.VISIBLE);
                }
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}
