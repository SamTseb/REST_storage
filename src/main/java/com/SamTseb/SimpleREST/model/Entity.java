package com.infotecs.test_work.model;

import java.util.Objects;

/** The class for storage key-data pairs */
public class Entity {
    private String key;
    private String data;
    private int ttl;

    public Entity(String key, String data, int ttl) {
        this.key = key;
        this.data = data;
        this.ttl = ttl;
    }

    /**
     * The copy constructor
     * @param obj The entity for copy
     */
    public Entity(Entity obj) {
        this.key = obj.key;
        this.data = obj.data;
        this.ttl = obj.ttl;
    }

    /**
     * The empty constructor for serialization and deserialization
     */
    public Entity() {
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return getTtl() == entity.getTtl() && getKey().equals(entity.getKey()) && getData().equals(entity.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }
}
