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
	private ArrayList<Integer> removedCols = new ArrayList<Integer>();
	private ArrayList<Integer> listRows = new ArrayList<Integer>();
	
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
    	process();
    	if(solution != null) {
    		transformToSudoku(grid);
    		return true;
    	}
        // placeholder
        return false;
    } // end of solve()
    
    private void transformToSudoku(SudokuGrid grid) {
    	for(int i = 0; i != solution.size(); i++) {    		
    		int index = solution.get(i);
    		int valueIndex = (index%size) + 1;
    		int col = ((index-valueIndex+1)/size)%size;
    		int row = (index - (valueIndex-1) - col*size)/size/size;
    		int value = valueRange[valueIndex-1];
    		grid.setValue(row, col, value);    		
    	}
    }

    private void process() {
    	boolean finished = false;
    	while(!finished) {
    		int col = chooseCol();
    		removedCols.add(col);
    		chooseRow(col);
    		boolean valid = false;
    		int i = 0;
    		while(!valid) {
    			int row = listRows.get(i);
    			cover(row);
    			if(isEmpty()) {
    				valid = true;
    				finished = true;
    			} else {
    				if(!isRowValid()) {
    					uncover(row);
    					i++;
    				} else {
    					listRows.clear();
    					valid = true;
    				}
    			}
    		}
    	}
    }
    
    private void chooseRow(int col) {
    	for(int i = 0; i < coverRows; i++) {
    		if(matrix[i][col] == 1) {
    			listRows.add(i);
    		}
    	}
    }

    private boolean isRowValid() {
    	int count = 0;
    	for(int i = 0; i != coverCols; i++) {
    		if(!isRemoved(i)) {
    			for(int j = 0; j != coverRows; j++) {
    				if(matrix[j][i] == 1) {
    					count++;
    				}
    			}
    			if(count == 0) {
    				return false;
    			} else {
    				count = 0;
    			}
    		}
    	}
    	return true;
    }

    private boolean isEmpty() {
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
    		return true;
    	} else {
    		return false;
    	}
    }

    private void cover(int row) {
    	tmpSolution.add(row);
    	ArrayList<Integer> listCols = new ArrayList<Integer>();
    	for(int i = 0; i != coverCols; i++) {
    		if(matrix[row][i] == 1) {
    			listCols.add(i);
    			removedCols.add(i);
    		}
    	}
    	ArrayList<Integer> listRows = new ArrayList<Integer>();
    	for(int i = 0; i != listCols.size(); i++) {
    		int col = listCols.get(i);
    		for(int j = 0; j != coverRows; j++) {
    			if(matrix[j][col] == 1) {
    				listRows.add(j);
    			}
    		}
    		for(int k = 0; k != listRows.size(); k++) {
    			int subRow = listRows.get(k);
    			for(int a = 0; a != coverCols; a++) {
    				if(matrix[subRow][a] == 1) {
    					matrix[subRow][a] = 0;
    					backtracking[subRow][a] = 1;
    				}
    			}
    		}
    		listRows.clear();
    	}
    }
    
    private void undoCols(int value) {
    	int index = -1;
    	for(int i = 0; i != removedCols.size(); i++) {
    		if(removedCols.get(i) == value) {
    			index = value;
    		}
    	}
    	if(index != -1) {
    		removedCols.remove(index);
    	}
    }
    
    private boolean isRemoved(int value) {
    	boolean removed = false;
    	for(int i = 0; i != removedCols.size(); i++) {
    		if(removedCols.get(i) == value) {
    			removed = true;
    		}
    	}
    	return removed;
    }

    private void uncover(int row) {
    	int index = -1;
    	for(int i = 0; i != tmpSolution.size(); i++) {
    		if(tmpSolution.get(i) == row) {
    			index = i;
    		}
    	}
    	if(index != -1) {
    		tmpSolution.remove(index);
    	}
    	ArrayList<Integer> listCols = new ArrayList<Integer>();
    	for(int i = 0; i != coverCols; i++) {
    		if(backtracking[row][i] == 1) {
    			listCols.add(i);
    			undoCols(i);
    		}
    	}
    	ArrayList<Integer> listRows = new ArrayList<Integer>();
    	for(int i = 0; i != listCols.size(); i++) {
    		int col = listCols.get(i);
    		for(int j = 0; j != coverRows; j++) {
    			if(backtracking[j][col] == 1) {
    				listRows.add(j);
    			}
    		}
    		for(int k = 0; k != listRows.size(); k++) {
    			int subRow = listRows.get(k);
    			for(int a = 0; a != coverCols; a++) {
    				if(backtracking[subRow][a] == 1) {
    					backtracking[subRow][a] = 0;
    					matrix[subRow][a] = 1;
    				}
    			}
    		}
    		listRows.clear();
    	}
    }
    
    private int chooseCol() {
    	int min = Integer.MAX_VALUE;
    	int count = 0;
    	int col = -1;
    	for(int i = 0; i != coverCols; i++) {
    		for(int j = 0; j != coverRows; j++) {
    			if(matrix[j][i] == 1) {
    				count++;
    			}
    		}
    		if (count > 0) {
				if (count < min || col == -1) {
    				min = count;
    				col = i;
    			}
			}
    		count = 0;
    	}
    	return col;
    }

} // end of class AlgorXSolver
