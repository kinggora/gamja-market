package com.example.gamjamarket.Setting;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gamjamarket.R;


public class LikesListActivity extends AppCompatActivity {
    private static final String TAG = "LikesListActivity";

    private LikesListFragment1 fragment1 = new LikesListFragment1();
    private LikesListFragment2 fragment2 = new LikesListFragment2();

    ColorDrawable c = new ColorDrawable(Color.parseColor("#af876d"));
    ColorDrawable w = new ColorDrawable(Color.parseColor("#ffffff"));

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likeslist);

        Button btn1 = (Button)findViewById(R.id.btn_likes1);
        Button btn2 = (Button)findViewById(R.id.btn_likes2);
        FrameLayout selectedspace1 = (FrameLayout)findViewById(R.id.likelist_selectspace1);
        FrameLayout selectedspace2 = (FrameLayout)findViewById(R.id.likelist_selectspace2);

        replaceFragment(fragment1);
        Button backButton = (Button)findViewById(R.id.likelist_backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(Color.parseColor("#191919"));
                btn2.setTextColor(Color.parseColor("#767676"));
                selectedspace1.setBackground(c);
                selectedspace2.setBackground(w);
                replaceFragment(fragment1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(Color.parseColor("#767676"));
                btn2.setTextColor(Color.parseColor("#191919"));
                selectedspace1.setBackground(w);
                selectedspace2.setBackground(c);
                replaceFragment(fragment2);

            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.likeslist_layout, fragment).commit();
    }

}
