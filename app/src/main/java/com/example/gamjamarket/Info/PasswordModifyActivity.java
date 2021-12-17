package com.example.gamjamarket.Info;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gamjamarket.Chat.MessageActivity;
import com.example.gamjamarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class PasswordModifyActivity extends AppCompatActivity {
    private static final String TAG = "PasswordModifyActivity";

    private EditText newPassword;
    private EditText newPassword2;
    private Button modifyBtn;
    private TextView checkPassword;
    private ImageView clear1;
    private ImageView clear2;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        newPassword = (EditText)findViewById(R.id.modify_password_new);
        newPassword2 = (EditText)findViewById(R.id.modify_password_new2);
        checkPassword = (TextView)findViewById(R.id.modify_password_check);
        modifyBtn = (Button)findViewById(R.id.modify_btn_password);
        clear1 = (ImageView)findViewById(R.id.password_clear1);
        clear2 = (ImageView)findViewById(R.id.password_clear2);

        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    clear1.setVisibility(View.VISIBLE);
                } else {
                    clear1.setVisibility(View.INVISIBLE);

                }
            }
        });

        newPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    clear2.setVisibility(View.VISIBLE);
                    modifyBtn.setClickable(true);

                } else {
                    clear2.setVisibility(View.INVISIBLE);
                    modifyBtn.setClickable(false);
                }
            }
        });

        clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword.getText().clear();
            }
        });

        clear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword2.getText().clear();
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordNew = newPassword.getText().toString();
                String passwordNew2 = newPassword2.getText().toString();

                FirebaseUser user = mAuth.getCurrentUser();
                if (passwordNew.equals(passwordNew2)) {
                    user.updatePassword(passwordNew)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                        Intent intent = new Intent(PasswordModifyActivity.this, MessageActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }else {
                    checkPassword.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
