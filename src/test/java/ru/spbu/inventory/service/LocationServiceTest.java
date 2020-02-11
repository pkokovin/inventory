package ru.spbu.inventory.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.spbu.inventory.model.Location;
import ru.spbu.inventory.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.spbu.inventory.LocationsTestData.*;

class LocationServiceTest extends  AbstractServiceTest{

    @Autowired
    LocationService service;

    @Test
    void add() {
        Location newLocation = getNew();
        Location added = service.add(newLocation);
        Integer newId = added.getId();
        newLocation.setId(newId);
        LOCATION_MATCHERS.assertMatch(added, newLocation);
        LOCATION_MATCHERS.assertMatch(service.get(newId), newLocation);
    }

    @Test
    void remove() {
        service.remove(LOCATION1_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(LOCATION1_ID));
    }
    @Test
    void removeNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(LOCATION1_ID-50));
    }

    @Test
    void update() {
        Location updated = getUpdated();
        service.update(updated);
        LOCATION_MATCHERS.assertMatch(service.get(LOCATION1_ID), updated);
    }

    @Test
    void get() {
        LOCATION_MATCHERS.assertMatch(service.get(LOCATION1_ID), LOCATION1);
    }

    @Test
    void getAll() {
        LOCATION_MATCHERS.assertMatch(service.getAll(), LOCATIONS);
    }
}