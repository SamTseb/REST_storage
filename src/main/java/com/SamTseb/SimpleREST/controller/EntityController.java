package com.SamTseb.SimpleREST.controller;

import com.SamTseb.SimpleREST.model.Entity;
import com.SamTseb.SimpleREST.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntityController {
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping(value = "/entities/{key}")
    public ResponseEntity<Entity> readByID(@PathVariable String key) {
        final Entity data = entityService.read(key);
        return data != null
                ? new ResponseEntity<>(data, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/entities")
    public ResponseEntity<List<Entity>> readAll() {
        final List<Entity> data = entityService.readAll();
        return data != null
                ? new ResponseEntity<>(data, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/entities/{key}")
    public ResponseEntity<Entity> create(@PathVariable String key, @RequestParam(required = false) Integer ttl, @RequestBody Entity entity) {
        final boolean created;
        if (ttl == null)
            created = entityService.create(key, entity);
        else
            created = entityService.create(key, entity, ttl);

        return created ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping(value = "/entities/{key}")
    public ResponseEntity<?> update(@PathVariable String key, @RequestParam(required = false) Integer ttl, @RequestBody Entity entity){
        final boolean updated;
        if (ttl == null)
            updated = entityService.update(key, entity);
        else
            updated = entityService.update(key, entity, ttl);

        return updated ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping(value = "/entities/{key}")
    public ResponseEntity<Entity> delete(@PathVariable String key){
        final Entity deleted = entityService.delete(key);

        if(deleted != null)
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/dump")
    public ResponseEntity<?> dump(){
        return entityService.dump() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/load")
    public ResponseEntity<?> load(){
        return entityService.load() ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
