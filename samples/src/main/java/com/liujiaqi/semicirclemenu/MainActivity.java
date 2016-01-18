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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.liujiaqi.library.SemicircleMenuView;

/**
 * Created by CaryMax1988 on 2016/1/13.
 * EMail liujiaqi19881124@gmail.com
 */
public class MainActivity extends AppCompatActivity {
    SemicircleMenuView view1;
    SemicircleMenuView view2;
    SemicircleMenuView view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view1 = (SemicircleMenuView) findViewById(R.id.view1);
        view1.setAnimationType(SemicircleMenuView.AnimationType.ALPHA);
        view2 = (SemicircleMenuView) findViewById(R.id.view2);
        view2.setAnimationType(SemicircleMenuView.AnimationType.ZOOM);
        view3 = (SemicircleMenuView) findViewById(R.id.view3);
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
