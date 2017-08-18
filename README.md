# TagView
 TagView is a simple android flow tag viewgroup
 You can add new view when you provide strings and also drag the subordinate view 
 
# snap
![TagView](/snap/demo.PNG)


# demo

```xml
<com.dingdangmao.tagview.TagView
    android:id="@+id/tag"
    app:mleft="10dp"
    app:mright="10dp"
    app:mtop="5dp"
    app:mbottom="5dp"
    app:bg_alpha="100"
    app:bg_radius="10dp"
    app:bg_color="#ffcccc"
    app:bg_border_width="3.0"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    </com.dingdangmao.tagview.TagView>
```



```java
        tag=(TagView)findViewById(R.id.tag);
        tag.addListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=(TextView)v;
                Toast.makeText(Main.this,((TextView) v).getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        
        /*
        you can add this to set style of view
        */
        
        tag.addBefore(new TagView.before() {
            @Override
            public void execute(TextView v) {
                v.setBackgroundResource(R.drawable.text);
            }
        });
        
        /*
        addtag - add the tag
        settag - set the tagthe former will be deleted）
        */
        
        tag.addTag(new String[]{"java","c++","c#","Python"});
        tag.addTagString("Apple");
        tag.addTagString("Facebook");
        tag.addTagString("Amazon");
        tag.addTagString("the sky full of stars");
```



