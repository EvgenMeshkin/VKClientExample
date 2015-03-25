package by.android.evgen.vkclientexample.adapter;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.model.Items;

/**
 * Created by evgen on 24.03.2015.
 */
public class VkFriendsAdapter extends RecyclerView.Adapter<VkFriendsAdapter.ViewHolder> {

    private Items[] mData;
    private Context mContext;

    public VkFriendsAdapter(Context context, Items[] data) {
        mData = data;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friends_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Items item = mData[i];
        viewHolder.name.setText(item.first_name);
        viewHolder.content.setText(item.last_name);
        final String urlImage = item.photo_200_orig;
//        viewHolder.icon.setImageBitmap(null);
//        viewHolder.icon.setTag(urlImage);
        Picasso.with(mContext).load(urlImage).into(viewHolder.icon);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView content;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(android.R.id.title);
            content = (TextView) itemView.findViewById(android.R.id.content);
            icon = (ImageView) itemView.findViewById(android.R.id.icon);
        }
    }

}
