/*
 * Copyright 2016 CaryMax1988
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liujiaqi.semicirclemenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.liujiaqi.library.SemicircleMenuView;

/**
 * Created by CaryMax1988 on 2016/1/13.
 * EMail liujiaqi19881124@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    SemicircleMenuView view1;
    SemicircleMenuView view2;
    SemicircleMenuView view3;

    RelativeLayout root_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view1 = (SemicircleMenuView) findViewById(R.id.view1);
        view1.setAnimationType(SemicircleMenuView.AnimationType.ALPHA);
        ImageButton ib_view3_1 = (ImageButton) findViewById(R.id.ib_view3_1);
        ib_view3_1.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Item Click", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton ib_view3_2 = (ImageButton) findViewById(R.id.ib_view3_2);
        ib_view3_2.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Item Click", Toast.LENGTH_SHORT).show();
            }
        });
        view2 = (SemicircleMenuView) findViewById(R.id.view2);

        ImageButton btna = new ImageButton(this);
        btna.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btna.setBackgroundResource(R.mipmap.ic_launcher);
        view2.addView(btna);

        ImageButton btnb = new ImageButton(this);
        btnb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnb.setBackgroundResource(R.mipmap.ic_launcher);
        view2.addView(btnb);

        view2.setAnimationType(SemicircleMenuView.AnimationType.ZOOM);
        view3 = (SemicircleMenuView) findViewById(R.id.view3);
        view3.setAnimationType(SemicircleMenuView.AnimationType.ROTATE);
        view3.invalidate();

        SemicircleMenuView view4 = new SemicircleMenuView(this);
        view4.setBackgroundColor(0xFF123455);
        view4.setAnimationType(SemicircleMenuView.AnimationType.NONE);
        view4.setmIntersize(100);
        ImageButton btn1 = new ImageButton(this);
        btn1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn1.setBackgroundResource(R.mipmap.ic_launcher);
        view4.addView(btn1);
        ImageButton btn2 = new ImageButton(this);
        btn2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn2.setBackgroundResource(R.mipmap.ic_launcher);
        view4.addView(btn2);
        ImageButton btn3 = new ImageButton(this);
        btn3.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btn3.setBackgroundResource(R.mipmap.ic_launcher);
        view4.addView(btn3);
        root_view = (RelativeLayout) findViewById(R.id.root_view);
        RelativeLayout.LayoutParams l = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        l.addRule(RelativeLayout.ABOVE, R.id.view1);
        root_view.addView(view4, l);
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
}
