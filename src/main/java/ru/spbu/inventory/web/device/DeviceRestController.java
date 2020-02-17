package ru.spbu.inventory.web.device;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.spbu.inventory.View;
import ru.spbu.inventory.model.Device;
import ru.spbu.inventory.model.DeviceSpecification;
import ru.spbu.inventory.service.DeviceService;

import java.beans.PropertyEditorSupport;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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

    @GetMapping(value = "/filter")
    public ResponseEntity<List<Device>> filter(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "model", required = false) String model,
                                               @RequestParam(value = "serial", required = false) String serial,
                                               @RequestParam(value = "inventory", required = false) String inventory,
                                               @RequestParam(value = "after", required = false) LocalDateTime after,
                                               @RequestParam(value = "before", required = false) LocalDateTime before,
                                               @RequestParam(value = "description", required = false) String description,
                                               @RequestParam(value = "contacts", required = false) String contacts,
                                               @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
                                               DeviceSpecification deviceSpecification) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "id");
        return new ResponseEntity<>(deviceService.filter(setSpec(name, model, serial
                , inventory, after, before, description, contacts, deviceSpecification), page).getContent(), HttpStatus.OK);
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
    public ResponseEntity<Device> addWithLocation(@Validated(View.Web.class) @RequestBody Device device, @PathVariable int locationId) {
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
    public void update(@Validated(View.Web.class) @RequestBody Device device) {
        log.info("updating device {}", device);
        deviceService.update(device);
    }

    private DeviceSpecification setSpec(String name, String model, String serial
            , String inventory, LocalDateTime after, LocalDateTime before, String description, String contacts, DeviceSpecification deviceSpecification) {
        if (name != null) deviceSpecification.setName(name);
        if (model != null) deviceSpecification.setModel(model);
        if (serial != null) deviceSpecification.setSerial(serial);
        if (inventory != null) deviceSpecification.setInventory(inventory);
        if (after != null) deviceSpecification.setAfter(after);
        if (before != null) deviceSpecification.setBefore(before);
        if (description != null) deviceSpecification.setDescription(description);
        if (contacts != null) deviceSpecification.setContacts(contacts);
        return  deviceSpecification;
    }

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
//            public void setAsText(String value) {
//                setValue(new Date(Long.valueOf(value)));
//            }
//        });
//    }
}
