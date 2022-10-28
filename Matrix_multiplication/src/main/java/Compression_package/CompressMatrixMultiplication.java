package Compression_package;


import Compression_package.CompressedColumnMatrix;
import Compression_package.CompressedRowMatrix;
import Compression_package.SparseMatrix;
import java.util.ArrayList;

public class CompressMatrixMultiplication{


    public SparseMatrix multiply(CompressedRowMatrix a, CompressedColumnMatrix b){
        ArrayList rows = new ArrayList<Integer>();
        ArrayList columns = new ArrayList<Integer>();
        ArrayList values = new ArrayList<Double>();
        SparseMatrix builder = new SparseMatrix(a.size);
        builder.builder(rows,columns,values);

        for(int i = 0; i < a.size; i++){
            for(int j = 0; j < b.size; j++){
                int ii = a.rowPointers[i];
                int iEnd = a.rowPointers[i+1];
                int jj = b.colPointers[j];
                int jEnd = b.colPointers[j+1];
                double s = 0;
                while (ii < iEnd && jj < jEnd) {
                    int aa = a.columns.get(ii);
                    int bb = b.rows.get(jj);
                    if (aa == bb) {
                        s += a.values.get(ii) * b.values.get(jj);
                        ii++;
                        jj++;
                    }
                    else if (aa < bb) ii++;
                    else jj++;
                }
                if (s > 1E-5) builder.setValue(i, j, s);
            }
        }
        return builder.getMatrix();
    }
}
