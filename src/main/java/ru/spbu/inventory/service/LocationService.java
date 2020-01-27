package ru.spbu.inventory.service;


import ru.spbu.inventory.model.Location;

import java.util.List;

public interface LocationService {
    Location add(Location location);

    void remove(int id);

    void update(Location location);

    Location get(int id);

    List<Location> getAll();
}
