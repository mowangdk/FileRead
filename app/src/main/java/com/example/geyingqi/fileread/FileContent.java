package com.example.geyingqi.fileread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by geyingqi on 10/31/15.
 */
public class FileContent extends Activity{
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_content);
        Intent intent  = getIntent();
        String fileContent = intent.getStringExtra("FileContent");
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(fileContent);
    }
}
