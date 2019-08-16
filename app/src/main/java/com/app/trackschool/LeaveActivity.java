package com.app.trackschool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class LeaveActivity extends AppCompatActivity {
    private EditText reason, from, to;
    private Button updateBtn;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        Toolbar toolbar = findViewById(R.id.calendar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Leave Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        reason = findViewById(R.id.reason_holiday);
        from = findViewById(R.id.holiday_from);
        to = findViewById(R.id.holiday_upto);
        updateBtn = findViewById(R.id.holiday_update_btn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reason_holiday = reason.getText().toString();
                String holiday_from = from.getText().toString();
                String holiday_to = to.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("reason", reason_holiday);
                hashMap.put("from", holiday_from);
                hashMap.put("to", holiday_to);

                if (!TextUtils.isEmpty(reason_holiday) || !TextUtils.isEmpty(holiday_from) ||
                        !TextUtils.isEmpty(holiday_to) ){
                    db.collection("Holiday").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LeaveActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LeaveActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }else {
                    Toast.makeText(LeaveActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
