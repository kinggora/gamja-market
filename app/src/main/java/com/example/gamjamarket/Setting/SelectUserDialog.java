package com.example.gamjamarket.Setting;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamjamarket.Model.ChatModel;
import com.example.gamjamarket.Model.PostlistItem;
import com.example.gamjamarket.Model.UserModel;
import com.example.gamjamarket.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SelectUserDialog {
    private static final String TAG = "SelectUserDialog";
    private Dialog dialog;
    private Context context;
    private PostlistItem postItem;
    private ArrayList<UserModel> userList = new ArrayList<UserModel>();
    private UseritemAdapter adapter;
    private boolean selected = false;
    private int selectedPosition;

    public SelectUserDialog(Context context, PostlistItem postItem){
        this.context = context;
        this.postItem = postItem;
    }

    public void callDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_selectuser);
        dialog.setCancelable(false);
        setDialog(dialog);


        try {
            WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point deviceSize = new Point();
            display.getSize(deviceSize);

            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = deviceSize.x;
            params.height = (int)(deviceSize.y * 0.9);
            params.horizontalMargin = 0.0f;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setAttributes(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialog.show();
    }

    public void setDialog(Dialog dialog){
        Button selectBtn = ((Dialog) dialog).findViewById(R.id.selectuser_btn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected){
                    setOnsale(false);
                    ReviewWritingDialog newDialog = new ReviewWritingDialog(context, userList.get(selectedPosition), dialog);
                    newDialog.callDialog();
                }
                else{
                    Toast.makeText(context, "???????????? ???????????????",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button exitImage = ((Dialog) dialog).findViewById(R.id.selectuser_exit);
        exitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ImageView goodsImage = ((Dialog) dialog).findViewById(R.id.selectuser_userimage);
        Glide.with(context)
                .load(postItem.getContents())
                .into(goodsImage);
        GradientDrawable roundCorner=
                (GradientDrawable) context.getDrawable(R.drawable.post_round_image);
        goodsImage.setBackground(roundCorner);
        goodsImage.setClipToOutline(true);
        TextView goodsTitle = ((Dialog) dialog).findViewById(R.id.selectuser_usernickname);
        goodsTitle.setText(postItem.getTitle());

        RecyclerView userListview = ((Dialog) dialog).findViewById(R.id.selectuser_list);
        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        userListview.setLayoutManager(verticalLayoutManager);
        adapter = new UseritemAdapter(userList, context, this);
        userListview.setAdapter(adapter);

        setUserlist();

    }

    public void setUserlist(){
        Thread thread = new Thread(()->{
            FirebaseFirestore fs =  FirebaseFirestore.getInstance();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            db.getReference().child("chatrooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot item: snapshot.getChildren()){
                        ChatModel chatModel = item.getValue(ChatModel.class);
                        for(String muid : chatModel.users.keySet()){
                            if(!muid.equals(uid)){
                                fs.collection("users").document(muid)
                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String nickname = documentSnapshot.getString("nickname");
                                        String profileImg = documentSnapshot.getString("profileimg");
                                        UserModel model = new UserModel();
                                        model.setUid(muid);
                                        model.setUsernickname(nickname);
                                        model.setProfileImageUrl(profileImg);
                                        userList.add(model);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });});
        thread.start();
    }

    public void selectedPosition(int i){
        Log.d(TAG, userList.get(i).getUsernickname()+ " is selected.");
        if(!selected){
            selected = true;
        }
        selectedPosition = i;
    }

    public void setOnsale(boolean onsale){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("board1").document(postItem.getPid()).update("onsale", onsale).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "??????????????? ????????? ?????????????????????",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
