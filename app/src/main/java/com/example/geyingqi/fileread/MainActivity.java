package com.example.geyingqi.fileread;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity  {
    EditText editText;
    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> pathList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.textNormal);
        button = (Button) findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pathList);
        adapter.setNotifyOnChange(true);
        listView.setAdapter(adapter);
        searchFile("");
        editText.addTextChangedListener(new TextWatcher() {
            String text;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(MainActivity.this, R.string.search, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                text = editText.getText().toString();
                searchFile(text);
            }
        });
        button.setText(R.string.button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = editText.getText().toString();
                String encoding = "UTF-8";
                File file = new File(path);
                if (file.isFile() && file.exists()) {
                    try {
                        InputStreamReader inReader = new InputStreamReader(new FileInputStream(file), encoding);
                        BufferedReader bufferedReader = new BufferedReader(inReader);
                        StringBuffer content = new StringBuffer();
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            content.append(line);
                        }
                        Intent intent = new Intent();
                        intent.putExtra("FileContent", content.toString());
                        intent.setClass(MainActivity.this, FileContent.class);
                        startActivity(intent);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = pathList.get(position);
                Log.d("MainActivity", "show the AndroidStudioProjects string = " + s);
                String editText1 = editText.getText().toString();
                if (null == editText1 || "".equals(editText1)){
                    editText.setText(s);
                } else {
                    editText.setText(editText1.substring(0,editText1.lastIndexOf("/"))+s.substring(s.lastIndexOf("/")));
                }

            }
        });

    }

    private void searchFile(String text) {
        ArrayList<String> tmpList = new ArrayList<String>();
        pathList.clear();
        String path;
        if ("".equals(text) || null == text){
            path = "/";
        } else {
            path  = text;
        }
        Log.d("MainActivity", "show the path = "+path.substring(0,path.lastIndexOf("/")+1));
        File[] files = new File(path.substring(0,path.lastIndexOf("/")+1)).listFiles();
        String find = path.substring(path.lastIndexOf("/")+1);
        Log.d("MainActivity", "show the path = " + path+"find = "+find);
        if (files == null) return;
        for(File file : files){
            if (file.getName().indexOf(find) >= 0){
                tmpList.add(file.getPath());
            }
        }
        pathList.addAll(tmpList);
        tmpList.clear();
        adapter.notifyDataSetChanged();
    }
}
