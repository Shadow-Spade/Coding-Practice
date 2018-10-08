package CoolMath;

public class Matrix {

    /*
       Samples used back in Linear Algebra
       Matrix mat = new Matrix(new double[][]{
                {1,1,0,3},
                {7,0,4,0},
                {0,1,-4,4}
        });
        Matrix result = new Matrix(new double[][]{
                {1,0,0,-8},
                {0,1,0,1},
                {0,0,1,-2}
        });
        //Show working functions
        //System.out.println(mat.ToRREF().toString());
        //Show supposed solution
        System.out.println(result.toString());
        //Check if they equal
        System.out.println(mat.equals(result));*/

    private final int ROWS, COLS;
    private final double[][] DATA;
    //Subclass to hold the current position when doing calculations
    static class Coordinate {
        int row;
        int col;

        Coordinate(int r, int c) {
            row = r;
            col = c;
        }
        Coordinate(Coordinate a){
            row = a.row;
            col = a.col;
        }

        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    //--Constructors--//

    //Create from Dimensions
    public Matrix(int rows, int cols){
        this.ROWS = rows;
        this.COLS = cols;
        this.DATA = new double[ROWS][COLS];
    }

    //Create from Array Data
    public Matrix(double[][] data){
        this.ROWS = data.length;
        this.COLS = data[0].length;
        this.DATA = data;
    }

    //Create from existing data
    public Matrix(Matrix copy){
        this(copy.DATA);
    }

    //--Methods--//

    //Swaps two rows in this matrix
    private void swap(int r1, int r2){
        swap(r1,r2,this);
    }

    //Swaps two rows in given matrix
    private void swap(int r1, int r2, Matrix A){
        double[] temp = A.DATA[r1];
        A.DATA[r1] = A.DATA[r2];
        A.DATA[r2] = temp;
    }

    private void swap(Coordinate c1, Coordinate c2){
        swap(c1.row,c2.row,this);
    }

    //Creates the identity matrix of a given square dimension
    public static Matrix identity(int sd){
        Matrix A = new Matrix(sd, sd);
        for (int x = 0; x < sd; x++){
            A.DATA[x][x] = 1;
        }
        return A;
    }

    //Adds the given Matrix
    public Matrix plus(Matrix B){
        Matrix A = this;
        if(A.COLS != B.COLS || A.ROWS !=  B.ROWS) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(ROWS, COLS);
        for(int r = 0; r < ROWS; r++){
            for (int c = 0; c < COLS; c++){
                C.DATA[r][c] = A.DATA[r][c] + B.DATA[r][c];
            }
        }
        return C;
    }

    //Subtracts the given Matrix
    public Matrix minus(Matrix B){
        Matrix A = this;
        if(A.COLS != B.COLS || A.ROWS !=  B.ROWS) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(ROWS, COLS);
        for(int r = 0; r < ROWS; r++){
            for (int c = 0; c < COLS; c++){
                C.DATA[r][c] = A.DATA[r][c] - B.DATA[r][c];
            }
        }
        return C;
    }

    //Multiplies the given Matrix
    public Matrix times(Matrix B){
        Matrix A = this;
        if(A.COLS != B.ROWS) throw new RuntimeException("Illegal matrix dimensions.");
        Matrix C = new Matrix(A.ROWS, B.COLS);
        for (int x = 0; x < C.ROWS; x++){
            for (int y = 0; x < C.COLS; x++){
                for (int z = 0; x < A.COLS; x++){
                    C.DATA[x][y] += (A.DATA[x][z] * B.DATA[z][y]);
                }
            }
        }
        return C;
    }

    //Checks if the matrix is Square in dimension
    public boolean isSquare(){
        return ROWS == COLS;
    }

    //Does this Matrix exactly equal this one?
    public boolean equals(Matrix B){
        Matrix A = this;
        if(A.COLS != B.COLS || A.ROWS !=  B.ROWS) return false;
        for(int r = 0; r < ROWS; r++){
            for (int c = 0; c < COLS; c++){
                if(A.DATA[r][c] != B.DATA[r][c]) return false;
            }
        }
        return true;
    }

    public boolean isColumnZero(Coordinate pos, Matrix A){
        for (int x = 0; x < A.ROWS; x++){
            if(A.DATA[x][pos.col]!=0){
                return false;
            }
        }
        return true;
    }

    public boolean isRowZero(Coordinate pos, Matrix A){
        for (int x = 0; x < A.COLS; x++){
            if(A.DATA[pos.row][x]!=0){
                return false;
            }
        }
        return true;
    }

    public double valueAtCord(Coordinate pos){
        return valueAtCord(pos, this);
    }

    public double valueAtCord(Coordinate pos, Matrix A){
        return A.DATA[pos.row][pos.col];
    }

