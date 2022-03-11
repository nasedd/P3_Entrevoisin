package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.databinding.ActivityDetailNeighbourBinding;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


public class DetailNeighbourActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private ActivityDetailNeighbourBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailNeighbourBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setContentView(R.layout.activity_detail_neighbour);

        mApiService = DI.getNeighbourApiService(); // à ce que j'ai crompris ça ne crée pas une nouvelle instance pour une nouvelle list
        //c'est comme une variable globale ?
        mNeighbours = mApiService.getNeighbours();

        Bundle bundle = getIntent().getExtras();
        long neighbourId = bundle.getLong("KEY_ID",1);
        for ( Neighbour item : mNeighbours) {
            if(neighbourId == item.getId()){
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







    }
}
