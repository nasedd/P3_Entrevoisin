package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */

@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));

        //Neighbour favoriteToAdd = service.getNeighbours().get(0);
        //service.addFavorite(favoriteToAdd);

    }

    @Test
    public void getNeighboursFavoriteWithSuccess() {
        List<Neighbour> favorites = service.getNeighboursFavorite();
        List<Neighbour> expectedFavorites = DummyNeighbourGenerator.DUMMY_FAVORITES;
        assertThat(favorites, containsInAnyOrder(expectedFavorites.toArray()));
    }

    @Test
    public void addFavoriteWithSuccess() {
        Neighbour favoriteToAdd = service.getNeighbours().get(0);
        service.addFavorite(favoriteToAdd);
        assertTrue(service.getNeighboursFavorite().contains(favoriteToAdd));
    }

    @Test
    public void deleteFavoriteWithSuccess() {

        //add favorite :
        Neighbour favoriteToAdd = service.getNeighbours().get(0);
        service.addFavorite(favoriteToAdd);
        assertTrue(service.getNeighboursFavorite().contains(favoriteToAdd));

        //delete favorite :
        Neighbour favoriteToDelete = service.getNeighboursFavorite().get(0);
        service.deleteFavorite(favoriteToDelete);
        assertEquals(0,service.getNeighboursFavorite().size());
        assertFalse(service.getNeighboursFavorite().contains(favoriteToDelete));
    }

    @Test
    public void createNeighbourWithSuccess() {

        Neighbour neighbourToCreate = new Neighbour(
                        1+System.currentTimeMillis(),
                        "toto",
                        "https://i.pravatar.cc/150?u="+ System.currentTimeMillis(),
                        "Mont Fuji",
                        "123456789",
                        "Yeah !");

        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));

    }

}


