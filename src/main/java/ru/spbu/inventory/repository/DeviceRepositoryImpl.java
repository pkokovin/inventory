package ru.spbu.inventory.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.spbu.inventory.model.Device;

import java.util.List;

@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    private final CrudDeviceRepository crudDeviceRepository;
    private final CrudLocationRepository crudLocationRepository;

    @Autowired
    public DeviceRepositoryImpl(CrudDeviceRepository crudDeviceRepository, CrudLocationRepository crudLocationRepository) {
        this.crudDeviceRepository = crudDeviceRepository;
        this.crudLocationRepository = crudLocationRepository;
    }

    @Override
    public Device save(Device device, int locationId) {
        if (!device.isNew() && get(device.getId()) == null) {
            return null;
        }
        device.setLocation(crudLocationRepository.getOne(locationId));
        return crudDeviceRepository.save(device);
    }

    @Override
    public boolean delete(int id) {
        return crudDeviceRepository.delete(id) != 0;
    }

    @Override
    public Device get(int id) {
        return crudDeviceRepository.get(id);
    }

    @Override
    public List<Device> getAll() {
        return crudDeviceRepository.findAll();
    }

    @Override
    public List<Device> getAllWithLocationId(int locationId) {
        return crudDeviceRepository.getAllByLocationId(locationId);
    }

    @Override
    public Page<Device> getWithSpecification(Specification<Device> specification, Pageable page) {
        return crudDeviceRepository.findAll(specification, page);
    }
}
