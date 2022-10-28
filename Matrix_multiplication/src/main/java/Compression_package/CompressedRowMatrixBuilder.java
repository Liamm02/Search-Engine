
package Compression_package;
import Compression_package.SparseMatrix;

public class CompressedRowMatrixBuilder{

    public CompressedRowMatrix compressedRowMatrix;

    public CompressedRowMatrixBuilder(int size){
        this.compressedRowMatrix = new CompressedRowMatrix(size);
    }

    public CompressedRowMatrix getMatrix(){
        return this.compressedRowMatrix;
    }

    public CompressedRowMatrixBuilder compressByRow(SparseMatrix sparseMatrix) {

        for (int pointer = 0; pointer < sparseMatrix.rows.size(); pointer++) {
            int position = this.compressedRowMatrix.rowPointers[sparseMatrix.rows.get(pointer)];
            while (position < this.compressedRowMatrix.rowPointers[sparseMatrix.rows.get(pointer)]){
                if(this.compressedRowMatrix.columns.get(position) > sparseMatrix.columns.get(pointer)) break;
                position++;
            }
            this.setValue(position, sparseMatrix.values.get(pointer), sparseMatrix.columns.get(pointer));
            this.modifyRowPointer(sparseMatrix.rows.get(pointer));
        }
        return this;
    }


    public void setValue(int position, double value, int column) {
        this.compressedRowMatrix.values.add(position, value);
        this.compressedRowMatrix.columns.add(position, column);
    }

    public void modifyRowPointer(int row) {
        for (int i = row + 1; i <= this.compressedRowMatrix.size; i++)
            this.compressedRowMatrix.rowPointers[i] += 1;
    }
}
