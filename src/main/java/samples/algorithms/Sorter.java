package samples.algorithms;

import java.util.Arrays;

public class Sorter {

    public static boolean isSorted(byte[] arr) {
        for (int i = 0; i < arr.length - 1; i++) if (arr[i] > arr[i + 1]) return false;
        return true;
    }

    public static void bucketSort(byte... arr) {
        byte maxValue = arr[0];
        for (byte element : arr) if (element > maxValue) maxValue = element;
        int[] bucket = new int[maxValue + 1];

        for (byte anArr : arr) {
            bucket[anArr]++;
        }

        int outPos = 0;
        for (byte i = 0; i < bucket.length; i++) {
            for (int j = 0; j < bucket[i]; j++) {
                arr[outPos++] = i;
            }
        }
    }

    public static void heapSort(byte... arr) {
        int i;
        byte temp;
        for (i = arr.length / 2 - 1; i >= 0; i--) {
            shiftDown(arr, i, arr.length);
        }
        for (i = arr.length - 1; i >= 1; i--) {
            temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            shiftDown(arr, 0, i);
        }
    }

    public static void quickSort(int powerOf2OfThreadsAmount, byte... arr) {
        QuickSortTask.setMaxRecursionDepth(powerOf2OfThreadsAmount);
        QuickSortTask quickSortTask = new QuickSortTask(arr, 0, arr.length - 1);
        quickSortTask.start();
        try {
            quickSortTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mergeSort(int powerOf2OfThreadsAmount, byte... arr) {
        MergeSortTask.setMaxRecursionDepth(powerOf2OfThreadsAmount);
        MergeSortTask mergeSortTask = new MergeSortTask(arr);
        mergeSortTask.start();
        try {
            mergeSortTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void insertionSort(byte... arr) {
//        long iterationCounter = 0;
//        long innerIfIterationCounter = 0;
        for (int currentIndex = 1; currentIndex < arr.length; currentIndex++) {
            int indexToInsert = currentIndex;
            byte currentElement = arr[currentIndex];
            for (int innerCounter = currentIndex - 1; 0 <= innerCounter; innerCounter--) {
//                iterationCounter++;
                if (arr[innerCounter] > arr[currentIndex]) {
//                    innerIfIterationCounter++;
                    indexToInsert = innerCounter;
                } else {
                    break;
                }
            }
            if (indexToInsert < currentIndex) {
                System.arraycopy(arr, indexToInsert, arr, indexToInsert + 1, currentIndex - indexToInsert);
                arr[indexToInsert] = currentElement;
            }
        }
//        System.out.println("Iteration amount: " + iterationCounter);
//        System.out.println("inside IF iteration amount: " + innerIfIterationCounter);
    }

    public static void selectionSort(byte... arr) {
//        long iterationCounter = 0;
//        long innerIfIterationCounter = 0;
        for (int sortedAmount = 0; sortedAmount < arr.length; sortedAmount++) {
            int indexOfMax = 0;
            byte max = arr[indexOfMax];
            int indexForMax = arr.length - 1 - sortedAmount;
            for (int maxFinderCounter = 1; maxFinderCounter <= indexForMax; maxFinderCounter++) {
//                iterationCounter++;
                if (max < arr[maxFinderCounter]) {
//                    innerIfIterationCounter++;
                    indexOfMax = maxFinderCounter;
                    max = arr[maxFinderCounter];
                }
            }
            arr[indexOfMax] = arr[indexForMax];
            arr[indexForMax] = max;
        }
//        System.out.println("Iteration amount: " + iterationCounter);
//        System.out.println("inside IF iteration amount: " + innerIfIterationCounter);
    }

    public static void bubbleSort(byte... arr) {
//        long iterationCounter = 0;
//        long innerIfIterationCounter = 0;
        for (int finishIndex = arr.length - 1; finishIndex > 1; finishIndex--) {
            for (int i = 0; i < finishIndex; i++) {
//                iterationCounter++;
                if (arr[i] > arr[i + 1]) {
//                    innerIfIterationCounter++;
                    byte temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                }
            }
        }
//        System.out.println("Iteration amount: " + iterationCounter);
//        System.out.println("inside IF iteration amount: " + innerIfIterationCounter);
    }

    public static void bogoSort(byte[] arr) {
        while (!isSorted(arr)) shuffle(arr);
    }

    private static void shuffle(byte[] arr) {
        java.util.Random generator = new java.util.Random();
        for (int i = 0; i < arr.length; i++) {
            int swapPosition = generator.nextInt(arr.length);
            swap(arr, i, swapPosition);
        }
    }

    // and return the index j.
    private static void shiftDown(byte[] a, int i, int j) {
        boolean done = false;
        int maxChild;
        byte temp;
        while ((i * 2 + 1 < j) && (!done)) {
            if (i * 2 + 1 == j - 1)
                maxChild = i * 2 + 1;
            else if (a[i * 2 + 1] > a[i * 2 + 2])
                maxChild = i * 2 + 1;
            else
                maxChild = i * 2 + 2;
            if (a[i] < a[maxChild]) {
                temp = a[i];
                a[i] = a[maxChild];
                a[maxChild] = temp;
                i = maxChild;
            } else {
                done = true;
            }
        }
    }

    // split the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]


    private static void swap(byte[] a, int i, int j) {
        byte swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static final class QuickSortTask extends Thread implements Runnable {
        private final byte[] arr;
        private int left;
        private int right;
        private static int recursionDepth;
        private static int maxRecursionDepth;
        private static long iterationCounter;
        private static long innerIfIterationCounter;

        public QuickSortTask(byte[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            quickSort(arr, left, right);
//            System.out.println("Iteration amount: " + iterationCounter);
//            System.out.println("inside IF iteration amount: " + innerIfIterationCounter);
        }


        private void quickSort(byte[] main, int left, int right) {
            if (right <= left) return;
            int j = split(main, left, right);
            if (recursionDepth++ < maxRecursionDepth) {
                try {
                    QuickSortTask leftTask = new QuickSortTask(main, left, j - 1);
                    QuickSortTask rightTask = new QuickSortTask(main, j + 1, right);
                    leftTask.start();
                    rightTask.start();
                    leftTask.join();
                    rightTask.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                quickSort(main, left, j - 1);
                quickSort(main, j + 1, right);
            }
        }

        public QuickSortTask(byte[] arr) {
            this.arr = arr;
        }

        public byte[] getArr() {
            return arr;
        }

        public static void setMaxRecursionDepth(int maxRecursionDepth) {
            QuickSortTask.maxRecursionDepth = maxRecursionDepth;
        }

        private static int split(byte[] main, int left, int right) {
            int i = left;
            int j = right + 1;
            byte pivot = main[left];
            while (true) {
                while (main[++i] < pivot) if (i == right) break;
                while (pivot < main[--j]) if (j == left) break;
                if (i >= j) break;
                swap(main, i, j);
            }
            swap(main, left, j);
            return j;
        }
    }

    public static final class MergeSortTask extends Thread implements Runnable {
        private final byte[] arr;
        private static int recursionDepth;
        private static int maxRecursionDepth;
        private static long iterationCounter;
        private static long innerIfIterationCounter;

        @Override
        public void run() {
            recursiveMergeSort(arr);
//            System.out.println("Iteration amount: " + iterationCounter);
//            System.out.println("inside IF iteration amount: " + innerIfIterationCounter);
        }

        private void recursiveMergeSort(byte... main) {
            if (main.length < 2) return;
            int leftLength = main.length >>> 1;
            byte[] left = Arrays.copyOfRange(main, 0, leftLength);
            byte[] right = Arrays.copyOfRange(main, leftLength, main.length);
            if (recursionDepth++ < maxRecursionDepth) {
                try {
                    MergeSortTask leftTask = new MergeSortTask(left);
                    MergeSortTask rightTask = new MergeSortTask(right);
                    leftTask.start();
                    rightTask.start();
                    leftTask.join();
                    rightTask.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                recursiveMergeSort(left);
                recursiveMergeSort(right);
            }
            int leftCounter = 0;
            int rightCounter = 0;
            int mainCounter = 0;
            for (; leftCounter < left.length && rightCounter < right.length; mainCounter++) {
//                iterationCounter++;
                if (left[leftCounter] < right[rightCounter]) {
//                    innerIfIterationCounter++;
                    main[mainCounter] = left[leftCounter++];
                } else {
//                    innerIfIterationCounter++;
                    main[mainCounter] = right[rightCounter++];
                }
            }
            if (leftCounter < left.length) {
                System.arraycopy(left, leftCounter, main, mainCounter, left.length - leftCounter);
            } else if (rightCounter < right.length) {
                System.arraycopy(right, rightCounter, main, mainCounter, right.length - rightCounter);
            }
        }

        public MergeSortTask(byte[] arr) {
            this.arr = arr;
        }

        public byte[] getArr() {
            return arr;
        }

        public static void setMaxRecursionDepth(int maxRecursionDepth) {
            MergeSortTask.maxRecursionDepth = maxRecursionDepth;
        }
    }

    private Sorter() {
    }
}


//    public static void sort(int... arr) {
//        if (arr.length == 1) {
//            return;
//        }
//        int leftEnd = arr.length >>> 1;
//        int[] left = Arrays.copyOfRange(arr, 0, leftEnd);
//        int[] right = Arrays.copyOfRange(arr, leftEnd, arr.length);
//
//        sort(left);
//        sort(right);
//        int leftCounter = 0;
//        int rightCounter = 0;
//        int mainCounter = 0;
//        for (; leftCounter < left.length && rightCounter < right.length; mainCounter++) {
//            if (left[leftCounter] < right[rightCounter]) {
//                arr[mainCounter] = left[leftCounter++];
//            } else {
//                arr[mainCounter] = right[rightCounter++];
//            }
//        }
//        if (leftCounter < left.length) {
//            System.arraycopy(left, leftCounter, arr, mainCounter, left.length - leftCounter);
//        } else if (rightCounter < right.length) {
//            System.arraycopy(right, rightCounter, arr, mainCounter, right.length - rightCounter);
//        }
//    }


//    public static void recursiveQuickSort(int[] arr, int left, int right) {
//        // For Recusrion
//        if (left < right) {
//            int pivot = partition(arr, left, right);
//
//            if (pivot > 1)
//                recursiveQuickSort(arr, left, pivot - 1);
//
//            if (pivot + 1 < right)
//                recursiveQuickSort(arr, pivot + 1, right);
//        }
//    }
//
//    static public void iterativeMergeSort(int[] numbers, int left, int right) {
//        int mid;
//        if (right <= left)
//            return;
//
//        class MergePosInfo {
//            public int left;
//            public int mid;
//            public int right;
//        }
//
//        ArrayList<MergePosInfo> list1 = new ArrayList<>();
//        ArrayList<MergePosInfo> list2 = new ArrayList<>();
//
//        MergePosInfo info = new MergePosInfo();
//        info.left = left;
//        info.right = right;
//        info.mid = -1;
//
//        list1.add(info);
//
//        while (true) {
//            if (list1.size() == 0)
//                break;
//
//            left = list1.get(0).left;
//            right = list1.get(0).right;
//            list1.remove(0);
//
//            mid = (right + left) >>> 1;
//
//            if (left < right) {
//                MergePosInfo info2 = new MergePosInfo();
//                info2.left = left;
//                info2.right = right;
//                info2.mid = mid + 1;
//                list2.add(info2);
//
//                info.left = left;
//                info.right = mid;
//                list1.add(info);
//
//                info.left = mid + 1;
//                info.right = right;
//                list1.add(info);
//            }
//        }
//
//        for (MergePosInfo aList2 : list2) {
//            merge(numbers, aList2.left, aList2.mid, aList2.right);
//        }
//
//    }
//
//    static public void sort(int[] numbers, int left, int right) {
//        int mid;
//        if (right > left) {
//            mid = (right + left) >>> 1;
//            sort(numbers, left, mid);
//            sort(numbers, (mid + 1), right);
//            merge(numbers, left, (mid + 1), right);
//        }
//    }
//
//    static public void merge(int[] numbers, int left, int mid, int right) {
//        int[] temp = new int[25];
//        int i, left_end, num_elements, tmp_pos;
//
//        left_end = (mid - 1);
//        tmp_pos = left;
//        num_elements = (right - left + 1);
//
//        while ((left <= left_end) && (mid <= right)) {
//            if (numbers[left] <= numbers[mid])
//                temp[tmp_pos++] = numbers[left++];
//            else
//                temp[tmp_pos++] = numbers[mid++];
//        }
//
//        while (left <= left_end)
//            temp[tmp_pos++] = numbers[left++];
//
//        while (mid <= right)
//            temp[tmp_pos++] = numbers[mid++];
//
//        for (i = 0; i < num_elements; i++) {
//            numbers[right] = temp[right];
//            right--;
//        }
//    }
//
//    public static int partition(int[] numbers, int left, int right) {
//        int pivot = numbers[left];
//        while (true) {
//            while (numbers[left] < pivot)
//                left++;
//
//            while (numbers[right] > pivot)
//                right--;
//
//            if (left < right) {
//                int temp = numbers[right];
//                numbers[right] = numbers[left];
//                numbers[left] = temp;
//            } else {
//                return right;
//            }
//        }
//    }

//    public static void doMergeSortInThreads(int threadsAmount, int... main) {
//        System.out.println(Arrays.toString(main) + "\n");
//        List<SortTask> tasks = new ArrayList<>();
//        int length = main.length / threadsAmount;
//        int lastArrLength = length;
//        if (threadsAmount > 1) {
//            for (int i = 0; i < threadsAmount - 1; i++) {
//                int[] smallerArr = new int[length];
//                System.arraycopy(main, i * length, smallerArr, 0, length);
//                tasks.add(new SortTask(smallerArr));
//                System.out.print(Arrays.toString(smallerArr));
//            }
//            lastArrLength = main.length - threadsAmount * length;
//        }
//        int[] lastSmallerArr = new int[lastArrLength];
//        System.arraycopy(main, length - lastArrLength, lastSmallerArr, 0, lastArrLength);
//        SortTask lastTask = new SortTask(lastSmallerArr);
//        tasks.add(lastTask);
//        System.out.print(Arrays.toString(lastSmallerArr));
//        tasks.stream().forEach(t -> {
//            t.start();
//        });
//        tasks.stream().forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        if (threadsAmount > 1) {
//            for (int i = 0; i < threadsAmount - 1; i++) {
//                int[] arr = tasks.get(i).getArr();
//                System.arraycopy(arr, 0, main, i * length, length);
//            }
//            System.arraycopy(tasks.get(tasks.size() - 1).getArr(), 0, main, threadsAmount * length, lastArrLength);
//        } else {
//            System.arraycopy(tasks.iterator().next().getArr(), 0, main, 0, lastArrLength);
//        }
//        System.out.println("\n" + Arrays.toString(main));
//    }


//    public static void quickSort(byte... main) {
//        if (main.length < 2) return;
//        if (main.length == 2) {
//            if (main[0] > main[1]) {
//                byte temp;
//                temp = main[0];
//                main[0] = main[1];
//                main[1] = temp;
//            }
//            return;
//        }
//        if (main.length == 3) {
//            byte temp;
//            if (main[0] > main[1]) {
//                temp = main[0];
//                main[0] = main[1];
//                main[1] = temp;
//            }
//            if (main[1] > main[2]) {
//                temp = main[1];
//                main[1] = main[2];
//                main[2] = temp;
//            }
//            if (main[0] > main[1]) {
//                temp = main[0];
//                main[0] = main[1];
//                main[1] = temp;
//            }
//            return;
//        }
//
//        byte min = main[0];
//        byte max = main[main.length - 1];
//        for (int i = 0; i < main.length - 1; i++) {
//            if (main[i] < main[i + 1]) {
//                min = main[i];
//            } else {
//                max = main[i];
//            }
//        }
//        byte pivot = (byte) ((min + max) / 2);
//        int lessEqualCounter = 0;
//        for (byte element : main) {
//            if (element <= pivot) {
//                lessEqualCounter++;
//            }
//        }
////        Random pivotIndexGenerator = new Random();
////        int middle = main.length >>> 1;
////        do {
////            lessCounter = 0;
////            pivot = main[pivotIndexGenerator.nextInt(main.length)];
////            for (byte element : main) if (element < pivot) lessCounter++;
////        }
////        while (lessCounter <= middle - 0.25 * middle || middle + 0.25 < lessCounter);
//
//        byte[] left = new byte[lessEqualCounter];
//        byte[] right = new byte[main.length - lessEqualCounter];
//        int leftCounter = 0;
//        int rightCounter = 0;
//        for (byte element : main) {
//            if (element <= pivot) {
//                left[leftCounter++] = element;
//            } else {
//                right[rightCounter++] = element;
//            }
//        }
//        quickSort(left);
//        quickSort(right);
//        System.arraycopy(left, 0, main, 0, leftCounter);
//        System.arraycopy(right, 0, main, leftCounter, rightCounter);
//
//    }


//    private static void sortDemo() {
//        int quantity = 10;
//        byte[] arr = new byte[quantity];
////        Byte[] arr = new Byte[quantity];
//        java.util.Random generator = new java.util.Random();
//        for (int i = 0; i < quantity; i++) {
//            arr[i] = (byte) generator.nextInt(Byte.MAX_VALUE);
//        }
////---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
////        System.out.println(java.util.Arrays.toString(arr));
//        System.out.println("Sorted? - " + samples.algorithms.Sorter.isSorted(arr));
////        System.out.println("Sorted? - " + samples.algorithms.QuickSorter.isSorted(arr));
////---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//        long start = System.nanoTime();
////        samples.algorithms.MergeSorter.sort(arr);     // 10 000 ~ 35 ms,  100 000 ~ 150 ms,   1 000 000 ~ 1 s,    10 000 000 ~ 70 s
////        samples.algorithms.QuickSorter.sort(arr);     // 10 000 ~ 20 ms,  100 000 ~ 100 ms,   1 000 000 ~ 400 ms, 10 000 000 ~ 3.5 s, 100 000 000 ~ 30 s
////---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
////        samples.algorithms.Sorter.bucketSort(arr);    // 10 000 ~ 1 ms,   100 000 ~ 10 ms,    1 000 000 ~ 55 ms,  10 000 000 ~ 70 ms, 100 000 000 ~ 300 ms,   500 000 000 ~ 1.5 s
////        samples.algorithms.Sorter.quickSort(0, arr);  // 10 000 ~ 8 ms,   100 000 ~ 50 ms,    1 000 000 ~ 250 ms, 10 000 000 ~ 2 s,   100 000 000 ~ 18 s,     500 000 000 ~ 60 s
////        samples.algorithms.Sorter.heapSort(arr);      // 10 000 ~ 10 ms,  100 000 ~ 60 ms,    1 000 000 ~ 400 ms, 10 000 000 ~ 3.8 s, 100 000 000 ~ 35 s
////        samples.algorithms.Sorter.mergeSort(0, arr);  // 10 000 ~ 25 ms,  100 000 ~ 200 ms,   1 000 000 ~ 550 ms, 10 000 000 ~ 4 s,   100 000 000 ~ 38 s
////        samples.algorithms.Sorter.insertionSort(arr); // 10 000 ~ 60 ms,  100 000 ~ 3.5 s,    1 000 000 ~ 45 s
////        samples.algorithms.Sorter.selectionSort(arr); // 10 000 ~ 300 ms, 100 000 ~ 24 s
////        samples.algorithms.Sorter.bubbleSort(arr);    // 10 000 ~ 500 ms, 100 000 ~ 50 s
////        samples.algorithms.Sorter.bogoSort(arr);
//        long end = System.nanoTime();
////---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//        System.out.println("Sorted? - " + samples.algorithms.Sorter.isSorted(arr));
////        System.out.println("Sorted? - " + samples.algorithms.QuickSorter.isSorted(arr));
////        System.out.println(java.util.Arrays.toString(arr));
//        System.out.println("Time spent:" + ((end - start) / 1_000_000) + " ms");
//    }
//}
//
////        samples.algorithms.MergeSorter.sort(arr);     // 10 000 ~ 35 ms,  100 000 ~ 150 ms,   1 000 000 ~ 1 s,    10 000 000 ~ 70 s
////        samples.algorithms.QuickSorter.sort(arr);     // 10 000 ~ 20 ms,  100 000 ~ 100 ms,   1 000 000 ~ 400 ms, 10 000 000 ~ 3.5 s, 100 000 000 ~ 30 s
////---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
////  bucketSort(arr);    // 10 000 ~ 0 ms,   100 000 ~ 2 ms,     1 000 000 ~ 10 ms,  10 000 000 ~ 40 ms,     100 000 000 ~ 125 ms,   500 000 000 ~ 550 ms, 1 000 000 000 000 ~ 1.1 s
////  quickSort(0, arr);  // 10 000 ~ 5 ms,   100 000 ~ 15 ms,    1 000 000 ~ 75 ms,  10 000 000 ~ 650 ms,    100 000 000 ~ 6.5 s,    500 000 000 ~ 35 s
////  heapSort(arr);      // 10 000 ~ 2 ms,   100 000 ~ 15 ms,    1 000 000 ~ 125 ms, 10 000 000 ~ 1.2 s,     100 000 000 ~ 12 s
////  mergeSort(0, arr);  // 10 000 ~ 5 ms,   100 000 ~ 25 ms,    1 000 000 ~ 175 ms, 10 000 000 ~ 1.5 s,     100 000 000 ~ 12.2 s
////  insertionSort(arr); // 10 000 ~ 17 ms,  100 000 ~ 1 s,      1 000 000 ~ 1.5 m
////  selectionSort(arr); // 10 000 ~ 90 ms,  100 000 ~ 8.5 s
////  bubbleSort(arr);    // 10 000 ~ 175 ms, 100 000 ~ 16.5 s