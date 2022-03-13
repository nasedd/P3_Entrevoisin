package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = FavoriteFragment.newInstance();;

        switch (position){
                case 0:
                    fragment =  NeighbourFragment.newInstance();
                     break;
                case 1:
                    fragment =  FavoriteFragment.newInstance();
                    break;
        }
        return fragment; // might not have been initialized si je ne l'initialise pas avant le switch. y a t il une autre solution ?
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}