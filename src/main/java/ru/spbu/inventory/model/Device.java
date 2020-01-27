package ru.spbu.inventory.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "devices")
public class Device extends AbstractBaseEntity {

    @Column(name = "dnsname", nullable = false, unique = true)
    @NotBlank
    @Size(min =3, max = 50)
    private String dnsName;

    @Column(name = "model", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    private String modelName;

    @Column(name = "serial", unique = true)
    private String serialNumber;

    @Column(name = "inventory")
    private String inventoryNumber;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "manufactured")
    private Date manufacturedDate;

    @NotNull
    @Column
    @NotBlank
    @Size(min = 5, max = 200)
    private String description;

    @NotNull
    @NotBlank
    @Column(name = "contacts")
    @Size(min = 5, max = 100)
    private String contacts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @NotNull
    private Location location;

    public Device() {
    }

    public Device(String dnsName, String modelName, String serialNumber, String inventoryNumber, Date manufacturedDate, String description, String contacts) {
        this.dnsName = dnsName;
        this.modelName = modelName;
        this.serialNumber = serialNumber;
        this.inventoryNumber = inventoryNumber;
        this.manufacturedDate = manufacturedDate;
        this.description = description;
        this.contacts = contacts;
    }

    public Device(Integer id, String dnsName, String modelName, String serialNumber, String inventoryNumber, Date manufacturedDate, String description, String contacts) {
        super(id);
        this.dnsName = dnsName;
        this.modelName = modelName;
        this.serialNumber = serialNumber;
        this.inventoryNumber = inventoryNumber;
        this.manufacturedDate = manufacturedDate;
        this.description = description;
        this.contacts = contacts;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Date getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(Date manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Device{" +
                "dnsName='" + dnsName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", manufacturedDate=" + manufacturedDate +
                ", description='" + description + '\'' +
                ", contacts='" + contacts + '\'' +
                ", id=" + id +
                '}';
    }
}
