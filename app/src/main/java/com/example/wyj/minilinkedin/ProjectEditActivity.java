package com.example.wyj.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.wyj.minilinkedin.Utils.DateUtils;
import com.example.wyj.minilinkedin.model.Project;

import java.util.Arrays;

public class ProjectEditActivity extends EditBaseActivity<Project> {
    public static final String KEY_PROJECT = "project";
    public static final String KEY_PROJECT_ID = "project_id";
    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.project_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(final Project data) {
        ((EditText) findViewById(R.id.project_name_edit)).setText(data.name);
        ((EditText) findViewById(R.id.project_start_date)).setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.project_end_date)).setText(DateUtils.dateToString(data.endDate));
        ((EditText) findViewById(R.id.project_description_edit)).setText(TextUtils.join("\n", data.details));
        findViewById(R.id.project_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(KEY_PROJECT_ID, data.id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected Project initializeData() {
        return getIntent().getParcelableExtra(KEY_PROJECT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_edit;
    }

    @Override
    protected void saveAndExit(Project data) {
        if (data == null) {
            data = new Project();
        }
        data.name = ((EditText) findViewById(R.id.project_name_edit)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.project_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.project_end_date)).getText().toString());
        data.details = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.project_description_edit)).getText().toString(), "\n"));
        Intent intent = new Intent();
        intent.putExtra(KEY_PROJECT, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
