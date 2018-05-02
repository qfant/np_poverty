package com.page.party;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfant.wuye.R;


/**
 * Created by chenxi.cui on 2018/3/19.
 */
public class MineAdapter extends RecyclerView.Adapter {
    private int[] iconArr;
    private String[] titleArr;
    private OnItemOnClickListener listener;

    public MineAdapter(int[] iconArr, String[] titleArr) {
        setData(iconArr, titleArr);
    }

    public void setData(int[] iconArr, String[] titleArr) {
        this.iconArr = iconArr;
        this.titleArr = titleArr;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageIcon;
        public TextView tvTitle;
        public View viewCircle;

        public ViewHolder(View view) {
            super(view);
            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            viewCircle = view.findViewById(R.id.view_circle);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_fragment_mine_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.imageIcon.setImageResource(iconArr[position]);
        viewHolder.tvTitle.setText(titleArr[position]);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemOnClickListener(position);
                }
            }
        });
    }

    public void setOnItemOnClickListener(OnItemOnClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemOnClickListener {
        void onItemOnClickListener(int pos);
    }

    @Override
    public int getItemCount() {
        return iconArr.length;
    }
}
