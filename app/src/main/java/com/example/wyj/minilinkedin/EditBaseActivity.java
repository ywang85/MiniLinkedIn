package com.example.wyj.minilinkedin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public abstract class EditBaseActivity<T> extends AppCompatActivity {

    private T data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId()); // 加载布局

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 返回按钮

        data = initializeData(); // 初始化数据
        // 如果数据为空，进入新建模式，否则进入编辑模式
        if (data != null) {
            setupUIForEdit(data);
        } else {
            setupUIForCreate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.save_button) {
            saveAndExit(data);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_main, menu);
       return true;
    }

    protected abstract void setupUIForCreate();

    protected abstract void setupUIForEdit(T data);

    protected abstract T initializeData();

    protected abstract int getLayoutId();

    protected abstract void saveAndExit(T data);

}
