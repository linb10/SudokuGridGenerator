import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator extends ConsoleProgram {
    int[][] result = new int[9][9];

    public void run() {
        fillBoxes();
        fillRemaining(0, 0);
        printBoard();  
    }


    void fillBoxes() {
        fillBox(0, 0);
        fillBox(3, 3); 
        fillBox(6, 6); 
    }

    void fillBox(int row, int col) {

        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=1; i<=9; i++) {
            nums.add(i);
        }
        

        for (int r = row; r<row+3; r++) {
            for (int c = col; c<col+3; c++) {
                int cellNum = (int)(Math.random() * nums.size());
                result[r][c] = nums.get(cellNum);
                nums.remove(cellNum);
            }
        }
    }

    boolean fillRemaining(int row, int col) {

        if (row == 9) {
            return true;
        }


        int nextRow, nextCol;
        if (col == 8) {
            nextRow = row+1;
            nextCol = 0;
        } 
        else {
            nextRow = row;
            nextCol = col+1;
        }
        

        if (result[row][col] != 0) {
            return fillRemaining(nextRow, nextCol);
        }
        

        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=1; i<=9; i++) {
            nums.add(i);
        }
        

        while (nums.size() > 0) {

            int rand = (int)(Math.random() * nums.size());
            int cellNum = nums.get(rand);
            nums.remove(rand);
            

            if (isValid(row, col, cellNum)) {
                result[row][col] = cellNum;

                if (fillRemaining(nextRow, nextCol)) {
                    return true;
                }
                result[row][col] = 0;
            }
        }
        

        return false;
    }
    

    boolean isValid(int row, int col, int num) {

        for (int c=0; c<9; c++) {
            if (result[row][c] == num) return false;
        }
        

        for (int r=0; r<9; r++) {
            if (result[r][col] == num) return false;
        }
        

        int boxRow = (row / 3)*3;
        int boxCol = (col / 3)*3;
        for (int r = boxRow; r < boxRow+3; r++) {
            for (int c = boxCol; c < boxCol+3; c++) {
                if (result[r][c] == num){
                    return false;
                }
            }
        }
        return true;
    }

    void printBoard() {

        for (int r=0; r<9; r++) {

            if (r % 3 == 0) {
                println("+-------+-------+-------+");
            }

            String row = "";
            for (int c=0; c<9; c++) {
                if (c == 0) {
                    row += "| ";
                }
                row += result[r][c] + " ";
                if (c == 2 || c == 5 || c == 8) {
                    row += "| ";
                }
            }
            println(row);
        }
        println("+-------+-------+-------+");
    }
}
