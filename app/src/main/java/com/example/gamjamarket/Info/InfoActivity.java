package com.example.gamjamarket.Info;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.gamjamarket.MainActivity;
import com.example.gamjamarket.R;
import com.example.gamjamarket.Setting.ProfileImg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class InfoActivity extends AppCompatActivity {
    private static final String TAG = "InfoActivity";

    private TextView nickname;
    private TextView name;
    private TextView email;
    private TextView phoneNumber;
    private Button modifyButton;
    private Button logoutButton;
    private Button passwordButton;
    private Button unregisterButton;
    private ImageView profileImageView;
    private ImageView backBtn;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();

        nickname = (TextView) findViewById(R.id.infoActivity_textview_nicname);
        name = (TextView) findViewById(R.id.infoActivity_textview_name2);
        email = (TextView) findViewById(R.id.infoActivity_textview_email2);
        phoneNumber = (TextView) findViewById(R.id.infoActivity_textview_number2);
        modifyButton = (Button) findViewById(R.id.infoActivity_btn_profilemodify);
        logoutButton = (Button) findViewById(R.id.infoActivity_btn_logout);
        passwordButton = (Button) findViewById(R.id.infoActivity_btn_passwordmodify);
        unregisterButton = (Button) findViewById(R.id.infoActivity_btn_unregister);
        profileImageView = (ImageView) findViewById(R.id.infoActivity_imageview);
        backBtn = (ImageView) findViewById(R.id.infoActivity_back);

        setUI(uid);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, InfoModifyActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutAlert();
            }
        });

        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, PasswordModifyActivity.class);
                startActivity(intent);
            }
        });

        unregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterAlert(uid);
            }
        });

    }
    public void logoutAlert() {

        new AlertDialog.Builder(InfoActivity.this)
                .setMessage("????????? ???????????????????????????????"+"/n" +"?????? ????????? ????????? ?????????????????????.")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mAuth.signOut();
                        finish();
                        Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }
    public void unregisterAlert(String uid) {

        new AlertDialog.Builder(InfoActivity.this)
                .setMessage("????????? ?????????????????????????"+"/n" +"?????? ????????? ????????? ?????? ???????????????.")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User account deleted.");
                                        }
                                    }
                                });
                        db.collection("users").document(uid)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });

                        //RealtimeDB user??????
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        mDatabase.child("users").child(uid).removeValue();

                        //????????? ?????? ??????
                        db.collection("likes").document(uid).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });

                        //????????? ??????
//                db.collection("board1")
//                        .whereEqualTo("uid", uid).delete();
                    }
                }).show();
    }

    public void setUI(String uid){
        ProfileImg profileImg = new ProfileImg();
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nickname.setText(document.getString("nickname"));
                        name.setText(document.getString("name"));
                        email.setText(document.getString("email"));
                        phoneNumber.setText(document.getString("phone"));
                        profileImageView.setImageResource(profileImg.getSrc(document.getString("profileimg")));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public void onResume(){
        super.onResume();
        String uid = mAuth.getCurrentUser().getUid();
        setUI(uid);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
