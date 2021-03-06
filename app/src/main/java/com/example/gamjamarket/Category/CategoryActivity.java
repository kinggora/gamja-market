package com.example.gamjamarket.Category;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.gamjamarket.Model.CategoryModel;
import com.example.gamjamarket.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";
    private ArrayList<CategoryModel> categoryList = new ArrayList<CategoryModel>();
    private CategoryGridviewAdapter adapter;
    private static String CATEGORY;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        GridView categoryGridview = (GridView)findViewById(R.id.category_gridview);
        adapter = new CategoryGridviewAdapter(categoryList, this);
        categoryGridview.setAdapter(adapter);

        CATEGORY = getIntent().getStringExtra("category");
        getCategorySet();

        Button backButton = (Button)findViewById(R.id.category_backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditText searchEdit = (EditText)findViewById(R.id.category_searchedit);
        Button searchButton = (Button)findViewById(R.id.category_searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_word = searchEdit.getText().toString();
                goSearch(search_word);
            }
        });

        //????????? ??? ???????????? ?????????
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search_word = searchEdit.getText().toString();
                    goSearch(search_word);
                    return true;
                }
                return false;
            }
        });


    }

    public void getCategorySet() {
        Thread thread = new Thread(()->{
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(CATEGORY).orderBy("index")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e("Firestore error", error.getMessage());
                                return;
                            }
                            for (DocumentChange dc : value.getDocumentChanges()) {
                                if (dc.getType() == DocumentChange.Type.ADDED) {
                                    DocumentSnapshot document = dc.getDocument();
                                    //CategoryModel model = new CategoryModel(document.getId(), document.getString("name"));
                                    CategoryModel model = document.toObject(CategoryModel.class);
                                    categoryList.add(model);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        });
        thread.start();
    }

    public Intent getNextIntent(){
        if(CATEGORY.equals("categories")){
            return new Intent(this, PostInCategory1Activity.class);
        }
        else if(CATEGORY.equals("categories2")){
            return new Intent(this, PostInCategory2Activity.class);
        }
        return null;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void goSearch(String word){
        String BOARD;
        if(CATEGORY.equals("categories")){
            BOARD = "board1";
        }else if(CATEGORY.equals("categories2")){
            BOARD = "board2";
        }else{ return; }
        Intent searchResultActivity = new Intent(CategoryActivity.this, SearchResultActivity.class);
        searchResultActivity.putExtra("board", BOARD);
        searchResultActivity.putExtra("word", word);
        startActivity(searchResultActivity);
    }


}
