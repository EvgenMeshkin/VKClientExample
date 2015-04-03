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
import java.util.Calendar;
import java.util.Date;

import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.model.dialog.Items;
import by.android.evgen.vkclientexample.model.users.Response;
import by.android.evgen.vkclientexample.model.users.Users;

/**
 * Created by evgen on 28.03.2015.
 */
public class VkDialogsAdapter extends RecyclerView.Adapter<VkDialogsAdapter.ViewHolder> {

    private Items[] mData;
    private Context mContext;
    private Users mUsers;

    public VkDialogsAdapter(Context context, Items[] data, Users users) {
        mData = data;
        mContext = context;
        mUsers = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friends_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Items item = mData[i];
//       Users users = new Select().from(Users.class).executeSingle();
        Response user = null;
        for (int j = 0; j < mUsers.response.length; j++) {
            Response value = mUsers.response[j];
            if (item.message.user_id.contains(value.id)) {
                user = value;
            }
        }
        if (user != null) {
            viewHolder.name.setText(user.first_name);
            final String urlImage = user.photo_200_orig;
            Picasso.with(mContext).load(urlImage).into(viewHolder.icon);
        }
        viewHolder.content.setText(item.message.body);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm ");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(Long.parseLong(item.message.date)/1000);
        viewHolder.online.setText(dateFormat.format(item.message.date));
        viewHolder.main.setTag(user);
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
        private View main;

        public ViewHolder(View itemView) {
            super(itemView);
            main = itemView;
            name = (TextView) itemView.findViewById(android.R.id.title);
            content = (TextView) itemView.findViewById(android.R.id.content);
            online = (TextView) itemView.findViewById(R.id.online);
            icon = (ImageView) itemView.findViewById(android.R.id.icon);
        }
    }

}
