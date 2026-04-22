package com.lostfound.demo.services;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import com.lostfound.demo.models.MatchResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ItemService - Central service for managing items in the Lost & Found system.
 * Acts as an in-memory repository with full CRUD operations.
 * Integrates ItemSearcher, ItemSorter, and MatchingService to provide
 * comprehensive item management functionality.
 *
 * @author Nguyen Minh Quyen
 */
public class ItemService {

    private List<Item> items;
    private ItemSearcher searcher;
    private ItemSorter sorter;
    private MatchingService matchingService;

    public ItemService() {
        this.items = new ArrayList<>();
        this.searcher = new ItemSearcher();
        this.sorter = new ItemSorter();
        this.matchingService = new MatchingService();
    }

    // === CRUD Operations ===

    /**
     * Add a new item to the system.
     */
    public Item addItem(Item item) {
        if (item.getId() == null) {
            item.setId(UUID.randomUUID().toString());
        }
        items.add(item);
        return item;
    }

    /**
     * Create a LostItem and add it to the system.
     */
    public LostItem addLostItem(String name, String category, String description,
                                String location, LocalDate date, String contactInfo,
                                String imageUrl, String reportedBy) {
        String id = UUID.randomUUID().toString();
        LostItem item = new LostItem(id, name, category, description, location,
                date, contactInfo, imageUrl, reportedBy);
        items.add(item);
        return item;
    }

    /**
     * Create a FoundItem and add it to the system.
     */
    public FoundItem addFoundItem(String name, String category, String description,
                                  String location, LocalDate date, String contactInfo,
                                  String imageUrl, String reportedBy) {
        String id = UUID.randomUUID().toString();
        FoundItem item = new FoundItem(id, name, category, description, location,
                date, contactInfo, imageUrl, reportedBy);
        items.add(item);
        return item;
    }

    /**
     * Get all items sorted by date descending (newest first).
     */
    public List<Item> getAllItems() {
        if (items.isEmpty()) {
            return new ArrayList<>();
        }

        // Create a copy to avoid modifying the original
        List<Item> sortedItems = new ArrayList<>(items);

        if (sortedItems.size() > 1) {
            sorter.mergeSortByDateDesc(sortedItems, 0, sortedItems.size() - 1);
        }

        return sortedItems;
    }

    /**
     * Get a single item by ID.
     */
    public Item getItemById(String id) {
        return searcher.findById(items, id);
    }

    /**
     * Delete an item by ID.
     */
    public boolean deleteItem(String id) {
        Item item = searcher.findById(items, id);
        if (item != null) {
            items.remove(item);
            return true;
        }
        return false;
    }

    /**
     * Mark an item as returned by ID.
     */
    public boolean markAsReturned(String id) {
        Item item = searcher.findById(items, id);
        if (item != null) {
            item.setStatus("returned");
            return true;
        }
        return false;
    }

    // === Search and Filter ===

    /**
     * Search items by keyword.
     */
    public List<Item> searchItems(String keyword) {
        List<Item> found = searcher.searchByKeyword(items, keyword);

        if (found.size() > 1) {
            sorter.mergeSortByDateDesc(found, 0, found.size() - 1);
        }

        return found;
    }

    /**
     * Filter items by type.
     */
    public List<Item> filterByType(String type) {
        List<Item> found = searcher.filterByType(items, type);

        if (found.size() > 1) {
            sorter.mergeSortByDateDesc(found, 0, found.size() - 1);
        }

        return found;
    }

    /**
     * Filter items by category.
     */
    public List<Item> filterByCategory(String category) {
        List<Item> found = searcher.filterByCategory(items, category);

        if (found.size() > 1) {
            sorter.mergeSortByDateDesc(found, 0, found.size() - 1);
        }

        return found;
    }

    /**
     * Get items reported by a specific user.
     */
    public List<Item> getItemsByReporter(String reportedBy) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getReportedBy() != null && item.getReportedBy().equals(reportedBy)) {
                result.add(item);
            }
        }

        if (result.size() > 1) {
            sorter.mergeSortByDateDesc(result, 0, result.size() - 1);
        }

        return result;
    }

    // === Matching ===

    /**
     * Find matching lost items for a given found item.
     */
    public List<MatchResult> findMatches(FoundItem foundItem) {
        return matchingService.findMatches(foundItem, items);
    }

    /**
     * Find matches for an item by its ID.
     * For found items, searches for matching lost items.
     * For lost items, searches for matching found items.
     */
    public List<MatchResult> findMatchesById(String itemId) {
        Item item = searcher.findById(items, itemId);
        if (item instanceof FoundItem) {
            return matchingService.findMatches((FoundItem) item, items);
        } else if (item instanceof LostItem) {
            return matchingService.findMatchesForLost((LostItem) item, items);
        }
        return new ArrayList<>();
    }

    // === Utility ===

    /**
     * Get the total number of items.
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Get the internal items list (for ItemCleaner).
     */
    public List<Item> getItemsList() {
        return items;
    }
}
