package com.lostfound.demo.models;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * FoundItem - Concrete subclass of Item representing a found item report.
 * Demonstrates Inheritance by extending Item and overriding all abstract methods.
 * When stored in a List&lt;Item&gt;, method calls like getItemType() are resolved
 * at runtime, illustrating Polymorphism.
 *
 * @author Ngu Hoang Nguyen
 */
public class FoundItem extends Item {

    /**
     * Constructor for FoundItem. Calls super with all 9 parameters.
     * Status is automatically set to "active" by the parent class.
     */
    public FoundItem(String id, String name, String category, String description,
                     String location, LocalDate date, String contactInfo,
                     String imageUrl, String reportedBy) {
        super(id, name, category, description, location, date, contactInfo, imageUrl, reportedBy);
    }

    @Override
    public String getItemType() {
        return "found";
    }

    @Override
    public String getSummary() {
        return "[FOUND] " + getName() +
                " | Category: " + getCategory() +
                " | Location: " + getLocation() +
                " | Date: " + getDate();
    }

    @Override
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("name", getName());
        map.put("category", getCategory());
        map.put("description", getDescription());
        map.put("location", getLocation());
        map.put("date", getDate() != null ? getDate().toString() : null);
        map.put("contactInfo", getContactInfo());
        map.put("status", getStatus());
        map.put("imageUrl", getImageUrl());
        map.put("reportedBy", getReportedBy());
        map.put("type", getItemType());
        return map;
    }
}
