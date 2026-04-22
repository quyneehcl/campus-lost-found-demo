package com.lostfound.demo.services;

import com.lostfound.demo.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemSearcher - Provides search and filtering functionalities using Linear Search.
 * All operations are implemented using linear search (basic for loops),
 * ensuring clarity and compliance with assignment constraints.
 * Each method returns a new list, avoiding unintended side effects on the original data.
 *
 * @author Nguyen Minh Quyen
 */
public class ItemSearcher {

    /**
     * Search items by keyword using Linear Search.
     * Checks name, description, location, and category fields.
     * If keyword is empty or null, returns a copy of all items.
     *
     * @param items   the list of items to search
     * @param keyword the search keyword
     * @return a new list of matching items
     */
    public List<Item> searchByKeyword(List<Item> items, String keyword) {
        List<Item> result = new ArrayList<>();

        if (keyword == null || keyword.trim().isEmpty()) {
            result.addAll(items);
            return result;
        }

        String query = keyword.toLowerCase().trim();

        for (Item item : items) {
            if (matchesKeyword(item, query)) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Check if an item matches the given keyword by examining multiple fields.
     */
    private boolean matchesKeyword(Item item, String query) {
        if (item.getName() != null && item.getName().toLowerCase().contains(query)) {
            return true;
        }
        if (item.getDescription() != null && item.getDescription().toLowerCase().contains(query)) {
            return true;
        }
        if (item.getLocation() != null && item.getLocation().toLowerCase().contains(query)) {
            return true;
        }
        if (item.getCategory() != null && item.getCategory().toLowerCase().contains(query)) {
            return true;
        }
        return false;
    }

    /**
     * Filter items by type using Linear Search.
     *
     * @param items the list of items to filter
     * @param type  the type to filter by ("lost" or "found")
     * @return a new list of items matching the given type
     */
    public List<Item> filterByType(List<Item> items, String type) {
        List<Item> result = new ArrayList<>();

        if (type == null || type.trim().isEmpty()) {
            result.addAll(items);
            return result;
        }

        String target = type.toLowerCase().trim();

        for (Item item : items) {
            if (item.getItemType().equals(target)) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Filter items by category using Linear Search.
     *
     * @param items    the list of items to filter
     * @param category the category to filter by (e.g., "Electronics", "Bags")
     * @return a new list of items matching the given category
     */
    public List<Item> filterByCategory(List<Item> items, String category) {
        List<Item> result = new ArrayList<>();

        if (category == null || category.trim().isEmpty()) {
            result.addAll(items);
            return result;
        }

        String target = category.trim();

        for (Item item : items) {
            if (item.getCategory() != null && item.getCategory().equalsIgnoreCase(target)) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Filter items by status using Linear Search.
     *
     * @param items  the list of items to filter
     * @param status the status to filter by ("active" or "returned")
     * @return a new list of items matching the given status
     */
    public List<Item> filterByStatus(List<Item> items, String status) {
        List<Item> result = new ArrayList<>();

        if (status == null || status.trim().isEmpty()) {
            result.addAll(items);
            return result;
        }

        String target = status.toLowerCase().trim();

        for (Item item : items) {
            if (item.getStatus() != null && item.getStatus().equals(target)) {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Find an item by its ID using Linear Search.
     *
     * @param items the list of items to search
     * @param id    the ID to look up
     * @return the matching item, or null if not found
     */
    public Item findById(List<Item> items, String id) {
        if (id == null) {
            return null;
        }

        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }
}
