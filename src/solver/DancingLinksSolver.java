/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import java.util.ArrayList;

import grid.SudokuGrid;


/**
 * Dancing links solver for standard Sudoku.
 */
public class DancingLinksSolver extends StdSudokuSolver
{
    // TODO: Add attributes as needed.
	private int[][] result;

    public DancingLinksSolver() {
        // TODO: any initialisation you want to implement.
    } // end of DancingLinksSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of the dancing links solver for Killer Sudoku.
    	transformToMatrix(grid);
    	
        DLX dlx = new DLX(matrix);
        dlx.solve();
        
        ArrayList<ActionNode> solution = dlx.getSolution();
        if(solution != null) {
        	convertToGrid(solution);
            for(int i = 0; i != size; i++) {
            	for(int j = 0; j != size; j++) {
            		grid.setValue(i, j, result[i][j]);
            	}
            }
            return true;
        }
     
        // placeholder
        return false;
    } // end of solve()
    
    private void convertToGrid(ArrayList<ActionNode> solution) {
    	result = new int[size][size];
    	for(ActionNode n : solution) {
    		ActionNode newNode = n;
    		int mValue = Integer.parseInt(newNode.col.name);

    		for (ActionNode tmp = n.right; tmp != n; tmp = tmp.right) {
    			int value = Integer.parseInt(tmp.col.name);

    			if (value < mValue) {
    				mValue = value;
    				newNode = tmp;
    			}
    		}

    		int index1 = Integer.parseInt(newNode.col.name);
    		int index2 = Integer.parseInt(newNode.right.col.name);
    		int row = index1 / size;
    		int col = index1 % size;
    		int valueIndex = (index2 % size)+1;
    		int value = valueRange[valueIndex-1];
    		result[row][col] = value;
    	}
    }


} // end of class DancingLinksSolver
