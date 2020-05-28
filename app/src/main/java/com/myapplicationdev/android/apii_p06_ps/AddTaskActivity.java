package com.myapplicationdev.android.apii_p06_ps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    Button btnInsert, btnCancel;
    EditText etName, etDesc, etTime;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        btnCancel = findViewById(R.id.btnCancel);
        btnInsert = findViewById(R.id.btnInsert);
        etTime = findViewById(R.id.etTime);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper db = new DBHelper(AddTaskActivity.this);
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                int time = Integer.parseInt(etTime.getText().toString());
                // Insert a task
                long inserted_id = db.insertTask(name, desc);
                db.close();

                if (inserted_id != -1){
                    Toast.makeText(AddTaskActivity.this, "Data inserted successfully",
                            Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                finish();

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, time);

                Intent intent = new Intent(AddTaskActivity.this,
                        NotificationReceiver.class);
                intent.putExtra("task", name);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        AddTaskActivity.this, reqCode,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
