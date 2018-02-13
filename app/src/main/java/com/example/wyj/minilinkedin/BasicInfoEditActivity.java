package com.example.wyj.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.wyj.minilinkedin.model.BasicInfo;

public class BasicInfoEditActivity extends EditBaseActivity<BasicInfo> {
    public static final String KEY_BASIC_INFO = "basic_info";

    @Override
    protected void setupUIForCreate() {

    }

    @Override
    protected void setupUIForEdit(BasicInfo data) {
        ((EditText) findViewById(R.id.basic_info_edit_name)).setText(data.name);
        ((EditText) findViewById(R.id.basic_info_edit_email)).setText(data.email);

    }

    @Override
    protected BasicInfo initializeData() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info_edit;
    }

    @Override
    protected void saveAndExit(BasicInfo data) {
        if (data == null) {
            data = new BasicInfo();
        }
        data.name = ((EditText) findViewById(R.id.basic_info_edit_name)).getText().toString();
        data.email = ((EditText) findViewById(R.id.basic_info_edit_email)).getText().toString();
        Intent intent = new Intent();
        intent.putExtra(KEY_BASIC_INFO, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
