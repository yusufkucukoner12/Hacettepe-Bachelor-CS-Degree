class Searchs {
    public static int linearSearch(int[] data, int key) {
        for(int i = 0; i<data.length;i++){
            if(data[i] == key){
                return i;
            }
        }        
        return -1;
    }
    public static int binarySearch(int[] data, int key) {
        int lo = 0;
        int hi = data.length - 1;

        while (hi - lo > 1) {
            int mid = (hi + lo) / 2;
            if (data[mid] < key) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        if (data[lo] == key) {
            return lo;
        } else if (data[hi] == key) {
            return hi;
        }
        return -1;
    }
}
