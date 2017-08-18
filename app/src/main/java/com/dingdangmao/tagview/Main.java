package com.dingdangmao.tagview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {
    TagView tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tag=(TagView)findViewById(R.id.tag);
        tag.addListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=(TextView)v;
                Toast.makeText(Main.this,((TextView) v).getText().toString(),Toast.LENGTH_SHORT).show();
                tag.removeView(v);
                tag.addView(v,0);
            }
        });
        tag.addBefore(new TagView.before() {
            @Override
            public void execute(TextView v) {
                v.setBackgroundResource(R.drawable.text);
            }
        });
        tag.addTag(new String[]{"java","c++","c#","Python"});
        tag.addTagString("Apple");
        tag.addTagString("Facebook");
        tag.addTagString("Amazon");
        tag.addTagString("the sky full of stars");

    }
}
