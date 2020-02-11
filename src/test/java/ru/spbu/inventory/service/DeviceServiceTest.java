package ru.spbu.inventory.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.spbu.inventory.model.Device;
import ru.spbu.inventory.model.DeviceSpecification;
import ru.spbu.inventory.util.exception.ErrorType;
import ru.spbu.inventory.util.exception.NotFoundException;
import ru.spbu.inventory.web.AbstractControllerTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.spbu.inventory.DevicesTestData.*;
import static ru.spbu.inventory.LocationsTestData.LOCATION1_ID;
import static ru.spbu.inventory.util.ValidationUtil.getRootCause;


class DeviceServiceTest extends AbstractServiceTest {

    @Autowired
    protected DeviceService service;



    @Test
    void add() throws Exception {
        Device newDevice = getNew();
        Device added = service.add(newDevice, LOCATION1_ID);
        Integer newId = added.getId();
        newDevice.setId(newId);
        DEVICE_MATCHERS.assertMatch(added, newDevice);
        DEVICE_MATCHERS.assertMatch(service.get(newId), newDevice);
    }

    @Test
    void remove() throws Exception {
        service.remove(DEVICE1_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(DEVICE1_ID));
    }

    @Test
    void removeNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.remove (1));
    }

    @Test
    void update() throws Exception {
        Device updated = getUpdated();
        service.update(updated);
        DEVICE_MATCHERS.assertMatch(service.get(DEVICE1_ID), updated);
    }

    @Test
    void get() throws Exception {
        Device actual = service.get(DEVICE1_ID);
        DEVICE_MATCHERS.assertMatch(actual, DEVICE01);
    }

    @Test
    void getAll() throws Exception {
        DEVICE_MATCHERS.assertMatch(service.getAll(), DEVICES);
    }

    @Test
    void getAllInLocation() throws Exception {

        DEVICE_MATCHERS.assertMatch(service.getAllInLocation(LOCATION1_ID), LOCATION1_DEVICES);
    }

    @Test
    void filter() throws Exception {
        Pageable page = PageRequest.of(0, 100, Sort.Direction.ASC, "id");
        DeviceSpecification specification = new DeviceSpecification();
        specification.setName("ats");
        DEVICE_MATCHERS.assertMatch(service.filter(specification, page), DEVICE01);
    }

    @Test
    void updateNotFound() throws Exception {
        Device notexistent = getNew();
        notexistent.setId(10);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> service.update(notexistent));
        String msg = e.getMessage();
        assertTrue(msg.contains("device must exist"));
    }
}