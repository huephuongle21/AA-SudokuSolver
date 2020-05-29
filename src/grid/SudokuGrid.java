/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

 package grid;

 import java.io.*;
import java.util.ArrayList;


/**
 * Abstract class representing the general interface for a Sudoku grid.
 * Both standard and Killer Sudoku extend from this abstract class.
 */
public abstract class SudokuGrid
{
	protected int grid[][];
	protected int value[];
	protected int size;
	protected int sqrt;

    /**
     * Load the specified file and construct an initial grid from the contents
     * of the file.  See assignment specifications and sampleGames to see
     * more details about the format of the input files.
     *
     * @param filename Filename of the file containing the intial configuration
     *                  of the grid we will solve.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void initGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Write out the current values in the grid to file.  This must be implemented
     * in order for your assignment to be evaluated by our testing.
     *
     * @param filename Name of file to write output to.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void outputGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Converts grid to a String representation.  Useful for displaying to
     * output streams.
     *
     * @return String representation of the grid.
     */
    public abstract String toString();


    /**
     * Checks and validates whether the current grid satisfies the constraints
     * of the game in question (either standard or Killer Sudoku).  Override to
     * implement game specific checking.
     *
     * @return True if grid satisfies all constraints of the game in question.
     */
    public abstract boolean validate();
    
    public abstract void initializeGrid(ArrayList<ArrayList<Integer>> list);
    
    public void createGrid(int size) {
    	this.size = size;
    	this.sqrt = (int) Math.sqrt(size);
    	this.grid = new int[size][size];
    	this.value = new int[size];
    }
    
    public int getSize() {
    	return size;
    }
    
    public int[] getValue() {
    	return value;
    }
    
    public int getGridValue(int row, int col) {
    	return grid[row][col];
    }
    
    public abstract void setValue(int row, int col, int value);
    
    public int getBoxSize() {
    	return sqrt;
    }
    
    public abstract boolean validateNewCell(int row, int col, int value);
    
    //Delete after finish
    public abstract void print();
    
    
    

} // end of abstract class SudokuGrid
