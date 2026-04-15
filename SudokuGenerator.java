import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator extends ConsoleProgram {
    int[][] result = new int[9][9];

    public void run() {
        fillBoxes(); //fill first 3 boxes b/c completely random
        fillRemaining(0, 0);  //fill remaining sudoku row by row
        printBoard();  
    }


    void fillBoxes() {
        fillBox(0, 0);  //top left
        fillBox(3, 3);  //middle
        fillBox(6, 6);  //bottom right
    }

    void fillBox(int row, int col) {
        //make arraylist 1-9
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=1; i<=9; i++) {
            nums.add(i);
        }
        
        //fills box by random pick for 1-9 and removing pick
        for (int r = row; r<row+3; r++) {
            for (int c = col; c<col+3; c++) {
                int cellNum = (int)(Math.random() * nums.size());
                result[r][c] = nums.get(cellNum);
                nums.remove(cellNum);
            }
        }
    }

    boolean fillRemaining(int row, int col) {
        //recursive base case
        if (row == 9) {
            return true;
        }

        //establish next cell parameters
        int nextRow, nextCol;
        if (col == 8) {
            nextRow = row+1;
            nextCol = 0;
        } 
        else {
            nextRow = row;
            nextCol = col+1;
        }
        
        //recursive to next cell if cell has a number already
        if (result[row][col] != 0) {
            return fillRemaining(nextRow, nextCol);
        }
        
        //make arraylist 1-9
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i=1; i<=9; i++) {
            nums.add(i);
        }
        
        //fill in entire row
        while (nums.size() > 0) {
            //random pick for 1-9 and removing pick
            int rand = (int)(Math.random() * nums.size());
            int cellNum = nums.get(rand);
            nums.remove(rand);
            
            //checks random pick is valid. then checks true if at last row
            if (isValid(row, col, cellNum)) {
                result[row][col] = cellNum;
                //next row will be index 9 (row 10 outside of sudoku grid)
                if (fillRemaining(nextRow, nextCol)) {
                    return true;
                }
                result[row][col] = 0;
            }
        }
        
        //at this point, not at last row. resursive
        return false;
    }
    
    //checks if num is valid for cell based on existing puzzle
    boolean isValid(int row, int col, int num) {
        //check all nums in same column of cell
        for (int c=0; c<9; c++) {
            if (result[row][c] == num) return false;
        }
        
        //check all nums in same row of cell
        for (int r=0; r<9; r++) {
            if (result[r][col] == num) return false;
        }
        
        //check all nums in same box of cell
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
        //row by row
        for (int r=0; r<9; r++) {
            //seperate boxes horizontally
            if (r % 3 == 0) {
                println("+-------+-------+-------+");
            }
            //make row with seperate boxes vertically
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