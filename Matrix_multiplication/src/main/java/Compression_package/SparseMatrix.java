package Compression_package;

import java.util.ArrayList;

public class SparseMatrix {
    
    public int size;
    public ArrayList<Integer> rows;
    public ArrayList<Integer> columns;
    public ArrayList<Double> values;
    
   public SparseMatrix(int size){
       this.size = size;   
   }
   
   public void builder(ArrayList<Integer> rows, ArrayList<Integer> columns,ArrayList<Double> values){
       this.rows = rows;
       this.columns = columns;
       this.values = values;
       
   }
   
   
   public void setValue(int row, int column, double value) {
        this.rows.add(row);
        this.columns.add(column);
        this.values.add(value);
    }
   
    public SparseMatrix getMatrix() {
        return this;
    }
    
}
