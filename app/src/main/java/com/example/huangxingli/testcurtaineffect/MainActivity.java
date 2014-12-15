package com.example.huangxingli.testcurtaineffect;

import android.app.ActionBar;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Scroller;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        int height  = actionBar.getHeight();
        Scroller scroller;


        Log.v("TAG","---ACTIONBAR为 --"+actionBar);
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            height = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }
        Log.v("TAG","---ACTIONBAR的高度为 --"+height);
       final Button button= (Button) findViewById(R.id.button);


    //    System.out.println(statusBarHeight+"..."+contentViewTop+"..."+titleBarHeight);]
        button.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                Window window = getWindow();
                button.getWindowVisibleDisplayFrame(rect);
                // 状态栏的高度
                int statusBarHeight = rect.top;
                // 标题栏跟状态栏的总体高度
                int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
                // 标题栏的高度：用上面的值减去状态栏的高度及为标题栏高度
                int titleBarHeight = contentViewTop - statusBarHeight;
                Log.v("TAG","--------------------状态栏的高度为"+statusBarHeight+"-------------------标题栏和状态栏的总高度为-----"+contentViewTop);
                Log.v("TAG","======================另一种方式获取的状态栏高度为 -----------"+getStatusBarHeight());
            }
        });
        button.setOnTouchListener(this);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("title_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float rawY=event.getRawY();
        float y=event.getY();
        Log.v("TAG","---RAWy IS "+rawY+"--Y is ---"+y);
        return false;
    }
}
