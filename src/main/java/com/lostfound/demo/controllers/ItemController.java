package com.lostfound.demo.controllers;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import com.lostfound.demo.models.MatchResult;
import com.lostfound.demo.services.ItemSearcher;
import com.lostfound.demo.services.ItemService;
import com.lostfound.demo.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * ItemController - Handles REST API requests from the frontend for item management.
 * Provides endpoints for creating, reading, updating, deleting items,
 * as well as searching and matching functionality.
 *
 * @author Phan Minh Duc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MatchingService matchingService;

    @Autowired
    private ItemSearcher itemSearcher;

    /**
     * POST /api/items - Create a new item (lost or found).
     * Receives data from request body with all 9 fields including imageUrl and reportedBy.
     */
    @PostMapping("/items")
    public ResponseEntity<Map<String, Object>> createItem(@RequestBody Map<String, String> body) {
        try {
            String type = body.get("type");
            String name = body.get("name");
            String category = body.get("category");
            String description = body.get("description");
            String location = body.get("location");
            String contactInfo = body.get("contactInfo");
            String imageUrl = body.get("imageUrl");
            String reportedBy = body.get("reportedBy");

            // Parse date or use today
            LocalDate date;
            if (body.get("date") != null && !body.get("date").isEmpty()) {
                date = LocalDate.parse(body.get("date"));
            } else {
                date = LocalDate.now();
            }

            Item item;
            if ("found".equalsIgnoreCase(type)) {
                item = itemService.addFoundItem(name, category, description, location,
                        date, contactInfo, imageUrl, reportedBy);
            } else {
                item = itemService.addLostItem(name, category, description, location,
                        date, contactInfo, imageUrl, reportedBy);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Item reported successfully");
            response.put("item", item.toMap());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * GET /api/items - Get all items sorted by date descending (newest first).
     */
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getAllItems() {
        List<Item> items = itemService.getAllItems();

        List<Map<String, Object>> itemsMap = new ArrayList<>();
        for (Item item : items) {
            itemsMap.add(item.toMap());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", items.size());
        response.put("items", itemsMap);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/items/search?keyword=xxx - Search items by keyword.
     */
    @GetMapping("/items/search")
    public ResponseEntity<Map<String, Object>> searchItems(@RequestParam String keyword) {
        List<Item> items = itemService.searchItems(keyword);

        List<Map<String, Object>> itemsMap = new ArrayList<>();
        for (Item item : items) {
            itemsMap.add(item.toMap());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", items.size());
        response.put("items", itemsMap);
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/items/return/{id} - Mark an item as returned.
     */
    @PutMapping("/items/return/{id}")
    public ResponseEntity<Map<String, Object>> markAsReturned(@PathVariable String id) {
        boolean success = itemService.markAsReturned(id);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "Item marked as returned");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Item not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * DELETE /api/items/{id} - Delete an item by ID.
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> deleteItem(@PathVariable String id) {
        boolean success = itemService.deleteItem(id);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "Item deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Item not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * GET /api/items/matches/{id} - Find matching items for a found item.
     */
    @GetMapping("/items/matches/{id}")
    public ResponseEntity<Map<String, Object>> findMatches(@PathVariable String id) {
        List<MatchResult> matches = itemService.findMatchesById(id);

        List<Map<String, Object>> matchesMap = new ArrayList<>();
        for (MatchResult match : matches) {
            matchesMap.add(match.toMap());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", matches.size());
        response.put("matches", matchesMap);
        return ResponseEntity.ok(response);
    }
}
