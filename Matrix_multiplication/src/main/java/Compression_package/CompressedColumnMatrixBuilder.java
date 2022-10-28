package Compression_package;


public class CompressedColumnMatrixBuilder{

    public CompressedColumnMatrix compressedColumnMatrix;

    public CompressedColumnMatrixBuilder(int size) {
        this.compressedColumnMatrix = new CompressedColumnMatrix(size);
    }

    public CompressedColumnMatrix getMatrix(){
        return this.compressedColumnMatrix;
    }

    public CompressedColumnMatrixBuilder compressByColumn(SparseMatrix sparseMatrix) {

        for (int pointer = 0; pointer < sparseMatrix.columns.size(); pointer++) {
            int position = this.compressedColumnMatrix.colPointers[sparseMatrix.columns.get(pointer)];
            while (position < this.compressedColumnMatrix.colPointers[sparseMatrix.columns.get(pointer)]){
                if(this.compressedColumnMatrix.rows.get(position) > sparseMatrix.rows.get(pointer)){
                    break;
                }
                position++;
            }
            this.setValue(position, sparseMatrix.values.get(pointer), sparseMatrix.rows.get(pointer));
            this.modifyColPointer(sparseMatrix.columns.get(pointer));
        }
        return this;
    }


    public void setValue(int position, double value, int row) {
        this.compressedColumnMatrix.values.add(position, value);
        this.compressedColumnMatrix.rows.add(position, row);
    }


    public void modifyColPointer(int column) {
        for (int i = column + 1; i <= this.compressedColumnMatrix.size; i++)
            this.compressedColumnMatrix.colPointers[i] += 1;
    }

}