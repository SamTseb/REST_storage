package com.SamTseb.SimpleREST.service;

import com.SamTseb.SimpleREST.model.Entity;
import org.junit.jupiter.api.*;

import java.util.*;

class EntityServiceImplTest {
    EntityService service;
    List<Entity> actual;
    Entity[] entities;
    Random random;

    public EntityServiceImplTest() {
        entities = new Entity[5];
        entities[0] = new Entity("1", "That is the first entity", 1);
        entities[1] = new Entity("2", "That is the second entity", 2);
        entities[2] = new Entity("3", "That is the third entity", 3);
        entities[3] = new Entity("4", "That is the fourth entity", 4);
        entities[4] = new Entity("5", "That is the fifth entity", 5);

        random = new Random(new Date().getTime());
    }

    @BeforeEach
    void setUp() {
        service = new EntityServiceImpl();
        actual = new ArrayList<>();
    }

    @Test
    void read_exist_entity() {
        for (int index = 0; index < 3; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        int index = random.nextInt(3);

        Assertions.assertEquals(entities[index], service.read(String.valueOf(index)));
    }

    @Test
    void read_not_exist_entity() {
        for (int index = 0; index < 3; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        Assertions.assertEquals(null, service.read("not_exist"));
    }

    @Test
    void create() {
        if (service.create("1", entities[0], 1))
            Assertions.assertEquals(service.read("1"), entities[0]);
        else
            Assertions.fail();
    }

    @Test
    void update() {
        for (int index = 0; index < 3; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        int index = random.nextInt(3);
        Entity updated = new Entity(String.valueOf(index), "That is absolutely new data!", 100500);

        service.update(String.valueOf(index), updated, 100500);

        Assertions.assertEquals(updated, service.read(String.valueOf(index)));
    }

    @Test
    void create_with_default_TTL() {
        Entity default_entity = new Entity(entities[0]);
        default_entity.setTtl(100);

        if (service.create("1", entities[0]))
            Assertions.assertEquals(service.read("1"), default_entity);
        else
            Assertions.fail();
    }

    @Test
    void Update_with_default_TTL() {
        for (int index = 0; index < 3; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        int index = random.nextInt(3);
        Entity updated = new Entity(String.valueOf(index), "That is absolutely new data!", 100);

        service.update(String.valueOf(index), updated);

        Assertions.assertEquals(updated, service.read(String.valueOf(index)));
    }

    @Test
    void delete() {
        for (int index = 0; index < 3; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        int index = random.nextInt(3);

        Entity deleted = service.read(String.valueOf(index));
        Assertions.assertEquals(deleted, service.delete(String.valueOf(index)));
        Assertions.assertNull(service.read(String.valueOf(index)));
    }

    @Test
    void deleteAll() {
        for (int index = 0; index < 5; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        for (int index = 0; index < 5; index++){
            service.delete(String.valueOf(index));
        }

        for (int index = 0; index < 5; index++){
            if (service.read(String.valueOf(index)) != null)
                Assertions.fail();
        }
    }

    @Test
    void dump_load() {
        for (int index = 0; index < 5; index++){
            service.create(String.valueOf(index), entities[index], index+1);
        }

        service.dump();

        for (int index = 0; index < 5; index++){
            service.delete(String.valueOf(index));
        }

        for (int index = 0; index < 5; index++){
            if (service.read(String.valueOf(index)) != null)
                Assertions.fail();
        }

        service.load();

        for (int index = 0; index < 5; index++){
            if (!(service.read(String.valueOf(index)).equals(entities[index])))
                Assertions.fail();
        }

    }
}