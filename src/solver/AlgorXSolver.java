/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package solver;

import java.util.ArrayList;
import grid.SudokuGrid;


/**
 * Algorithm X solver for standard Sudoku.
 */
public class AlgorXSolver extends StdSudokuSolver
{
    // TODO: Add attributes as needed.

	private ArrayList<Integer> tmpSolution = new ArrayList<Integer>();
	private ArrayList<Integer> solution;
	private int[][] backtracking;
	
    public AlgorXSolver() {
        // TODO: any initialisation you want to implement.
    } // end of AlgorXSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of the Algorithm X solver for standard Sudoku.

    	// Create a matrix
    	transformToMatrix(grid);
    	backtracking = new int[coverRows][coverCols];
    	    	    	
    	// Sub-method to solve sudoku using matrix
    	process(0);
    	if(solution != null) {
    		transformToSudoku(grid);
    	}

        // placeholder
        return false;
    } // end of solve()
    
    private void transformToSudoku(SudokuGrid grid) {
    	for(int i = 0; i != solution.size(); i++) {
    		int index = solution.get(i);
    		int value = (index%size) + 1;
    		int col = ((index-value+1)/size)%size;
    		int row = (index - (value-1) - col*size)/size/size;
    		grid.setValue(row, col, value);    		
    	}
    }
    
    private void process(int k) {   	
    	int count = 0;
    	for(int i = 0; i != coverRows; i++) {
    		for(int j = 0; j != coverCols; j++) {
    			if(matrix[i][j] == 1) {
    				count++;
    			}
    		}
    	}
    	if(count == 0) {
    		solution = new ArrayList<Integer>(tmpSolution);
    	} else {
			int col = findCol();
			if(col != -1) {
				chooseRow(col);
				process(k+1);
			}
			backtrack();
			// always backtrack and start at the same index
    	}
    }
    
    private int findCol() {
    	int returnCol = -1;
    	int count = 0;
    	for(int i = 0; i != coverCols; i++) {
    		for(int j = 0; j != coverRows; j++) {
    			if(matrix[j][i] == 1) {
    				returnCol = i;
    				count++;
    				if(count == NUMBER_CONSTRAINTS) {
    					return returnCol;
    				}
    			}
    		}
    	}
    	return -1;
    }
    
    private void chooseRow(int col) {
    	for(int i = 0; i != coverRows; i++) {
    		if(matrix[i][col] == 1) {
    			tmpSolution.add(i);
    			for(int j = 0; j != coverCols; j++) {
    				if(matrix[i][j] == 1) {
    					matrix[i][j] = 0;
    					backtracking[i][j] = 1;
    					for(int k = 0; k != coverRows; k++) {
        					if(matrix[k][j] == 1) {
        						matrix[k][j] = 0;
        						backtracking[k][j] = 1;
        					}
        				}
    				}
    			}
    		}    		
    	}
    }
    
    private void backtrack() {
    	int i = tmpSolution.size()-1;
    	int index = tmpSolution.get(i);
    	tmpSolution.remove(i);
    	for(int j = 0; j != coverCols; j++) {
    		if(backtracking[index][j] == 1) {
    			backtracking[index][j] = 0;
    			matrix[index][j] = 1;
    			for(int k = 0; k != coverRows; k++) {
    				if(backtracking[k][j] == 1) {
    					backtracking[k][j] = 0;
    					matrix[k][j] = 1;
    				}
    			}
    		}
    	}
    }
    
    private int chooseCol() {
    	int size = coverRows;
    	int count = 0;
    	int col = -1;
    	for(int i = 0; i != coverCols; i++) {
    		for(int j = 0; j != coverRows; j++) {
    			if(matrix[j][i] == 1) {
    				count++;
    			}
    		}
    		if(count < size && count >= NUMBER_CONSTRAINTS) {
    			size = count;
    			count = 0;
    			col = i;
    		}
    	}
    	return col;
    }
    

} // end of class AlgorXSolver