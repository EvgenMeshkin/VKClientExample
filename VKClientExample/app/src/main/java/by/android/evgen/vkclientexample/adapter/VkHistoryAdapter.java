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
import by.android.evgen.vkclientexample.model.users.Response;


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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friends_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Items item = mData[i];
        viewHolder.content.setText(item.body);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm ");
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

        private TextView name;
        private TextView content;
        private TextView online;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(android.R.id.title);
            content = (TextView) itemView.findViewById(android.R.id.content);
            online = (TextView) itemView.findViewById(R.id.online);
            icon = (ImageView) itemView.findViewById(android.R.id.icon);
        }
    }

}
