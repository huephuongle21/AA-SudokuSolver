/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.Arrays;

import grid.SudokuGrid;

/**
 * Abstract class for common attributes or methods for solvers of standard
 * Sudoku.
 * Note it is not necessary to use this, but provided in case you wanted to do
 * so and then no need to change the hierarchy of solver types.
 */
public abstract class StdSudokuSolver extends SudokuSolver
{
	protected final int NUMBER_CONSTRAINTS = 4;
	protected int size;
	protected int[] valueRange;
	protected int bSize;
	protected int coverRows;
	protected int coverCols;
	protected int[][] matrix;
	
    protected void transformToMatrix(SudokuGrid grid) {
    	size = grid.getSize() ;
    	valueRange = grid.getValue();
    	bSize = grid.getBoxSize();	
    	
    	coverCols = NUMBER_CONSTRAINTS*size*size;
    	coverRows = size*size*size;
    	
    	matrix = new int[coverRows][coverCols];
    	
    	int pos = 0;
    	pos = createCell(pos);
    	pos = createRow(pos);
    	pos = createColumn(pos);
    	createBox(pos);
    	
    	initialize(grid);   	
    }
    
    protected void initialize(SudokuGrid grid) {
    	for(int row = 1; row <= size; row++) {
    		for(int col = 1; col <= size; col++) {
    			int notFill = grid.getGridValue(row-1, col-1);
    			if(notFill != -1) {
    				for(int valueIndex = 1; valueIndex <= size; valueIndex++) {
    					if(valueRange[valueIndex-1] != notFill) {
    						Arrays.fill(matrix[index(row,col,valueIndex)], 0);
    					}
    				}
    			}
    		}
    	}
    }
    
    // Calculate index in the matrix
    private int index(int row, int col, int value) {
    	int index = (row-1)*size*size + (col-1)*size + (value-1);
    	return index;
    }
    
    // Create cell constraint
    private int createCell(int pos) {
    	for(int row = 1; row <= size; row++) {
    		for(int col = 1; col <= size; col++, pos++) {
    			for(int value = 1; value <= size; value++) {
    				matrix[index(row,col,value)][pos] = 1;
    			}
    		}
    	}
    	return pos;
    }
    
    // Create row constraint
    private int createRow(int pos) {
    	for(int row = 1; row <= size; row++) {
    		for(int value = 1; value <= size; value++, pos++) {
    			for(int col = 1; col <= size; col++) {
    				matrix[index(row,col,value)][pos] = 1;
    			}
    		}
    	}
    	return pos;
    }
    
    // Create column constraint
    private int createColumn(int pos) {
    	for(int row = 1; row <= size; row++) {
    		for(int value = 1; value <= size; value++, pos++) {
    			for(int col = 1; col <= size; col++) {
    				matrix[index(col,row,value)][pos] = 1;
    			}
    		}
    	}
    	return pos;
    }
    
    // Create box constraint
    public void createBox(int pos) {
    	for(int row = 1; row <= size; row+=bSize) {
    		for(int col = 1; col <= size; col+=bSize) {
    			for(int value = 1; value <= size; value++, pos++) {
    				for(int bRow = 0; bRow != bSize; bRow++) {
    					for(int bCol = 0; bCol != bSize; bCol++) {
    						matrix[index((row+bRow),(col+bCol),value)][pos] = 1;
    					}
    				}
    			}
    		}
    	}
    }
    
} // end of class StdSudokuSolver
