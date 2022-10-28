package Compression_package;


import java.util.ArrayList;

public class CompressedColumnMatrix{
    public int size;
    public ArrayList<Double> values;
    public ArrayList<Integer> rows;
    public int[] colPointers;

    public CompressedColumnMatrix(int size) {
        this.size = size;
        this.values = new ArrayList<Double>();
        this.rows = new ArrayList<Integer>();
        this.colPointers = new int[size + 1];
    }

}