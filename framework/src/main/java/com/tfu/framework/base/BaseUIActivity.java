package com.tfu.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.tfu.framework.utils.SystemUIUtils.fixSystemUI;

public class BaseUIActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fixSystemUI(this);
    }
}
