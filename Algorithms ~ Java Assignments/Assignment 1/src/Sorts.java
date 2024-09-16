class Sorts {
    public static void insertionSort(int[] array) {
        int n = array.length;
        for (int j = 1; j < n; j++) {
            int key = array[j];
            int i = j - 1;
            while (i > 0 && array[i] > key) {
                array[i + 1] = array[i];
                i = i - 1;
            }
            array[i + 1] = key;
        }
    }

    public static int[] mergeSort(int[] array) {
        
        int n = array.length;
        if (n <= 1) {
            return array;
        }
        int[] left = new int[n/2];
        int[] right = new int[n - n/2];

        for (int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = array[i + left.length];
        }
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }

    public static int[] merge(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        int i = 0, j = 0, k = 0;
        while (i < A.length && j < B.length) {
            if (A[i] <= B[j]) {
                C[k++] = A[i++];
            } else {
                C[k++] = B[j++];
            }
        }
        while (i < A.length) {
            C[k++] = A[i++];
        }
        while (j < B.length) {
            C[k++] = B[j++];
        }
        return C;
    }
    public static int[] countingSort(int[] inpArray) { 
        int k = inpArray[0];
        for (int i = 1; i < inpArray.length; i++) {//O(n)
            if (inpArray[i] > k) {
                k = inpArray[i];
            }
        }
        int[] countArray = new int[k + 1];
        int[] outputArray = new int[inpArray.length];

        for (int i = 0; i < inpArray.length; i++) {//O(n)
            countArray[inpArray[i]]++;
        }

        for (int j = 1; j <= k; j++) {//O(k)
            countArray[j] += countArray[j - 1];
        }

        for (int l = inpArray.length - 1; l >= 0; l--) {//O(n)
            outputArray[countArray[inpArray[l]] - 1] = inpArray[l];
            countArray[inpArray[l]]--;
        }
        return outputArray;
    }
}
