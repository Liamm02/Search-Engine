package Compression_package;


import java.util.ArrayList;
import java.util.Objects;

public class CompressedRowMatrix{
    public int size;
    public ArrayList<Double> values;
    public ArrayList<Integer> columns;
    public int[] rowPointers;

    public CompressedRowMatrix(int size) {
        this.size = size;
        this.values = new ArrayList<Double>();
        this.columns = new ArrayList<Integer>();
        this.rowPointers = new int[size + 1];
    }

}