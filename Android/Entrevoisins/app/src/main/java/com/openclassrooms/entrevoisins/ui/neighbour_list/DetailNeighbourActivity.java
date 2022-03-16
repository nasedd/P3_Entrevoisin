package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityDetailNeighbourBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;


import java.util.List;


public class DetailNeighbourActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;
    private List<Neighbour> mFavoriteNeighbours;
    private ActivityDetailNeighbourBinding binding;
    //private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailNeighbourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mApiService = DI.getNeighbourApiService();
        // favorite list :
        mFavoriteNeighbours = mApiService.getNeighboursFavorite();

        // get the bundle back :
        Bundle bundle = getIntent().getExtras();
        Neighbour neighbour = (Neighbour)bundle.getSerializable("KEY");

        if(neighbour != null){
            binding.neighbourName2.setText(neighbour.getName());
            binding.neighbourName.setText(neighbour.getName());
            binding.neighbourAddress.setText(neighbour.getAddress());
            binding.neighbourPhone.setText(neighbour.getPhoneNumber());
            binding.neighbourFacebook.setText(getResources().getString(R.string.facebook_url, neighbour.getName()));
            binding.neighbourDescription.setText(neighbour.getAboutMe());
            Glide.with(binding.neighbourPhoto.getContext()) //affichage d'image à ddl et l'affiche dès qu'elle est prête
                    .load(neighbour.getAvatarUrl())
                    .into(binding.neighbourPhoto);
        }

        binding.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isFavorite = isFavorite(neighbour);

                if (!isFavorite) {
                    mApiService.addFavorite(neighbour);
                    binding.favoriteButton.setImageResource(R.drawable.ic_star_yellow);
                } else if (isFavorite) {
                    mApiService.deleteFavorite(neighbour);
                    binding.favoriteButton.setImageResource(R.drawable.ic_star_white);
                }
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailNeighbourActivity.this.finish();
            }
        });

        //Update star color after opening activity
        if (isFavorite(neighbour)) {
            binding.favoriteButton.setImageResource(R.drawable.ic_star_yellow);
        }

    }

    private boolean isFavorite(Neighbour neighbour) {
        for (Neighbour item : mFavoriteNeighbours) {
            if (neighbour.equals(item)) {
                return true;
            }
        }
        return false;
    }
}