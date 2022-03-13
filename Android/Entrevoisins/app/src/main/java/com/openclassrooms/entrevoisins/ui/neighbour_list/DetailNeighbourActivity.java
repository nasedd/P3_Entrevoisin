package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityDetailNeighbourBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


import java.util.List;


public class DetailNeighbourActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private List<Neighbour> mFavoriteNeighbours;
    private ActivityDetailNeighbourBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailNeighbourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_detail_neighbour);

        mApiService = DI.getNeighbourApiService(); // à ce que j'ai crompris ça ne crée pas une nouvelle instance de class ?
        //c'est comme une instance de class globale ?
        mNeighbours = mApiService.getNeighbours();

        Bundle bundle = getIntent().getExtras();
        long neighbourId = bundle.getLong("KEY_ID", 1);
        for (Neighbour item : mNeighbours) {
            if (neighbourId == item.getId()) { //rechercher le neighbour correspondant à l'id dans la list de neighbours
                binding.neighbourName2.setText(item.getName());
                binding.neighbourName.setText(item.getName());
                binding.neighbourAddress.setText(item.getAddress());
                binding.neighbourPhone.setText(item.getPhoneNumber());
                binding.neighbourFacebook.setText(getResources().getString(R.string.facebook_url, item.getName()));
                binding.neighbourDescription.setText(item.getAboutMe());
                Glide.with(binding.neighbourPhoto.getContext()) //affichage d'image à ddl et l'affiche dès qu'elle est prête
                        .load(item.getAvatarUrl())
                        .into(binding.neighbourPhoto);
            }
        }

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailNeighbourActivity.this.finish();
            }
        });
        mFavoriteNeighbours = mApiService.getNeighboursFavorite();

        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isFavorite = isNeighbourFavorite(neighbourId);

                if (!isFavorite) {
                    mFavoriteNeighbours.add(findNeighbour(neighbourId)); //comment testé rapidement si ma list est bien incrémenté ?
                    binding.favoriteButton.setImageResource(R.drawable.ic_star_yellow);
                } else if (isFavorite) {
                    mFavoriteNeighbours.remove(findNeighbour(neighbourId));
                    binding.favoriteButton.setImageResource(R.drawable.ic_star_white);
                }
            }


        });

        //Actualiser la couleur de l'étoile quand on ouvre l'activity Detail
        if (isNeighbourFavorite(neighbourId)) {
            binding.favoriteButton.setImageResource(R.drawable.ic_star_yellow);
        }


    }

    private boolean isNeighbourFavorite(long neighbourId) {
        boolean bool = false;
        for (Neighbour item : mFavoriteNeighbours) {
            if (neighbourId == item.getId()) { //verifie si le neighbour est dans la list de favorie
                bool = true;
                break;
            }
        }
        return bool;
    }

    private Neighbour findNeighbour(long neighbourId) {
        Neighbour neighbour = new Neighbour(1, "", "", "", "", "");
        for (Neighbour item : mNeighbours) {
            if (neighbourId == item.getId()) {
                neighbour = item;
                break;
            }
        }
        return neighbour;
    }
}
