package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zividig.zivapp.R;
import com.zividig.zivapp.baidumap.MyRecyclerAdapter;
import com.zividig.zivapp.recyclerlist.AdvanceDecoration;

/**
 * 设置
 * Created by Administrator on 2016-03-18.
 */
public class Setting extends Fragment implements MyRecyclerAdapter.OnRecyclerViewListener {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("设置");

        //RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.setting_recycler_view);
        //固定大小
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        adapter.setOnRecyclerViewListener(this);
        recyclerView.addItemDecoration(new AdvanceDecoration(getContext(), OrientationHelper.VERTICAL));
        recyclerView.setAdapter(adapter); //设置适配器
        return view;
    }

    @Override
    public void onItemClick(int position) {
        System.out.println("Setting-----" +position);
    }
}
