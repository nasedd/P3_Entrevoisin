package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);

    /**
     * Get my favorite list
     * @return {@link List}
     */
    List<Neighbour> getNeighboursFavorite();

    //add neighbour in favorite list :
    void addFavorite(Neighbour neighbour);

    //delete neighbour in favorite list :
    void deleteFavorite(Neighbour neighbour);





}
