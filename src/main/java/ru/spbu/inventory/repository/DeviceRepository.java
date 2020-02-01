package ru.spbu.inventory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    Page<Device> getWithSpecification(Specification<Device> specification, Pageable page);
}
