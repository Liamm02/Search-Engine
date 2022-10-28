import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Compression_package.SparseMatrix;
import Compression_package.CompressedRowMatrixBuilder;
import Compression_package.CompressedRowMatrix;
import Compression_package.CompressedColumnMatrixBuilder;
import Compression_package.CompressedColumnMatrix;
import Compression_package.CompressMatrixMultiplication;


public class Main {

    static double[][] matrix = new double[2000][2000];
    static double[][] matrixA = new double[2000][2000];
    static double[][] matrixB = new double[2000][2000];
    static int size = 2000;


    public static void main(String[] args) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrixA[i][j] = Math.random() * 1000;
                matrixB[i][j] = Math.random() * 1000;
            }
        }
        
        
        ArrayList rows2 = new ArrayList<Integer>();
        ArrayList columns2 = new ArrayList<Integer>();
        ArrayList values2 = new ArrayList<Double>();
        
        SparseMatrix mtx_2000 = new SparseMatrix(matrixA.length);
        for(int i= 0; i < matrixA.length;i++){
            for(int j= 0; j < matrixA.length;j++){
                values2.add(matrixA[i][j]); 
                
            }
            columns2.add(i);
            rows2.add(i);
        }
        mtx_2000.builder(rows2,columns2,values2);
        
        System.out.println("Starting random matrix CRS compression");
        long time = System.currentTimeMillis();
        CompressedRowMatrixBuilder mtx2000_RowCompressed = new CompressedRowMatrixBuilder(matrixA.length);
        mtx2000_RowCompressed.compressByRow(mtx_2000);
        CompressedRowMatrix ROW_COMP2000 = mtx2000_RowCompressed.getMatrix();
        System.out.println("Finished random matrix compression by rows: " + (System.currentTimeMillis() - time)+" milliseconds");
        
        System.out.println("Starting random matrix CSS compression");
        time = System.currentTimeMillis();
        CompressedColumnMatrixBuilder mtx2000_ColCompressed = new CompressedColumnMatrixBuilder(matrixA.length);
        mtx2000_ColCompressed.compressByColumn(mtx_2000);
        CompressedColumnMatrix COL_COMP2000 = mtx2000_ColCompressed.getMatrix();
        System.out.println("Finished random matrix compression by columns: " + (System.currentTimeMillis() - time)+" milliseconds");
        
        System.out.println("Starting random matrix compressed multiplication");
        time = System.currentTimeMillis();
        CompressMatrixMultiplication multiplication = new CompressMatrixMultiplication();
        SparseMatrix result = multiplication.multiply(ROW_COMP2000, COL_COMP2000);
        System.out.println("Finished random matrix compressed multiplication: " + (System.currentTimeMillis() - time)+" milliseconds");
        
        int sizeMTX = 0;
        ArrayList rows = new ArrayList<Integer>();
        ArrayList columns = new ArrayList<Integer>();
        ArrayList values = new ArrayList<Double>();
        
        int checker = 1;
        BufferedReader reader;
        try {
            //
            reader = new BufferedReader(new FileReader(
                    System.getProperty("user.dir")+"\\mc2depi.mtx"));
            String line = reader.readLine();
            while (line != null) {
                if (checker == 1){
                    String[] splited = line.split("\\s+");
                    
                    sizeMTX = Integer.parseInt(splited[0]);
                    checker = 0;
                } 
                else {
                    String[] splited = line.split("\\s+");
                    rows.add(Integer.parseInt(splited[0]));
                    columns.add(Integer.parseInt(splited[1]));
                    values.add(Double.parseDouble(splited[2]));                   
                    
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        SparseMatrix mtx = new SparseMatrix(sizeMTX);
        mtx.builder(rows,columns,values);
        
        System.out.println("Starting TEST matrix CRS compression");
        time = System.currentTimeMillis();
        CompressedRowMatrixBuilder mtx_RowCompressed = new CompressedRowMatrixBuilder(sizeMTX);
        mtx_RowCompressed.compressByRow(mtx);
        CompressedRowMatrix ROW_COMP = mtx_RowCompressed.getMatrix();
        System.out.println("Finished TEST matrix compression by rows: " + (System.currentTimeMillis() - time)+" milliseconds");
        
        System.out.println("Starting TEST matrix CSS compression");
        time = System.currentTimeMillis();
        CompressedColumnMatrixBuilder mtx_ColCompressed = new CompressedColumnMatrixBuilder(sizeMTX);
        mtx_ColCompressed.compressByColumn(mtx);
        CompressedColumnMatrix COL_COMP = mtx_ColCompressed.getMatrix();
        System.out.println("Finished TEST matrix compression by columns: " + (System.currentTimeMillis() - time)+" milliseconds");
        
        System.out.println("Starting compressed multiplication on TEST matrix");
        time = System.currentTimeMillis();
        CompressMatrixMultiplication multiplication2 = new CompressMatrixMultiplication();
        SparseMatrix result2 = multiplication2.multiply(ROW_COMP, COL_COMP);
        System.out.println("Finished TEST compressed multiplication: " + (System.currentTimeMillis() - time)+" milliseconds");
        
        
        


        System.out.println("Starting normal multiplication");
        time = System.currentTimeMillis();
        normalMultiplication();
        System.out.println("Finished normal multiplication: " + (System.currentTimeMillis() - time)+" milliseconds");
        /*
         * Starting normal multiplication
         * Finished normal multiplication: 42532
         * */


        System.out.println("Starting tiled multiplication");
        time = System.currentTimeMillis();
        tiledMultiplication();
        System.out.println("Finished tiled multiplication: " + (System.currentTimeMillis() - time)+" milliseconds");
        /*
         * Starting tiled multiplication
         * Finished tiled multiplication: 28489
         * */


        System.out.println("Starting tiled multiplication multithreaded");
        time = System.currentTimeMillis();
        tiledMultiplicationMultiThread();
        System.out.println("Finished tiled multiplication multithreaded: " + (System.currentTimeMillis() - time)+" milliseconds");
        /*
         * Starting tiled multiplication multithreaded
         * Finished tiled multiplication multithreaded: 3047
         * */

    }

    public static void normalMultiplication() {
        int sum;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sum = 0;
                for (int k = 0; k < size; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                matrix[i][j] = sum;
            }
        }
    }


    static void tiledMultiplication() {
        int sum, blockSize = size / 20;
        for (int i0 = 0; i0 < size; i0 += blockSize) {
            for (int j0 = 0; j0 < size; j0 += blockSize) {
                for (int k0 = 0; k0 < size; k0 += blockSize) {
                    for (int i = i0; i < Math.min(i0 + blockSize, size); i++) {
                        for (int j = j0; j < Math.min(j0 + blockSize, size); j++) {
                            sum = 0;
                            for (int k = k0; k < Math.min(k0 + blockSize, size); k++) {
                                sum += matrixA[i][k] * matrixB[k][j];
                            }
                            matrix[i][j] = sum;
                        }
                    }
                }
            }
        }
    }

    static void tiledMultiplicationMultiThread() {
        int blockSize = size / 20;
        Thread[] pool = new Thread[20];

        class ThreadJob extends Thread {
            private int i0;

            ThreadJob(int i0) {
                this.i0 = i0;
            }

            public void run() {
                int sum;

                for (int j0 = 0; j0 < size; j0 += blockSize) {
                    for (int k0 = 0; k0 < size; k0 += blockSize) {
                        for (int i = i0; i < Math.min(i0 + blockSize, size); i++) {
                            for (int j = j0; j < Math.min(j0 + blockSize, size); j++) {
                                sum = 0;
                                for (int k = k0; k < Math.min(k0 + blockSize, size); k++) {
                                    sum += matrixA[i][k] * matrixB[j][k];
                                }
                                matrix[i][j] = sum;
                            }
                        }
                    }
                }
            }
        }
        int i = 0;
        for (int i0 = 0; i0 < size; i0 += blockSize) {
            Thread thread = new ThreadJob(i0);
            pool[i] = thread;
            i++;
            thread.start();
        }

        for (i = 0; i < 20; i++) {
            try {
                pool[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
