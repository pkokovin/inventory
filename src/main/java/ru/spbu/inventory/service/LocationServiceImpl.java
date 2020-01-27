package ru.spbu.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spbu.inventory.model.Location;
import ru.spbu.inventory.repository.LocationRepository;

import java.util.List;

import static ru.spbu.inventory.util.ValidationUtil.checkNotFoundWithId;


@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location add(Location location) {
        Assert.notNull(location, "location must not be null");
        return locationRepository.save(location);
    }

    @Override
    public void remove(int id) {
        checkNotFoundWithId(locationRepository.delete(id), id);
    }

    @Override
    public void update(Location location) {
        locationRepository.save(location);
    }

    @Override
    public Location get(int id) {
        return checkNotFoundWithId(locationRepository.get(id), id);
    }

    @Override
    public List<Location> getAll() {
        return locationRepository.getAll();
    }
}
