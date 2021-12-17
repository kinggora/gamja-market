package com.example.gamjamarket.Setting;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.gamjamarket.R;

public class MyItemActivity extends AppCompatActivity {
    private static final String TAG = "MyItemActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myitem);

        Button btn1 = (Button)findViewById(R.id.btn_onSalePost);
        Button btn2 = (Button)findViewById(R.id.btn_completedPost);
        FrameLayout selectedspace1 = (FrameLayout)findViewById(R.id.myitem_selectspace1);
        FrameLayout selectedspace2 = (FrameLayout)findViewById(R.id.myitem_selectspace2);
        ColorDrawable c = new ColorDrawable(Color.parseColor("#af876d"));
        ColorDrawable w = new ColorDrawable(Color.parseColor("#ffffff"));

        MyItemFragment onSaleFragment = new MyItemFragment(true);
        replaceFragment(onSaleFragment);

        Button backButton = (Button)findViewById(R.id.myitem_backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyItemFragment onSaleFragment = new MyItemFragment(true);
                replaceFragment(onSaleFragment);
                btn1.setTextColor(Color.parseColor("#191919"));
                btn2.setTextColor(Color.parseColor("#767676"));
                selectedspace1.setBackground(c);
                selectedspace2.setBackground(w);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyItemFragment completedFragment = new MyItemFragment(false);
                replaceFragment(completedFragment);
                btn1.setTextColor(Color.parseColor("#767676"));
                btn2.setTextColor(Color.parseColor("#191919"));
                selectedspace1.setBackground(w);
                selectedspace2.setBackground(c);
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.myitem_layout, fragment).commit();
    }


}
