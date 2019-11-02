package com.example.tirthshah.volunteerifest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

/**
 * Created by Tirth Shah on 02-01-2016.
 */
public class GroupViewAdapter{}
// extends RecyclerView.Adapter<GroupViewAdapter.MyViewHolder> {
//    private final LayoutInflater inflater;
//    private int singleXmlLayout;
//    List<Information> data= Collections.emptyList();
//    Context context;
//    public GroupViewAdapter(Context context, List<Information> data, int xmlLayout) {
//        this.context=context;
//        this.inflater = LayoutInflater.from(context);
//        this.data=data;
//        this.singleXmlLayout =xmlLayout;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v=inflater.inflate(singleXmlLayout,parent,false);
//        MyViewHolder holder=new MyViewHolder(v);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        Information current=data.get(position);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(context, ChatActivity.class);
//                i.putExtra("room",data.get(position).title);
//                context.startActivity(i);
//            }
//        });
//        holder.title.setText(current.title);
//        holder.icon.setImageResource(current.iconId);
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView title;
//        ImageView icon;
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            title= (TextView) itemView.findViewById(R.id.groupTitle);
//            icon= (ImageView) itemView.findViewById(R.id.groupIcon);
//        }
//    }
//}