    public Coordinate findPivot(Coordinate a, Matrix A) {
        int first_row = a.row;
        Coordinate pivot = new Coordinate(a.row, a.col);
        Coordinate current = new Coordinate(a.row, a.col);

        for (int i = a.row; i < (A.ROWS - first_row); i++) {
            current.row = i;
            if (valueAtCord(current,A) == 1.0) {
                swap(current, a);
            }
        }

        current.row = a.row;
        for (int i = current.row; i < (A.ROWS - first_row); i++) {
            current.row = i;
            if (valueAtCord(current,A) != 0) {
                pivot.row = i;
                break;
            }
        }

        return pivot;
    }

    public void Scale(Coordinate cord, double scalar, Matrix A) {
        for(int i = 0; i < A.COLS; i++){
            A.DATA[cord.row][i] *= scalar;
        }
    }

    public void MultiplyAndAdd(Coordinate to, Coordinate from, double scalar, Matrix A) {
        for (int i = 0; i < A.COLS; i++) {
            A.DATA[to.row][i] += (A.DATA[from.row][i] * scalar);
        }
    }

    //Reduces this matrix to its RREF
    public Matrix ToRREF(){
        return ToRREF(this);
    }

    //Reduces the given matrix to its RREF
    public Matrix ToRREF(Matrix A){
        //Setup
        Coordinate pivot = new Coordinate(0,0);
        int submatrix = 0;

        //Initial Loop
        for (int x = 0; x < A.COLS; x++) {
            pivot = new Coordinate(pivot.row,x);

            //Check if column is applicable, if not change
            for (int i = x; i < A.COLS; i++){
                if (!isColumnZero(pivot,A)){
                    break;
                } else {
                    pivot.col = i;
                }
            }
            //Select a nonzero entry in the pivot column with the highest absolute value as a pivot.
            pivot = findPivot(pivot,A);

            if (valueAtCord(pivot,A) == 0) {
                pivot.row++;
                continue;
            }
            //If necessary, interchange rows to move this entry into the pivot position.
            //move this row to the top of the submatrix
            if (pivot.row != submatrix) {
                swap(new Coordinate(submatrix, pivot.col), pivot);
            }
            //Force pivot to be 1
            if (valueAtCord(pivot,A) != 1) {
                double scalar = Math.pow(valueAtCord(pivot,A), -1);
                Scale(pivot,scalar,A);

            }
            //Use row replacement operations to create zeroes in all positions below the pivot.
            //belowPivot = belowPivot + (Pivot * -belowPivot)
            for (int i = pivot.row; i < A.ROWS; i++) {
                if (i == pivot.row) {
                    continue;
                }
                Coordinate belowPivot = new Coordinate(i, pivot.col);
                double temp = -valueAtCord(belowPivot,A);
                double complement = (temp / valueAtCord(pivot, A));
                MultiplyAndAdd(belowPivot, pivot, complement,A);
            }
            //Beginning with the rightmost pivot and working upward and to the left, create zeroes above each pivot.
            //If a pivot is not 1, make it 1 by a scaling operation.
            //Use row replacement operations to create zeroes in all positions above the pivot
            for (int i = pivot.row; i >= 0; i--) {
                if (i == pivot.row) {
                    if (valueAtCord(pivot,A) != 1.0) {
                        Scale(pivot, Math.pow(valueAtCord(pivot,A),-1), A);
                    }
                    continue;
                }
                if (i == pivot.row) {
                    continue;
                }

                Coordinate abovePivot = new Coordinate(i, pivot.col);
                double temp = -valueAtCord(abovePivot,A);
                double complement = (temp/ valueAtCord(pivot,A));
                MultiplyAndAdd(abovePivot, pivot, complement,A);
            }

            if ((pivot.row + 1) >= A.ROWS || isRowZero(new Coordinate(pivot.row+1, pivot.col),A)) {
                break;
            }

            submatrix++;
            pivot.row++;
        }
        for (int r = 0; r < A.ROWS; r++){
            for (int c = 0; c < A.COLS; c++){
                if(A.DATA[r][c] == -0.0){
                    A.DATA[r][c] = 0;
                }
            }
        }
        return A;
    }

    //TODO
    public double getDet(){
        return getDet(this);
    }

    public double getDet(Matrix A){
        if(!A.isSquare()){
            return 0;
        }
        return 0;
    }

    public boolean isInvertible(){
        return isInvertible(this);
    }

    public boolean isInvertible(Matrix A){
        if(!A.isSquare()){
            return false;
        }
        return true;
    }

    //--Generated Getters--//

    public int getROWS() {
        return ROWS;
    }

    public int getCOLS() {
        return COLS;
    }

    public double[][] getDATA() {
        return DATA;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder().append("");
        for(int x = 0; x< ROWS; x++){
            matrix.append("[");
            for (int y = 0; y<COLS; y++){
                if(y+1==COLS){
                    matrix.append(DATA[x][y]);
                }else {
                    matrix.append(DATA[x][y]).append(",");
                }
            }
            matrix.append("]\n");
        }
        return "//--"+ ROWS + "x" + COLS +" MATRIX--//\n" + matrix.toString();
    }
}
