package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mFavoriteNeighbours;

    public FavoriteRecyclerViewAdapter(List<Neighbour> items) {
        mFavoriteNeighbours = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // le viewgroup est le recyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mFavoriteNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext()) //affichage d'image à ddl et l'affiche dès qu'elle est prête
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mStarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFavoriteNeighbours.remove(neighbour);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });

        holder.mDetail.setOnClickListener(new View.OnClickListener() { // pourquoi mettre un m en prefixe ? = m pour member attribut de class
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("KEY_ID",neighbour.getId());
                Intent intent = new Intent(v.getContext(), DetailNeighbourActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return mFavoriteNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_star_button)
        public ImageButton mStarButton;
        @BindView(R.id.list_item_detail)
        public ConstraintLayout mDetail;
        //public Context context;



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            //context = view.getContext();
        }
    }
}
