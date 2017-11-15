package com.jkb.aj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jkb.apt.annimator.HelloAPT;

@HelloAPT("你好，APT")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
