package ru.spbu.inventory.web.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.spbu.inventory.model.Location;
import ru.spbu.inventory.service.LocationService;

import java.net.URI;
import java.util.List;

import static ru.spbu.inventory.util.ValidationUtil.assureIdConsistent;
import static ru.spbu.inventory.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = LocationRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationRestController {
    static final String REST_URL = "/rest/locations";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final LocationService service;

    @Autowired
    public LocationRestController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Location> getAll() {
        log.info("getAll locations");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Location get(@PathVariable Integer id) {
        log.info("get location By Id");
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> addWithLocation(@RequestBody Location location) {
        log.info("add Location");
        checkNew(location);
        Location added = service.add(location);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(added.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(added);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        service.remove(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Location location, @PathVariable int id) {
        log.info("update {} with id={}", location, id);
        assureIdConsistent(location, id);
        service.update(location);
    }

}
