package ru.spbu.inventory.web.device;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.spbu.inventory.model.Device;
import ru.spbu.inventory.service.DeviceService;

import java.net.URI;
import java.util.List;

import static ru.spbu.inventory.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DeviceRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceRestController {
    static final String REST_URL = "/rest/devices";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final DeviceService deviceService;

    @Autowired
    public DeviceRestController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<Device> getAll() {
        log.info("get all devices");
        return deviceService.getAll();
    }

    @GetMapping("/location/{locationId}")
    public List<Device> getAllAtLocation(@PathVariable int locationId) {
        log.info("get all at location {}", locationId);
        return deviceService.getAllInLocation(locationId);
    }

    @GetMapping("/{id}")
    public Device get(@PathVariable int id) {
        log.info("get device by id");
        return deviceService.get(id);
    }

    @PostMapping(value ="/{locationId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Device> addWithLocation(@RequestBody Device device, @PathVariable int locationId) {
        checkNew(device);
        log.info("create {} at location with id {}", device, locationId);
        Device created = deviceService.add(device, locationId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete device with id {}", id);
        deviceService.remove(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Device device) {
        log.info("updating device {}", device);
        deviceService.update(device);
    }
}
