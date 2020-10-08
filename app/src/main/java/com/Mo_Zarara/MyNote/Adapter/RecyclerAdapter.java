package com.Mo_Zarara.MyNote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Mo_Zarara.MyNote.Models.Note;
import com.Mo_Zarara.MyNote.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.View_Holder> {

    private List<Note> list = new ArrayList<>();
    Context context;

    private OnItemClickListenerInterface mListener;

    /*public RecyclerAdapter(List<NewsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }*/

    public class View_Holder extends RecyclerView.ViewHolder {

        public View view;
        public TextView date, title, description;

        public View_Holder(@NonNull View itemView) {
            super(itemView);

            view = itemView;

            date = itemView.findViewById(R.id.date_id);
            title = itemView.findViewById(R.id.title_id);
            description = itemView.findViewById(R.id.description_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    if (mListener != null && index != RecyclerView.NO_POSITION) {
                        mListener.OnItemClick(list.get(index));
                    }
                }
            });
        }



    }


    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new View_Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {

        /*NewsModel currentNews = list.get(position);
        holder.date.setText(currentNews.getPublishedAt());*/

        holder.date.setText(list.get(position).getDate());
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());




        /*final int index = holder.getAdapterPosition();
        ((View_Holder) holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((ListActivity) context).openStories(dataList.get(index));

                //((MainActivity) context).openNews(context, list.get(index).getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(index).getUrl()));
                websiteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(websiteIntent);



            }
        });*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setList(List<Note> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }


    public Note getNoteAt(int pos) {
        return list.get(pos);
    }

    //FOR CLICK ITEM

    public interface OnItemClickListenerInterface {

        void OnItemClick(Note note);

    }

    public void OnItemClickListnerFunc(OnItemClickListenerInterface listener){
        mListener = listener;
    }

}
