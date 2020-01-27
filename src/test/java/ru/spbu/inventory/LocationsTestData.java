package ru.spbu.inventory;

import ru.spbu.inventory.model.Location;

import java.util.List;

import static ru.spbu.inventory.model.AbstractBaseEntity.START_SEQ;

public class LocationsTestData {
    public static final int LOCATION1_ID = START_SEQ + 2;

    public static final Location LOCATION1 = new Location(LOCATION1_ID, "ats_peterhoff", "Petrodvorets", "Botanicheskaya", "27B", 16);
    public static final Location LOCATION2 = new Location(LOCATION1_ID + 1, "germozona", "Saint-Petersburg", "Universitetskaya", "7-9-11", 115);
    public static final Location LOCATION3 = new Location(LOCATION1_ID + 2, "admin", "Saint-Petersburg", "Himichesky", "5P", 114);

    public static final List<Location> LOCATIONS = List.of(LOCATION1, LOCATION2, LOCATION3);

    public static Location getNew() {
        return new Location(null, "test_location", "City", "Street", "N", 32);
    }

    public static Location getUpdated() {
        return new Location(LOCATION1_ID, "updated_name", LOCATION1.getCity()
                , LOCATION1.getStreet(), LOCATION1.getBuilding(), LOCATION1.getRoom());
    }

    public static TestMatchers<Location> LOCATION_MATCHERS = TestMatchers.useFieldsComparator(Location.class, "devices");
}
