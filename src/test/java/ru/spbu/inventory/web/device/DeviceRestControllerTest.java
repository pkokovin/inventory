package ru.spbu.inventory.web.device;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.spbu.inventory.DevicesTestData;
import ru.spbu.inventory.model.Device;
import ru.spbu.inventory.service.DeviceService;
import ru.spbu.inventory.util.exception.NotFoundException;
import ru.spbu.inventory.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.spbu.inventory.DevicesTestData.*;
import static ru.spbu.inventory.LocationsTestData.LOCATION1_ID;
import static ru.spbu.inventory.TestUtil.readFromJson;
import static ru.spbu.inventory.TestUtil.readFromJsonMvcResult;
import static ru.spbu.inventory.UserTestData.USER;

class DeviceRestControllerTest extends AbstractControllerTest {

    @Autowired
    private DeviceService deviceService;

    DeviceRestControllerTest() {
        super(DeviceRestController.REST_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DEVICE_MATCHERS.contentJson(DEVICES));
    }

    @Test
    void getAllAtLocation() throws Exception {
        perform(doGet("location" + "/" + LOCATION1_ID).basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DEVICE_MATCHERS.contentJson(LOCATION1_DEVICES));
    }

    @Test
    void get() throws Exception {
        perform(doGet(DEVICE1_ID).basicAuth(USER))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> DEVICE_MATCHERS.assertMatch(readFromJsonMvcResult(result, Device.class), DEVICE01));
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet(DEVICE1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getNotFound() throws Exception {
        perform(doGet(DEVICE1_ID - 500).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void addWithLocation() throws Exception {
        Device newDevice = DevicesTestData.getNew();
        ResultActions action = perform(doPost(String.valueOf(LOCATION1_ID)).jsonBody(newDevice).basicAuth(USER));

        Device added = readFromJson(action, Device.class);
        Integer newId = added.getId();
        newDevice.setId(newId);
        DEVICE_MATCHERS.assertMatch(added, newDevice);
        DEVICE_MATCHERS.assertMatch(deviceService.get(newId), newDevice);
    }

    @Test
    void addWithLocationOnWrongAddress() throws Exception {
        Device newDevice = DevicesTestData.getNew();
        perform(doPost(String.valueOf(LOCATION1_ID - 50)).jsonBody(newDevice).basicAuth(USER))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        perform(doDelete(DEVICE1_ID).basicAuth(USER))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> deviceService.get(DEVICE1_ID));
    }

    @Test
    void update() throws Exception {
        Device updated = DevicesTestData.getUpdated();
        perform(doPut().jsonBody(updated).basicAuth(USER))
                .andExpect(status().isNoContent());
        DEVICE_MATCHERS.assertMatch(deviceService.get(DEVICE1_ID), updated);
    }
}