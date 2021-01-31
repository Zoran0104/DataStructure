package com.zoran.tree.Sort;

public class SelectionSort {
    public static void selectionSort(int[] array) {
        int minIndex;
        for (int i = 0; i < array.length; i++) {
            minIndex = i;
            for (int j = i+1; j < array.length ; j++) {
                if (array[j] <= array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tmp = array[minIndex];
                array[i] = array[minIndex];
                array[minIndex] = tmp;
            }
        }
    }
}
