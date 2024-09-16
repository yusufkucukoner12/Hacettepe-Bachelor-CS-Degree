import java.io.IOException;

class Main {
    public static void main(String args[]) throws IOException {
        Pickthedata pickthedata = new Pickthedata(args[0]);
        Sorts sorts = new Sorts();
        Searchs searchs = new Searchs();
        Plotter plotter = new Plotter();
    
        
       
        
        double[] linearRandom = SearchManager.runSearchTests(searchs, Searchs::linearSearch, "Random", "Linear Search", 1000,pickthedata);
        double[] linearSorted = SearchManager.runSearchTests(searchs, Searchs::linearSearch, "Sorted", "Linear Search", 1000,pickthedata);
        double[] binarySorted = SearchManager.runSearchTests(searchs, Searchs::binarySearch, "Sorted", "Binary Search", 1000,pickthedata);


        plotter.plotSearchResults(linearRandom, linearSorted, binarySorted, new int[]{500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000,250000}, "Search Results");
    }
}

