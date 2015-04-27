package by.android.evgen.vkclientexample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import by.android.evgen.vkclientexample.R;
import by.android.evgen.vkclientexample.helper.GetFriends;
import by.android.evgen.vkclientexample.model.dialog.Items;

/**
 * Created by evgen on 24.03.2015.
 */
public class VkFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Items> mData;
    private Context mContext;

    public VkFriendsAdapter(Context context, List<Items> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) == null) {
        return 1;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friends_adapter, viewGroup, false);
            return new ViewHolderMain(v);
        } else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item_progress, viewGroup, false);
            return new ViewHolderFooter(v);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolderMain) {
            Items item = mData.get(i);
            if (item != null) {
                ((ViewHolderMain) viewHolder).name.setText(item.first_name);
                ((ViewHolderMain) viewHolder).content.setText(item.last_name);
                if (item.online) {
                    ((ViewHolderMain) viewHolder).online.setText(mContext.getString(R.string.online));
                } else {
                    ((ViewHolderMain) viewHolder).online.setText(mContext.getString(R.string.offlaine));
                }
                final String urlImage = item.photo_200_orig;
                Picasso.with(mContext).load(urlImage).into(((ViewHolderMain) viewHolder).icon);
                viewHolder.itemView.setTag(item);
            }
        } else if (viewHolder instanceof ViewHolderFooter) {
            ((ViewHolderFooter) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolderMain extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView content;
        private TextView online;
        private ImageView icon;
        private View itemView;

        public ViewHolderMain(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = (TextView) itemView.findViewById(android.R.id.title);
            content = (TextView) itemView.findViewById(android.R.id.content);
            online = (TextView) itemView.findViewById(R.id.online);
            icon = (ImageView) itemView.findViewById(android.R.id.icon);
        }
    }

    class ViewHolderFooter extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ViewHolderFooter(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressbar);
        }
    }
}
