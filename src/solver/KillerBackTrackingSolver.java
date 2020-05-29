/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import grid.SudokuGrid;


/**
 * Backtracking solver for Killer Sudoku.
 */
public class KillerBackTrackingSolver extends KillerSudokuSolver
{
    // TODO: Add attributes as needed.

    public KillerBackTrackingSolver() {
        // TODO: any initialisation you want to implement.
    } // end of KillerBackTrackingSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of a backtracking solver for Killer Sudoku.
    	int row = 0;
    	int col = 0;
    	int[] node = nextEmpty(grid);
    	
    	if(node[0] == -1) {
    		return true;
    	} else {
    		row = node[1];
    		col = node[2];
    	}
    	int[] valueRange = grid.getValue();
    	for(int value : valueRange) {
//    		grid.setValue(row, col, value);
//    		if(!grid.validate()) {
//    			grid.setValue(row, col, 0);
//    		} else {
//    			if(solve(grid)) {
//    				return true;
//    			}
//    		}
    		if(grid.validateNewCell(row, col, value)) {
    			grid.setValue(row, col, value);
    			if(solve(grid)) {
    				return true;
    			} else {
    				grid.setValue(row, col, 0);
    			}
    		}
    	}  	
    	grid.setValue(row, col, 0);

        // placeholder
        return false;
    } // end of solve()
    
    private int[] nextEmpty(SudokuGrid grid) {
    	int returnValue = -1;
    	int returnRow = 0;
    	int returnCol = 0;
    	int size = grid.getSize();
    	for(int row = 0; row != size; row++) {
    		for(int col = 0; col != size; col++) {
    			int value = grid.getGridValue(row, col);
    			if(value == 0) {
    				returnValue = value;
    				returnRow = row;
    				returnCol = col;
    				int[] returnNumber = {returnValue, returnRow, returnCol};
    				return returnNumber;
    			}
    		}
    	}
    	int[] solved = {returnValue, returnRow, returnCol}; 
    	return solved;
    	
    }

} // end of class KillerBackTrackingSolver()
