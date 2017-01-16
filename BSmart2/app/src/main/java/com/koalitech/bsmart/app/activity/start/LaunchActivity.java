package com.koalitech.bsmart.app.activity.start;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.koalitech.bsmart.R;
import com.koalitech.bsmart.domain.remote.HttpListener;
import com.koalitech.bsmart.domain.remote.RemoteServer;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

}
