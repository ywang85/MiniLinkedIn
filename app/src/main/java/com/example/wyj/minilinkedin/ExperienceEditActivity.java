package com.example.wyj.minilinkedin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.wyj.minilinkedin.Utils.DateUtils;
import com.example.wyj.minilinkedin.model.Experience;

import java.util.Arrays;

public class ExperienceEditActivity extends EditBaseActivity<Experience> {

    public static final String KEY_EXPERIENCE = "experience";
    public static final String KEY_EXPERIENCE_ID = "experience_id";
    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.experience_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(final Experience data) {
        ((EditText) findViewById(R.id.company_edit)).setText(data.company);
        ((EditText) findViewById(R.id.title_edit)).setText(data.title);
        ((EditText) findViewById(R.id.experience_start_date)).setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.experience_end_date)).setText(DateUtils.dateToString(data.endDate));
        ((EditText) findViewById(R.id.experience_description_edit)).setText(TextUtils.join("\n", data.details));
        findViewById(R.id.experience_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(KEY_EXPERIENCE_ID, data.id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected Experience initializeData() {
        return getIntent().getParcelableExtra(KEY_EXPERIENCE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_experience_edit;
    }

    @Override
    protected void saveAndExit(Experience data) {
        if (data == null) {
            data = new Experience();
        }
        data.company = ((EditText) findViewById(R.id.company_edit)).getText().toString();
        data.title = ((EditText) findViewById(R.id.title_edit)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.experience_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.experience_end_date)).getText().toString());
        data.details = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.experience_description_edit)).getText().toString(), "\n"
        ));
        Intent intent = new Intent();
        intent.putExtra(KEY_EXPERIENCE, data);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


}
