package ru.spbu.inventory.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class DeviceSpecification implements Specification<Device> {
    private String name;
    private String model;
    private String serial;
    private String inventory;
    private LocalDateTime after;
    private LocalDateTime before;
    private String description;
    private String contacts;

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public void setAfter(LocalDateTime after) {
        this.after = after;
    }

    public void setBefore(LocalDateTime before) {
        this.before = before;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public Specification<Device> and(Specification<Device> other) {
        return null;
    }

    @Override
    public Specification<Device> or(Specification<Device> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<Device> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (name != null && !StringUtils.isEmpty(name)) {
            predicates.add(cb.like(cb.lower(root.get("dnsName")), "%" + name + "%"));
        }
        if (model != null && !StringUtils.isEmpty(model)) {
            predicates.add(cb.like(cb.lower(root.get("modelName")), "%" + model + "%"));
        }
        if (serial != null && !StringUtils.isEmpty(serial)) {
            predicates.add(cb.like(cb.lower(root.get("serialNumber")), "%" + serial + "%"));
        }
        if (inventory != null && !StringUtils.isEmpty(inventory)) {
            predicates.add(cb.like(cb.lower(root.get("inventoryNumber")), "%" + inventory + "%"));
        }
        if (after != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("manufacturedDate"), after));
        }
        if (before != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("manufacturedDate"), before));
        }
        if (description != null && !StringUtils.isEmpty(description)) {
            predicates.add(cb.like(cb.lower(root.get("description")), "%" + description + "%"));
        }
        if (contacts != null && !StringUtils.isEmpty(contacts)) {
            predicates.add(cb.like(cb.lower(root.get("contacts")), "%" + contacts + "%"));
        }

        return predicates.size() <= 0 ? null : cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
