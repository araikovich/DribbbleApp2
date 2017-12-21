package araikovichinc.dribbbleapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import araikovichinc.dribbbleapp.API.Shot;
import araikovichinc.dribbbleapp.R;

/**
 * Created by Tigran on 20.12.2017.
 */

public class ShotRecyclerViewAdapter extends RecyclerView.Adapter<ShotRecyclerViewAdapter.ShotViewHolder> {

    private List<Shot> shots;
    private Context context;
    private RelativeLayout.LayoutParams params;

    public ShotRecyclerViewAdapter(List<Shot> shots, int layoutParam, Context context){
        this.shots = shots;
        double h = layoutParam * 0.75;
        int height = (int)h;
        params = new RelativeLayout.LayoutParams(layoutParam, height);
        this.context = context;
    }

    @Override
    public ShotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card_item, parent, false);
        return new ShotViewHolder(v);
    }



    @Override
    public void onBindViewHolder(ShotViewHolder holder, int position) {
        holder.shotsPhoto.setLayoutParams(params);
        holder.title.setText(shots.get(position).getTitle());
        holder.description.setText(shots.get(position).getDescription());
        if(shots.get(position).getImages().getHidpi()!= null){
            Glide.with(context).load(shots.get(position).getImages().getHidpi()).into(holder.shotsPhoto);
        }else {
            Glide.with(context).load(shots.get(position).getImages().getNormal()).into(holder.shotsPhoto);
        }
    }

    @Override
    public int getItemCount() {
        if(shots == null)
            return 0;
        return shots.size();
    }

    public class ShotViewHolder extends RecyclerView.ViewHolder{

        ImageView shotsPhoto;
        TextView title, description;
        CardView cardView;

        public ShotViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card);
            shotsPhoto = (ImageView)itemView.findViewById(R.id.feed_card_item_image);
            title = (TextView)itemView.findViewById(R.id.feed_card_item_title);
            description = (TextView)itemView.findViewById(R.id.feed_card_item_description);
        }
    }
}
