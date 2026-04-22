package com.lostfound.demo.services;

import com.lostfound.demo.models.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * ItemCleaner - Automatically removes reports older than 30 days using a Min-Heap.
 * Uses a PriorityQueue with an ascending Comparator based on date,
 * so the oldest item is always at the top, facilitating efficient data cleanup.
 * Demonstrates the use of Min-Heap data structure for priority-based processing.
 *
 * @author Hoang Nguyen Giap
 */
public class ItemCleaner {

    /**
     * Build a Min-Heap based on item dates (oldest at the top).
     *
     * @param items the list of items to add to the heap
     * @return a PriorityQueue (Min-Heap) with items ordered by date ascending
     */
    public PriorityQueue<Item> buildMinHeapByDate(List<Item> items) {
        // Comparator: ascending order by date (oldest first at the top of heap)
        PriorityQueue<Item> minHeap = new PriorityQueue<>(new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return item1.getDate().compareTo(item2.getDate());
            }
        });

        // Add all items to the queue
        for (Item item : items) {
            minHeap.add(item);
        }

        return minHeap;
    }

    /**
     * Remove reports older than 30 days from the heap.
     * Uses a while loop to check the element at the top of the min-heap.
     * If the date is older than 30 days, it is removed using .poll().
     *
     * @param heap the Min-Heap of items sorted by date
     * @return the number of items removed
     */
    public int removeOldReports(PriorityQueue<Item> heap) {
        int removedCount = 0;
        LocalDate cutoff = LocalDate.now().minusDays(30);

        while (!heap.isEmpty()) {
            Item topItem = heap.peek();

            // If the oldest item is still within 30 days, stop
            if (topItem.getDate().isAfter(cutoff) || topItem.getDate().isEqual(cutoff)) {
                break;
            }

            // Remove the item (older than 30 days)
            heap.poll();
            removedCount++;
        }

        return removedCount;
    }

    /**
     * Clean a list of items by removing those older than 30 days.
     * This is a convenience method that combines buildMinHeapByDate and removeOldReports.
     *
     * @param items the list of items to clean
     * @return a new list with only items from the last 30 days
     */
    public List<Item> cleanOldItems(List<Item> items) {
        if (items == null || items.isEmpty()) {
            return new ArrayList<>();
        }

        PriorityQueue<Item> minHeap = buildMinHeapByDate(items);

        // Build a list of items to keep (not older than 30 days)
        List<Item> remainingItems = new ArrayList<>();
        LocalDate cutoff = LocalDate.now().minusDays(30);

        while (!minHeap.isEmpty()) {
            Item item = minHeap.poll();
            if (item.getDate().isAfter(cutoff) || item.getDate().isEqual(cutoff)) {
                remainingItems.add(item);
            }
        }

        return remainingItems;
    }
}
