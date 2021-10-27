package com.hhh.searchapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private EditText mEditText;
    private ListView mListView;
    private TextView mTextView;
    Context context;
    private TextView tv_name;
    private MyAdapter adapter;
    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initView();
    }

    private void initView() {
        list = new ArrayList<>();

        mTextView = (TextView) findViewById(R.id.textview);
        mEditText = (EditText) findViewById(R.id.edittext);
        mListView = (ListView) findViewById(R.id.listview);
        adapter = new MyAdapter(
                this,
                list,
                R.layout.item,
                new String[]{"name"},
                new int[]{R.id.tv_name}
        );
        mListView.setAdapter(adapter);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把EditText内容设置为空
                mEditText.setText("");
                //把ListView隐藏
                mListView.setVisibility(View.GONE);
            }
        });

        //EditText添加监听
        mEditText.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }//文本改变之前执行

            @Override
            //文本改变的时候执行
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果长度为0
                showListView();
            }

            public void afterTextChanged(Editable s) {
            }//文本改变之后执行
        });

    }

    public List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 1; i <= 200; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "这是第" + i + "行");
            list.add(map);
        }
        return list;
    }

    private class MyAdapter extends SimpleAdapter {
        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
                         String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            tv_name = v.findViewById(R.id.tv_name);
            tv_name.setText(list.get(position).get("name"));
            return v;
        }
    }

    private void showListView() {
        mListView.setVisibility(View.VISIBLE);
        //获得输入的内容
        String str = mEditText.getText().toString().trim();
        List<Map<String, String>> newList = new ArrayList<>();
        if (str.equals("")) {
            list.clear();
            newList.clear();
            newList = getData();
            list.addAll(newList);
            adapter.notifyDataSetChanged();
        } else {
            list.clear();
            newList.clear();
            newList = getData();
            System.out.println("newList:" + newList);
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).get("name").contains(str)) {
                    list.add(newList.get(i));
                }
            }
            adapter.notifyDataSetChanged();
        }

    }
}