package ba.unsa.etf.rpr.dao;

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
    T getById(int id);

    /**
     * Retrieves all entities from database
     * @return list of entities from database
     */
    List<T> getAll();

    /**
     * Saves entity to database
     * @param item bean to be saved to database
     * @return saved entity from database
     */
    T add(T item);

    /**
     * Updates entity in database based on item id
     * @param item bean to be updated
     * @return updated version of bean
     */
    T update(T item);

    /**
     * Deletes entity from database based on item id
     * @param item to be deleted
     */
    void delete(T item);


}
