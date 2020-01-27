package ru.spbu.inventory.repository;

import ru.spbu.inventory.model.Device;

import java.util.List;

public interface DeviceRepository {
    Device save(Device device, int locationId);

    // false if not found
    boolean delete(int id);

    // null if not found
    Device get(int id);

    List<Device> getAll();

    List<Device> getAllWithLocationId(int locationId);
}
