package ru.spbu.inventory.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;
import ru.spbu.inventory.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

//The main entity class. Entities to count and store at database.
@Entity
@Table(name = "devices", uniqueConstraints = {@UniqueConstraint(columnNames = "dnsname", name = "devices_unuque_dnsname_idx")})
public class Device extends AbstractBaseEntity {
//    every device should have dns name (even if it is passive network device, or just active but without management interface)
    @Column(name = "dnsname", nullable = false, unique = true)
    @NotBlank
    @Size(min =3, max = 50)
    @SafeHtml(groups = {View.Web.class})
    private String dnsName;

//    every device should have model name (even if noname device)
    @Column(name = "model", nullable = false)
    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml(groups = {View.Web.class})
    private String modelName;

//    serial number unique if exist
    @Column(name = "serial", unique = true)
    @SafeHtml(groups = {View.Web.class})
    private String serialNumber;

//    inventory number may be one for two devices with different serial numbers
    @Column(name = "inventory")
    @SafeHtml(groups = {View.Web.class})
    private String inventoryNumber;

//    manufactured date, default date of install
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "manufactured")
    private Date manufacturedDate;

//    short description of device
    @NotNull
    @Column
    @NotBlank
    @Size(min = 5, max = 200)
    @SafeHtml(groups = {View.Web.class})
    private String description;

//    contacts of responsible person
    @NotNull
    @NotBlank
    @Column(name = "contacts")
    @Size(min = 5, max = 100)
    @SafeHtml(groups = {View.Web.class})
    private String contacts;

//    disposition of device
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Location location;

    public Device() {
    }

    public Device(String dnsName, String modelName, String serialNumber, String inventoryNumber, Date manufacturedDate, String description, String contacts) {
       this(null, dnsName, modelName, serialNumber, inventoryNumber, manufacturedDate, description, contacts);
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
