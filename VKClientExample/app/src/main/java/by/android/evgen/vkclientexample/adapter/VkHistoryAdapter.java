package by.android.evgen.vkclientexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.model.UserData;
import by.android.evgen.vkclientexample.model.history.Items;


/**
 * Created by evgen on 29.03.2015.
 */
public class VkHistoryAdapter extends RecyclerView.Adapter<VkHistoryAdapter.ViewHolder> {

    private Items[] mData;
    private Context mContext;
    private UserData mUser;
    private UserData mUserFrom;

    public VkHistoryAdapter(Context context, Items[] data, UserData user, UserData fromUser) {
        mData = data;
        mContext = context;
        mUser = user;
        mUserFrom = fromUser;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("****", "getItemType" + position);
        Items item = mData[position];
        if(mUser.getUser_id().contains(item.from_id)) {
            Log.d("****", "getItemType" + 1);
            return 0;
        } else {
            Log.d("****", "getItemType" + 0);
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("****", "onCreateViewHolder" + i);
        View v = null;
        if (i == 0) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_adapter_left, viewGroup, false);
        } else if (i == 1) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history_adapter_right, viewGroup, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Items item = mData[i];
        viewHolder.content.setText(item.body);
        viewHolder.newContent.setText(item.body);
        SimpleDateFormat dateFormat = new SimpleDateFormat(" HH:mm ");
        Long timeInMillis = Long.valueOf(item.date);
        Date date=new Date(timeInMillis * 1000);
        viewHolder.online.setText(dateFormat.format(date));
        if(mUser.getUser_id().contains(item.from_id)) {
            viewHolder.name.setText(mUser.getUser_name());
            final String urlImage = mUser.getUser_image();
            Picasso.with(mContext).load(urlImage).into(viewHolder.icon);
        } else {
            viewHolder.name.setText(mUserFrom.getUser_name());
            final String urlImage = mUserFrom.getUser_image();
            Picasso.with(mContext).load(urlImage).into(viewHolder.icon);
        }
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView newContent;
        private TextView name;
        private TextView content;
        private TextView online;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(android.R.id.title);
            content = (TextView) itemView.findViewById(android.R.id.content);
            newContent = (TextView) itemView.findViewById(R.id.new_content);
            online = (TextView) itemView.findViewById(R.id.online);
            icon = (ImageView) itemView.findViewById(android.R.id.icon);
        }
    }

}
