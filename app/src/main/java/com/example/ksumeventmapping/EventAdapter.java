package com.example.ksumeventmapping;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.CustomViewHolder>{

    private Context context;
    private JSONArray events;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public EventAdapter(Context context, JSONArray events) {
        this.context = context;
        this.events = events;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.item_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        try {
            JSONObject event = events.getJSONObject(position);
            holder.note_title.setText(event.getString("title"));
            holder.room.setText(event.getString("venue"));
            String url = "https://iwkerala.org/img/" + event.getString("image");
            Glide.with(context).load(url).into(holder.eventImg);
            String[] dates = event.getString("date").split("\\s");
            holder.month.setText(dates[1]);
            holder.day.setText(dates[2]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Picasso.get().load(chapter.imageUrl).into(holder.ivChapter);
    }

    @Override
    public int getItemCount() {
        return events.length();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView eventImg;
        public TextView note_title,day,month,room;


        public CustomViewHolder(View itemView) {
            super(itemView);
            note_title = (TextView) itemView.findViewById(R.id.eventName);
            day = (TextView) itemView.findViewById(R.id.day);
            month = (TextView) itemView.findViewById(R.id.month);
            room = (TextView) itemView.findViewById(R.id.eventRoom);
            eventImg = (ImageView) itemView.findViewById(R.id.eventImage);
            itemView.setOnClickListener(this);
//            ivChapter = (ImageView) itemView.findViewById(R.id.ivChapter);
        }
        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                try {
                    clickListener.onClick(view, events.getJSONObject(getAdapterPosition()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
