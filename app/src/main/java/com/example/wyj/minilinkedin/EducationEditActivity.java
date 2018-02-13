package com.example.wyj.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.wyj.minilinkedin.Utils.DateUtils;
import com.example.wyj.minilinkedin.model.Education;

import java.util.ArrayList;
import java.util.Arrays;

public class EducationEditActivity extends EditBaseActivity<Education> {
    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";
    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.education_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(final Education data) {
        ((EditText)findViewById(R.id.education_school_edit)).setText(data.school);
        ((EditText)findViewById(R.id.education_major_edit)).setText(data.major);
        ((EditText)findViewById(R.id.education_start_data)).setText(DateUtils.dateToString(data.startDate));
        ((EditText)findViewById(R.id.education_end_data)).setText(DateUtils.dateToString(data.endDate));
        ((EditText)findViewById(R.id.education_courses_edit)).setText(TextUtils.join("\n", data.course));
        findViewById(R.id.education_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_education_edit;
    }

    @Override
    protected void saveAndExit(Education data) {
        if (data == null) {
            data = new Education();
        }
        data.school = ((EditText) findViewById(R.id.education_school_edit)).getText().toString();
        data.major = ((EditText) findViewById(R.id.education_major_edit)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_start_data)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_end_data)).getText().toString());
        data.course = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.education_courses_edit)).getText().toString(), "\n"));
        Intent intent = new Intent();
        intent.putExtra(KEY_EDUCATION, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
