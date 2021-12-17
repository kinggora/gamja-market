package com.example.gamjamarket.Info;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.gamjamarket.Category.CategoryActivity;
import com.example.gamjamarket.Chat.MessageActivity;
import com.example.gamjamarket.Login.DongRegisterActivity;
import com.example.gamjamarket.MainActivity;
import com.example.gamjamarket.R;
import com.example.gamjamarket.Setting.LikesListActivity;
import com.example.gamjamarket.Setting.MyItemActivity;
import com.example.gamjamarket.Setting.ProfileImg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class InfoFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String uid = mAuth.getCurrentUser().getUid();

    private TextView nickname;
    private ImageView profileImageView;
    private RatingBar reviewRatingbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitializationToolbar();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);


        reviewRatingbar = view.findViewById(R.id.infoActivity_ratingbar);
        nickname = view.findViewById(R.id.infoFragment_textview_nickname);
        profileImageView = view.findViewById(R.id.infoFragment_imageview);
        ImageView settingBtn = view.findViewById(R.id.infoFragment_btn_setting);
        LinearLayout myProductBtn = view.findViewById(R.id.infoFragment_btn_myproduct);
        LinearLayout heartBtn = view.findViewById(R.id.infoFragment_btn_heart);
        LinearLayout reviewBtn = view.findViewById(R.id.infoFragment_btn_review);
        LinearLayout positionBtn = view.findViewById(R.id.infoFragment_btn_position);

        setUI(uid);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });

        myProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myitemActivity = new Intent(v.getContext(), MyItemActivity.class);
                startActivity(myitemActivity);
            }
        });

        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent likeslistActivity = new Intent(v.getContext(), LikesListActivity.class);
                startActivity(likeslistActivity);
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewActivity = new Intent(v.getContext(), ReviewActivity.class);
                reviewActivity.putExtra("uid", uid);
                startActivity(reviewActivity);
            }
        });

        positionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dongRegisterActivity = new Intent(v.getContext(), DongRegisterActivity.class);
                startActivity(dongRegisterActivity);
            }
        });
        return view;
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
                        profileImageView.setImageResource(profileImg.getSrc(document.getString("profileimg")));
                    }
                }
            }
        });
        setRatingbar();
    }

    public void onResume(){
        super.onResume();
        String uid = mAuth.getCurrentUser().getUid();
        setUI(uid);

    }

    public void InitializationToolbar(){
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("내 정보");
            ((MainActivity) activity).findViewById(R.id.main_toolbar_image).setVisibility(View.INVISIBLE);
            ((MainActivity) activity).findViewById(R.id.main_search_button).setVisibility(View.INVISIBLE);
            ((MainActivity) activity).findViewById(R.id.main_like_button).setVisibility(View.INVISIBLE);
        }
    }

    public void setRatingbar(){
        db.collection("users").document(uid).collection("review").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Double sum = 0.0;
                        int n = queryDocumentSnapshots.getDocuments().size();
                        for(DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                            if (ds.exists() && ds != null) {
                                Double rating = ds.getDouble("rating");
                                sum += rating;
                            }
                        }
                        if(n > 0){
                            reviewRatingbar.setRating((float)(sum/n));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
