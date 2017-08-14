package com.dingdangmao.tagview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main extends AppCompatActivity {
    TagView tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tag=(TagView)findViewById(R.id.tag);

    }
}
