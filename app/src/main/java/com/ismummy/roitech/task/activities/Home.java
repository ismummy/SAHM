package com.ismummy.roitech.task.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ismummy.roitech.task.R;
import com.ismummy.roitech.task.adapters.TaskAdapter;
import com.ismummy.roitech.task.helpers.DBHandler;
import com.ismummy.roitech.task.models.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {

    private ArrayList<Task> arrayList;
    private TaskAdapter adapter;
    private RecyclerView recyclerView;
    private DBHandler db;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        db = new DBHandler(this);
        initRecycler();

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    private void initRecycler() {
        TextView init = (TextView) findViewById(R.id.init);
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = s.format(new Date());
        arrayList = db.getTask(date);
        if (arrayList.isEmpty())
            init.setVisibility(View.VISIBLE);
        else
            init.setVisibility(View.GONE);

        adapter = new TaskAdapter(arrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void addTask() {
        final Dialog d = new Dialog(this);
        d.setTitle("Add New Task");
        d.setContentView(R.layout.today_task);
        d.setCancelable(true);

        final EditText title = (EditText) d.findViewById(R.id.title);
        final EditText description = (EditText) d.findViewById(R.id.description);
        final TimePicker time = (TimePicker) d.findViewById(R.id.start);

        Button send = (Button) d.findViewById(R.id.add_task);
        final ProgressBar bar = (ProgressBar) d.findViewById(R.id.bar);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bar.isShown()) {
                    if (!title.getText().toString().isEmpty()) {
                        Task task = new Task();
                        task.setDate(date);
                        task.setTitle(title.getText().toString());
                        task.setDescription(description.getText().toString());
                        task.setStart(time.getCurrentHour() + ":" + time.getCurrentMinute());
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, time.getCurrentHour());
                        calendar.set(Calendar.MINUTE, time.getCurrentMinute());
                        calendar.set(Calendar.SECOND, 1);

                        db.addTask(task);
                        title.setText("");
                        description.setText("");
                        Intent intent = new Intent(Home.this, Alarm.class);
                        intent.putExtra("task", task);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Home.this, 0, intent, 0);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        arrayList.clear();
                        arrayList.addAll(db.getTask(date));
                        adapter.notifyDataSetChanged();
                        d.dismiss();
                    }
                }
            }
        });
        d.setCanceledOnTouchOutside(false);
        d.show();
    }

}
