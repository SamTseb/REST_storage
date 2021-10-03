package com.infotecs.test_work.service;

import com.infotecs.test_work.model.Entity;

import java.util.List;

/** The interface for work with entities in the storage. */
public interface EntityService {

    /**
     * Create a new entity
     * @param key The key for data
     * @return true - creating is success, false - it isn't success
     */
    boolean create(String key, Entity entity, int ttl);
    boolean create(String key, Entity entity);

    /**
     * Return an entity by the key
     *
     * @param key The key of an entity
     * @return The entity
     */
    Entity read(String key);

    /**
     * Return all entities from storage
     * @return All entities
     */
    List<Entity> readAll();

    /**
     * Update an entity by the key using the entity instance from a request body
     *
     * @param key A string key in the database
     * @param entity Data for updating entity
     * @param ttl An entity's time to live
     * @return true - the entity was update, false - wasn't update
     */
    boolean update(String key, Entity entity, int ttl);
    boolean update(String key, Entity entity);

    /**
     * Delete an entity by the key
     *
     * @param key An entity key
     * @return true - the entity was delete, false - wasn't delete
     */
    Entity delete(String key);

    /**
     * The function for save the state of the storage. The save file is "dump.json"
     * @return true - if saving is success, false - if it isn't.
     */
    boolean dump();

    /**
     * The function for load the state of storage. The save file is "dump.json"
     * @return true - if loading is success, false - if it isn't.
     */
    boolean load();
}
