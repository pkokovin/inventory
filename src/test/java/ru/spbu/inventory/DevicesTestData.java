package ru.spbu.inventory;


import ru.spbu.inventory.model.Device;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

import static ru.spbu.inventory.model.AbstractBaseEntity.START_SEQ;

public class DevicesTestData {

    public static final int DEVICE1_ID = START_SEQ + 5;

    public static final Device DEVICE01 = new Device(DEVICE1_ID, "ats.ad.pu.ru", "DELL RX750"
            , "dls8989008kkh788", "IN77/34.99899873", LocalDateTime.of(2013, 6, 1, 00, 00, 00)
            , "punk ats", "+78123334455 nik@pu.ru");
    public static final Device DEVICE02 = new Device(DEVICE1_ID + 1, "ns05.ad.pu.ru", "SUPERMICRO N560"
            , "sm876fff98789799", "IN45/34.87457376", LocalDateTime.of(2010, 5, 1, 00, 00, 00)
            , "punk dns", "adminpunk@pu.ru");
    public static final Device DEVICE03 = new Device(DEVICE1_ID + 2, "srvsyn01.ad.pu.ru", "SYNOLOGY RS2416RP"
            , "sy127834587875", "IN37/45.6575675", LocalDateTime.of(2015, 4, 1, 00, 00, 00)
            , "thirst storage", "+78125555555 admin@pu.ru");
    public static final Device DEVICE04 = new Device(DEVICE1_ID + 3, "srvqnap01.ad.pu.ru", "QNAP TVS-EC1271U-RP"
            , "q127834dfjh587875", "IN33/234.234234", LocalDateTime.of(2016, 7, 1, 00, 00, 00)
            , "second storage", "admin@pu.ru");
    public static final Device DEVICE05 = new Device(DEVICE1_ID + 4, "huawei01.ad.pu.ru", "HUAWEI RH2288H"
            , "hw989879sr98473", "IN37/54.8734875", LocalDateTime.of(2017, 9, 1, 00, 00, 00)
            , "vsan 01", "admin@pu.ru");
    public static final Device DEVICE06 = new Device(DEVICE1_ID + 5, "node3.pu.ru", "CISCO 3750"
            , "830845874HH345", "IN77/33.3453543", LocalDateTime.of(2012, 3, 1, 00, 00, 00)
            , "core network 3750sw", "nocadm@pu.ru");

    public static final List<Device> DEVICES = List.of(DEVICE01, DEVICE02, DEVICE03, DEVICE04, DEVICE05, DEVICE06);

    public static final List<Device> LOCATION1_DEVICES = List.of(DEVICE01, DEVICE02);

    public static Device getNew() {
        return new Device(null, "new.pu.ru", "ELTEX", "XX11", "IN01", LocalDateTime.of(2019, 10, 9, 0, 0, 0)
                , "new dev", "new contact");
    }

    public static Device getUpdated() {
        return new Device(DEVICE1_ID, DEVICE01.getDnsName(), DEVICE01.getModelName(), DEVICE01.getSerialNumber()
                , DEVICE01.getInventoryNumber(), DEVICE01.getManufacturedDate(), "UPDATED DESCRIPTION", DEVICE01.getContacts());
    }

    public static TestMatchers<Device> DEVICE_MATCHERS = TestMatchers.useFieldsComparator(Device.class, "location");
}
