package ru.spbu.inventory.service;

import ru.spbu.inventory.model.Device;

import java.util.List;

public interface DeviceService {
    Device add(Device device, int locationId);

    void remove(int id);

    void update(Device device);

    Device get(int id);

    List<Device> getAll();

    List<Device> getAllInLocation(int locationId);
}
