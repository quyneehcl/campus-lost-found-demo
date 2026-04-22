package com.lostfound.demo.services;

import com.lostfound.demo.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemSorter - Implements the Merge Sort algorithm to sort items by date
 * in descending order (newest first).
 * The algorithm recursively divides the list and merges sorted sublists
 * using LocalDate comparisons.
 * No built-in sorting utilities are used, demonstrating a manual implementation
 * of a fundamental Divide and Conquer algorithm. Complexity: O(n log n).
 *
 * @author Ngu Hoang Nguyen
 */
public class ItemSorter {

    /**
     * Sort the list of items by date in descending order (newest first)
     * using Merge Sort algorithm.
     *
     * @param list  the list of items to sort
     * @param left  the starting index
     * @param right the ending index
     */
    public void mergeSortByDateDesc(List<Item> list, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Recursively sort the left half
            mergeSortByDateDesc(list, left, mid);

            // Recursively sort the right half
            mergeSortByDateDesc(list, mid + 1, right);

            // Merge the two sorted halves
            merge(list, left, mid, right);
        }
    }

    /**
     * Merge two sorted sublists. Items with newer dates come first.
     */
    private void merge(List<Item> list, int left, int mid, int right) {
        // Create temporary lists for left and right halves
        List<Item> leftList = new ArrayList<>();
        List<Item> rightList = new ArrayList<>();

        // Copy data to temporary lists
        for (int i = left; i <= mid; i++) {
            leftList.add(list.get(i));
        }
        for (int j = mid + 1; j <= right; j++) {
            rightList.add(list.get(j));
        }

        // Merge the temporary lists back into the original list
        int i = 0;   // Index for leftList
        int j = 0;   // Index for rightList
        int k = left; // Index for merged list

        while (i < leftList.size() && j < rightList.size()) {
            // Compare dates: newer (later) date comes first (descending order)
            if (leftList.get(i).getDate().isAfter(rightList.get(j).getDate())) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftList
        while (i < leftList.size()) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        // Copy remaining elements of rightList
        while (j < rightList.size()) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }
}
