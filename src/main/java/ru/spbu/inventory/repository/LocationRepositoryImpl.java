package ru.spbu.inventory.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.spbu.inventory.model.Location;

import java.util.List;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

    private final CrudLocationRepository locationRepository;

    @Autowired
    public LocationRepositoryImpl(CrudLocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public boolean delete(int id) {
        return locationRepository.delete(id) != 0;
    }

    @Override
    public Location get(int id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.findAll();
    }
}
