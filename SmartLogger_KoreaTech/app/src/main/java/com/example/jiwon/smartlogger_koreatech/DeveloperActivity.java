/*
 * ------------------------------------------------------------------------
 * Copyright 2014 Korea Electronics Technology Institute
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
 * ------------------------------------------------------------------------
 */

package com.example.jiwon.smartlogger_koreatech;


import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import java.io.IOException;

public class DeveloperActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        findViewById(R.id.runCube).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Run nCube activity


                        }



                }
        );

        findViewById(R.id.checkCube).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Send to nCube service


                    }
                }
        );

        findViewById(R.id.stopCube).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }
        );


        findViewById(R.id.setting).setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //return true;

                    }
                }
        );

        findViewById(R.id.sendMessage).setOnClickListener(

                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        }
                        //return true;
                        /*
                        try {
                            TasSender tasSender;
                            tasSender = new TasSender();
                            tasSender.sendMessage("sensorTest","hello");
                            Log.d("ssw","sssss");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */
                    }

        );

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


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {





    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
