package ru.spbu.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.inventory.model.Device;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudDeviceRepository extends JpaRepository<Device, Integer>, JpaSpecificationExecutor<Device> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Device d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Device save(Device device);

    @Query("SELECT d FROM Device d WHERE d.location.id=:locationId")
    List<Device> getAllByLocationId(@Param("locationId") int locationId);

    @Query("SELECT d FROM Device d WHERE d.id=:id")
    Device get(@Param("id") int id);
}
