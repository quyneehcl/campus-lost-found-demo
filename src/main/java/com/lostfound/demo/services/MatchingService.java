package com.lostfound.demo.services;

import com.lostfound.demo.models.FoundItem;
import com.lostfound.demo.models.Item;
import com.lostfound.demo.models.LostItem;
import com.lostfound.demo.models.MatchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * MatchingService - Core component responsible for matching lost and found items.
 * Uses a weighted scoring system based on four criteria:
 *   - Category (40%)
 *   - Location (30%)
 *   - Name (20%)
 *   - Description (10%)
 * A custom similarity function evaluates string similarity through exact matches,
 * substring checks, and word overlap.
 * Candidates are scored and inserted into a PriorityQueue (max-heap),
 * ensuring results are returned in descending order of relevance.
 * Demonstrates Polymorphism through the Comparable interface of MatchResult.
 *
 * @author Nguyen The Minh Duc
 */
public class MatchingService {

    /**
     * Find matching lost items for a given found item using weighted scoring
     * and a PriorityQueue (max-heap) to rank results.
     *
     * @param foundItem the found item to find matches for
     * @param allItems  the complete list of all items in the system
     * @return a list of MatchResult objects sorted by score (descending)
     */
    public List<MatchResult> findMatches(FoundItem foundItem, List<Item> allItems) {
        PriorityQueue<MatchResult> maxHeap = new PriorityQueue<>();
        String targetId = foundItem.getId();
        String targetType = "lost"; // found item looks for lost items

        for (Item item : allItems) {
            if (!item.getItemType().equals(targetType)) continue;
            if (!item.getStatus().equals("active")) continue;
            if (item.getId().equals(targetId)) continue;

            double score = calculateScore(foundItem.getCategory(), item.getCategory(),
                    foundItem.getLocation(), item.getLocation(),
                    foundItem.getName(), item.getName(),
                    foundItem.getDescription(), item.getDescription());

            if (score > 0) {
                maxHeap.add(new MatchResult(targetId, item.getId(),
                        foundItem.getName(), item.getName(), item.getItemType(),
                        item.getCategory(), item.getLocation(), item.getContactInfo(), score));
            }
        }

        List<MatchResult> results = new ArrayList<>();
        while (!maxHeap.isEmpty()) results.add(maxHeap.poll());
        return results;
    }

    /**
     * Find matching found items for a given lost item using weighted scoring
     * and a PriorityQueue (max-heap) to rank results.
     *
     * @param lostItem  the lost item to find matches for
     * @param allItems  the complete list of all items in the system
     * @return a list of MatchResult objects sorted by score (descending)
     */
    public List<MatchResult> findMatchesForLost(LostItem lostItem, List<Item> allItems) {
        PriorityQueue<MatchResult> maxHeap = new PriorityQueue<>();
        String targetId = lostItem.getId();
        String targetType = "found"; // lost item looks for found items

        for (Item item : allItems) {
            if (!item.getItemType().equals(targetType)) continue;
            if (!item.getStatus().equals("active")) continue;
            if (item.getId().equals(targetId)) continue;

            double score = calculateScore(lostItem.getCategory(), item.getCategory(),
                    lostItem.getLocation(), item.getLocation(),
                    lostItem.getName(), item.getName(),
                    lostItem.getDescription(), item.getDescription());

            if (score > 0) {
                maxHeap.add(new MatchResult(targetId, item.getId(),
                        lostItem.getName(), item.getName(), item.getItemType(),
                        item.getCategory(), item.getLocation(), item.getContactInfo(), score));
            }
        }

        List<MatchResult> results = new ArrayList<>();
        while (!maxHeap.isEmpty()) results.add(maxHeap.poll());
        return results;
    }

    /**
     * Calculate weighted similarity score between two items.
     * Category (40%), Location (30%), Name (20%), Description (10%)
     */
    private double calculateScore(String catA, String catB, String locA, String locB,
                                   String nameA, String nameB, String descA, String descB) {
        double score = 0;
        if (catA != null && catB != null && catA.equalsIgnoreCase(catB)) score += 40;
        score += stringSimilarity(locA, locB) * 30;
        score += stringSimilarity(nameA, nameB) * 20;
        score += stringSimilarity(descA, descB) * 10;
        return Math.round(score * 100.0) / 100.0;
    }

    /**
     * Calculate string similarity between two strings using a multi-level approach.
     * Handles null values gracefully.
     *
     * Level 1: Exact match (case-insensitive) -> returns 1.0
     * Level 2: One string contains the other -> returns 0.7
     * Level 3: Word overlap using nested loop -> similarity = 0.5 * (common words / max words)
     *
     * @param a the first string
     * @param b the second string
     * @return a similarity score between 0.0 and 1.0
     */
    private double stringSimilarity(String a, String b) {
        // Handle null cases
        if (a == null || b == null) {
            return 0;
        }

        if (a.trim().isEmpty() || b.trim().isEmpty()) {
            return 0;
        }

        String lowerA = a.toLowerCase().trim();
        String lowerB = b.toLowerCase().trim();

        // Level 1: Exact match
        if (lowerA.equals(lowerB)) {
            return 1.0;
        }

        // Level 2: One string contains the other
        if (lowerA.contains(lowerB) || lowerB.contains(lowerA)) {
            return 0.7;
        }

        // Level 3: Word overlap using nested loop
        String[] wordsA = lowerA.split("\\s+");
        String[] wordsB = lowerB.split("\\s+");

        int commonWords = 0;

        for (String wordA : wordsA) {
            for (String wordB : wordsB) {
                if (wordA.equals(wordB)) {
                    commonWords++;
                    break; // Each word in A counted once
                }
            }
        }

        int maxWords = Math.max(wordsA.length, wordsB.length);
        if (maxWords == 0) {
            return 0;
        }

        return 0.5 * ((double) commonWords / maxWords);
    }
}
