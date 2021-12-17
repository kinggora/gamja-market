package com.example.gamjamarket.Home2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gamjamarket.Home1.ImageviewActivity;
import com.example.gamjamarket.Model.WriteinfoModel;
import com.example.gamjamarket.Model.WriteinfoModel2;
import com.example.gamjamarket.R;

public class PostviewFragment2 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post2, container, false);
        WriteinfoModel2 model = (WriteinfoModel2) getArguments().getSerializable("writeinfoModel");

        ImageView explainImage1= view.findViewById(R.id.post2_explain1);
        ImageView explainImage2= view.findViewById(R.id.post2_explain2);
        ImageView explainImage3= view.findViewById(R.id.post2_explain3);


        Glide.with(getActivity())
                .load(model.getExplain1())
                .into(explainImage1);
        Glide.with(getActivity())
                .load(model.getExplain2())
                .into(explainImage2);
        Glide.with(getActivity())
                .load(model.getExplain3())
                .into(explainImage3);

        return view;
    }


}
