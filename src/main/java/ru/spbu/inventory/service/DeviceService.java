package ru.spbu.inventory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.spbu.inventory.model.Device;

import java.util.List;

public interface DeviceService {
    Device add(Device device, int locationId);

    void remove(int id);

    void update(Device device);

    Device get(int id);

    List<Device> getAll();

    List<Device> getAllInLocation(int locationId);

    Page<Device> filter(Specification<Device> specification, Pageable page);
}
