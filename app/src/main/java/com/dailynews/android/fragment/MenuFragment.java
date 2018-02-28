package com.dailynews.android.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dailynews.android.R;
import com.dailynews.android.model.NewsListItem;
import com.dailynews.android.util.Constant;
import com.dailynews.android.util.HttpUtils;
import com.dailynews.android.util.PreUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseFragment implements View.OnClickListener {

    private ListView theme_list;
    private TextView theme_download,theme_main, theme_backup, theme_login;
    private LinearLayout head_menu_layout;
    private List<NewsListItem> items;
    private Handler handler = new Handler();
    private NewsTypeAdapter mAdapter;

    //初始化片段视图
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        head_menu_layout = (LinearLayout)view.findViewById(R.id.head_menu_layout);
        theme_login = (TextView)view.findViewById(R.id.login);
        theme_backup = (TextView)view.findViewById(R.id.theme_backup);
        theme_download = (TextView)view.findViewById(R.id.theme_download);
        theme_main = (TextView)view.findViewById(R.id.theme_main);
        theme_list = (ListView)view.findViewById(R.id.theme_list);

        theme_download.setOnClickListener(this);
        theme_main.setOnClickListener(this);
        //主题列表点击事件,替换点击主题所对应的片段
        theme_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }

    //片段内控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.theme_main:

        }
    }

    //初始化片段数据
    @Override
    protected void initData() throws IOException {
        super.initData();
        items = new ArrayList<NewsListItem>();
        //判断当前活动网络是否可用,如果可用就发送请求，否则从缓存中获取数据并解析
        if (HttpUtils.isNetWorkConnected(mActivity)){
            //传入拼接的URL地址发送请求
            /**
            HttpUtils.get(Constant.THEMES, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    String json = response.toString();
                    //将请求返回的数据以键值对保存到缓存中
                    PreUtils.putStringToDefault(mActivity, Constant.THEMES, json);
                    //解析返回的Json数据
                    parseJson(response);
                }
            });
             */
            HttpUtils.sendOkHttpRequest(Constant.THEMES, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mActivity,"加载失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String textResponse = response.body().string();
                    //将请求返回的数据以键值对保存到缓存中
                    PreUtils.putStringToDefault(mActivity, Constant.THEMES, textResponse);
                    //解析返回的Json数据
                    parseJson(textResponse);
                }
            });
        }else {
            String json = PreUtils.getStringFromDefault(mActivity,Constant.THEMES,"");
            parseJson(json);

        }
    }

    private void parseJson(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray itemsArray = jsonObject.getJSONArray("others");
            for (int i = 0; i < itemsArray.length(); i++){
                NewsListItem newsListItem = new NewsListItem();
                JSONObject itemObject = itemsArray.getJSONObject(i);
                newsListItem.setId(itemObject.getString("id"));
                newsListItem.setTitle(itemObject.getString("name"));
                items.add(newsListItem);
            }
            mAdapter = new NewsTypeAdapter();
            theme_list.setAdapter(mAdapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    //主题列表子项适配器
    public class NewsTypeAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.menu_item_layout, parent,false);
                TextView themeName = (TextView)convertView.findViewById(R.id.theme_name);
                themeName.setText(items.get(position).getTitle());
            }
            return convertView;
        }
    }
}
