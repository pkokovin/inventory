package ru.spbu.inventory.web.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.spbu.inventory.LocationsTestData;
import ru.spbu.inventory.model.Location;
import ru.spbu.inventory.service.LocationService;
import ru.spbu.inventory.util.exception.NotFoundException;
import ru.spbu.inventory.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spbu.inventory.TestUtil.readFromJson;
import static ru.spbu.inventory.TestUtil.readFromJsonMvcResult;
import static ru.spbu.inventory.UserTestData.USER;
import static ru.spbu.inventory.LocationsTestData.*;

class LocationRestControllerTest extends AbstractControllerTest {

    @Autowired
    private LocationService locationService;

    LocationRestControllerTest() {
        super(LocationRestController.REST_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(LOCATION_MATCHERS.contentJson(LOCATIONS));
    }

    @Test
    void get() throws Exception {
        perform(doGet(LOCATION1_ID).basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> LOCATION_MATCHERS.assertMatch(readFromJsonMvcResult(result, Location.class), LOCATION1));
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet(LOCATION1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(LOCATION1_ID - 500).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void addWithLocation() throws Exception {
        Location newLocation = LocationsTestData.getNew();
        ResultActions action = perform(doPost().jsonBody(newLocation).basicAuth(USER));

        Location added = readFromJson(action, Location.class);
        Integer newId = added.getId();
        newLocation.setId(newId);
        LOCATION_MATCHERS.assertMatch(added, newLocation);
        LOCATION_MATCHERS.assertMatch(locationService.get(newId), newLocation);
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(LOCATION1_ID).basicAuth(USER))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> locationService.get(LOCATION1_ID));
    }

    @Test
    void update() throws Exception {
        Location updated = LocationsTestData.getUpdated();
        perform(doPut(LOCATION1_ID).jsonBody(updated).basicAuth(USER))
                .andExpect(status().isNoContent());
        LOCATION_MATCHERS.assertMatch(locationService.get(LOCATION1_ID), updated);
    }
}