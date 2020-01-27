package ru.spbu.inventory.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location extends AbstractNamedEntity {

    //named entity name = canonical name of place

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "building", nullable = false)
    private String building;

    @Column(name = "room")
    private Integer room;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    protected List<Device> devices;

    public Location() {
    }

    public Location(String cname, String city, String street, String building, Integer room) {
        this(null, cname, city, street, building, room);
    }

    public Location(Integer id, String cname, String city, String street, String building, Integer room) {
        super(id, cname);
        this.city = city;
        this.street = street;
        this.building = building;
        this.room = room;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public List<Device> getDevices() {
        return devices;
    }

    @Override
    public String toString() {
        return "Location{" +
                " id=" + id +
                ", name='" + name +
                ", city='" + city +
                ", street='" + street +
                ", building='" + building +
                ", room=" + room +
                '}';
    }
}
