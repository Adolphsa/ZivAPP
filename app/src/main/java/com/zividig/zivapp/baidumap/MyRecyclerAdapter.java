package com.zividig.zivapp.baidumap;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zividig.zivapp.R;

/**
 * RecyclerView的数据适配器
 * Created by Administrator on 2016-04-01.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter {

    public interface OnRecyclerViewListener {
                void onItemClick(int position);
            }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
                 this.onRecyclerViewListener = onRecyclerViewListener;
            }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public View rootView;
        public ImageView itemLeftImg;
        public TextView itemText;
        public ImageView itemRightImg;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);

            itemLeftImg = (ImageView) itemView.findViewById(R.id.setting_item_left_img);
            itemText = (TextView) itemView.findViewById(R.id.setting_item_text);
            itemRightImg = (ImageView) itemView.findViewById(R.id.setting_item_right_img);
            rootView = itemView.findViewById(R.id.setting_item_rootview);
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
                System.out.println("item被点击了" + position);
            }
        }
    }

    //加载布局文件
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.layout_recycler_view_item,null);
        return new ViewHolder(view);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.position = position;
        System.out.println(position);
        switch (position){
            case 0:
                viewHolder.itemLeftImg.setImageResource(R.mipmap.mine_setting);
                viewHolder.itemText.setText("设置");
                viewHolder.itemRightImg.setImageResource(R.mipmap.right_icon);
                break;
            case 1:
                viewHolder.itemLeftImg.setImageResource(R.mipmap.mine_help);
                viewHolder.itemText.setText("帮助");
                viewHolder.itemRightImg.setImageResource(R.mipmap.right_icon);
                break;
            case 2:
                viewHolder.itemLeftImg.setImageResource(R.mipmap.mine_about);
                viewHolder.itemText.setText("关于");
                viewHolder.itemRightImg.setImageResource(R.mipmap.right_icon);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
