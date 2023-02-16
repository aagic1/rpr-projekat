package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

/**
 * Root interface for all Dao classes
 *
 * @author Ahmed Agic
 */
public interface Dao<T> {

    /**
     * Returns entity from database by id
     * @param id primary key of entity
     * @return searched entity from database
     */
    T getById(int id) throws RecipeException;

    /**
     * Retrieves all entities from database
     * @return list of entities from database
     */
    List<T> getAll() throws RecipeException;

    /**
     * Saves entity to database
     * @param item bean to be saved to database
     * @return saved entity from database
     */
    T add(T item) throws RecipeException;

    /**
     * Updates entity in database based on item id
     * @param item bean to be updated
     * @return updated version of bean
     */
    T update(T item) throws RecipeException;

    /**
     * Deletes entity from database based on item id
     * @param item to be deleted
     */
    void delete(T item) throws RecipeException;


}
