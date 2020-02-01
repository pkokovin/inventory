package ru.spbu.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.spbu.inventory.model.Device;
import ru.spbu.inventory.model.Location;
import ru.spbu.inventory.repository.DeviceRepository;
import ru.spbu.inventory.repository.LocationRepository;

import java.util.List;


import static ru.spbu.inventory.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository, LocationRepository locationRepository) {
        this.deviceRepository = deviceRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Device add(Device device, int locationId) {
        Assert.notNull(device, "device must not be null");
        checkNotFoundWithId(locationRepository.get(locationId), locationId);
        return deviceRepository.save(device, locationId);
    }

    @Override
    public void remove(int id) {
        checkNotFoundWithId(deviceRepository.delete(id), id);
    }

    @Override
    public void update(Device device) {
        Assert.notNull(device, "device must not be null");
        Assert.notNull(device.getId(), "device must not be new");
        Device current = deviceRepository.get(device.getId());
        Assert.notNull(current, "device must exist");
        Location location = current.getLocation();
        Integer locationId = location.getId();
        Assert.notNull(locationId, "locationId of location must not be null");
        checkNotFoundWithId(deviceRepository.save(device, locationId), device.getId());
    }

    @Override
    public Device get(int id) {
        return checkNotFoundWithId(deviceRepository.get(id), id);
    }

    @Override
    public List<Device> getAll() {
        return deviceRepository.getAll();
    }

    @Override
    public List<Device> getAllInLocation(int locationId) {
        return deviceRepository.getAllWithLocationId(checkNotFoundWithId(locationRepository.get(locationId), locationId).id());
    }

    @Override
    public Page<Device> filter(Specification<Device> specification, Pageable page) {
        return deviceRepository.getWithSpecification(specification, page);
    }
}
