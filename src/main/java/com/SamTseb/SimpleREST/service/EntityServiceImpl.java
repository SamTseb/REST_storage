package com.SamTseb.SimpleREST.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.SamTseb.SimpleREST.model.Entity;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityServiceImpl implements EntityService {

    /** The entities storage */
    private static Map<String, Entity> ENTITY_REPOSITORY_MAP = new HashMap<>();

    /** The default TTL  */
    private static final int DEFAULT_TTL = 100;

    @Override
    public boolean create(String key, Entity entity, int ttl) {
        if (key != null && !ENTITY_REPOSITORY_MAP.containsKey(key)) {
            entity.setKey(key);
            entity.setTtl(ttl);
            ENTITY_REPOSITORY_MAP.put(key, entity);
            return true;
        } else
            return false;
    }

    public boolean create(String key, Entity entity) {
        return create(key, entity, DEFAULT_TTL);
    }

    @Override
    public Entity read(String key) {
        return ENTITY_REPOSITORY_MAP.get(key);
    }

    @Override
    public List<Entity> readAll() {
        return new ArrayList<>(ENTITY_REPOSITORY_MAP.values());
    }

    @Override
    public boolean update(String key, Entity entity, int ttl) {
        if (ENTITY_REPOSITORY_MAP.containsKey(key)) {
            entity.setKey(key);
            entity.setTtl(ttl);
            ENTITY_REPOSITORY_MAP.put(key, entity);
            return true;
        }
        else
            return false;
    }

    public boolean update(String key, Entity entity) {
        return update(key, entity, DEFAULT_TTL);
    }

    @Override
    public Entity delete(String key) {
        Entity deleted = ENTITY_REPOSITORY_MAP.get(key);
        if (deleted != null)
            ENTITY_REPOSITORY_MAP.remove(key);
        return deleted;
    }

    @Override
    public boolean dump() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("dump.json"), ENTITY_REPOSITORY_MAP);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Entity>> typeRef = new TypeReference<HashMap<String, Entity>>() {
        };
        try {
            ENTITY_REPOSITORY_MAP = mapper.readValue(new File("dump.json"), typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
