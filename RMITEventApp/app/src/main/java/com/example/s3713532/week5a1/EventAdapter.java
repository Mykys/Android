package com.example.s3713532.week5a1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context mContext;
    private List<Event> eventList;
    private int adapterPage;


    public EventAdapter(Context mContext, List<Event> eventList, int adapterPage) {
        this.mContext = mContext;
        this.eventList = eventList;
        this.adapterPage = adapterPage;
    }

    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventAdapter.MyViewHolder viewHolder, int position) {
        final Event event = eventList.get(position);
        viewHolder.title.setText(event.getTitle());
        viewHolder.date.setText(event.getDate());

        // Image
        String eventImage = event.getImage();
        if (eventImage == null) {
            eventImage = "http://localhap.blob.core.windows.net/shared/event-placeholder.jpg";
        }

        if (eventImage.contains("http")) {

            Glide.with(mContext)
                    .load(eventImage)
                    .placeholder(R.drawable.load)
                    .into(viewHolder.thumbnail);

        } else {

            String base64Image = eventImage;
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

            Glide.with(mContext)
                    .load(decodedString)
                    .placeholder(R.drawable.load)
                    .into(viewHolder.thumbnail);
        }

        viewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(viewHolder.overflow, event);
            }
        });
    }

    // Showing popup menu when tapping on 3 dots
    private void showPopupMenu(View view, Event event) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();

        if (adapterPage == 1) {
            inflater.inflate(R.menu.menu_event, popup.getMenu());
        }

        if (adapterPage == 2) {
            inflater.inflate(R.menu.menu_favourite, popup.getMenu());
        }

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(event));
        popup.show();
    }

    // Click listener for popup menu items
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private Event event;

        public MyMenuItemClickListener(Event event) {
            this.event = event;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    // Add to favourite list
                    Intent intent = new Intent(mContext, FavouriteEvents.class);
                    intent.putExtra("selectedEvent", event);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    return true;
                case R.id.action_remove_favourite:
                    // Remove from favourite list
                    eventList.remove(event);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Removed", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            // on item click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Event clickedDataItem = eventList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("clickedEvent", clickedDataItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}