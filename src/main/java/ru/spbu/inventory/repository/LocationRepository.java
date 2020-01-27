package ru.spbu.inventory.repository;

import ru.spbu.inventory.model.Location;

import java.util.List;

public interface LocationRepository {
    Location save(Location location);
    boolean delete(int id);
    Location get(int id);
    List<Location> getAll();
}
