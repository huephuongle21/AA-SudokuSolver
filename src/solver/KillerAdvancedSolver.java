/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import grid.Cage;
import grid.Cell;
import grid.SudokuGrid;


/**
 * Your advanced solver for Killer Sudoku.
 */
public class KillerAdvancedSolver extends KillerSudokuSolver
{
    // TODO: Add attributes as needed.
	private SudokuGrid grid;
	private KillerBoard killerBoard;
	private List<Cage> cages;
	private List<Cell> cells;
	private List<Cell> finalSolution;
	
	static ArrayList<Constraint> rowConstraints = new ArrayList<Constraint>();
	static ArrayList<Constraint> colConstraints = new ArrayList<Constraint>();
	static ArrayList<Constraint> nonetConstraints = new ArrayList<Constraint>();

    public KillerAdvancedSolver() {
        // TODO: any initialisation you want to implement.
    } // end of KillerAdvancedSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of your advanced solver for Killer Sudoku.
    	this.grid = grid;
    	generatePossibleSolution();
    	
    	// Initialize
    	convertToBoard();
    	cages = killerBoard.getCages();
    	cells = killerBoard.getCells();
    	initializeConstraint();
    	
    	process();
    	if(finalSolution != null) {
    		convertToGrid();
    		return true;
    	}
        // placeholder
        return false;
    } // end of solve()
    
    private void convertToGrid() {
		for(Cell aCell : finalSolution) {
			grid.setValue(aCell.row(), aCell.col(), aCell.value());
		}
	}
    
	private void process() {
		Cage toTry = pickCage();
		if(toTry == null) {	
			finalSolution = new ArrayList<Cell>();
			for(Cell aCell : cells) {
				finalSolution.add(aCell.getDeepCopy());
			}
		} else {
			for(Stack<Integer> value : toTry.getTmpSolution()) {
				int index = 0;
    			for(Cell cell : toTry.getCellArray()) {
    				int toSet = value.get(index);
    				if(isCellValid(cell.row(), cell.col(), cell.getNonet(), toSet)) {
    					cell.setValue(toSet);
    					index++;
    				} else {
    					break;
    				}
    			}
    			if(toTry.isCageFilled()) {
    				toTry.setValid(true);
    				process();
    			} else {
    				toTry.setValid(false);
    			}
    			toTry.setValid(false);
			}
		}
	}

	private void convertToBoard() {
    	killerBoard = new KillerBoard(grid.getCage());
	}
    
    private Cage pickCage() {
    	for(Cage aCage : cages) {
    		if(!aCage.checked()) {
    			return aCage;
    		}
    	}
    	return null;
    }

	private void generatePossibleSolution() {
    	for(Cage cage : grid.getCage()) {
    		int total = cage.getValue();
			int size = cage.nCells();
			int sum = 0;
			Stack<Integer> oneSolution = new Stack<Integer>();
			List<Stack<Integer>> possibleSolutions = new ArrayList<Stack<Integer>>();
			Set<Integer> pv = new HashSet<Integer>();
			possibleSolutions = calculateSum(oneSolution, sum, 0, total, size, possibleSolutions, pv);
			cage.setSolutions(possibleSolutions, pv);
    	}
	}

	private List<Stack<Integer>> calculateSum(Stack<Integer> oneSolution, int sum, int sIndex,
			int total, int size, List<Stack<Integer>> listSolution, Set<Integer> pv) {
    	int[] listValue = grid.getValue();

        if (sum == total && oneSolution.size() == size) {
            Stack<Integer> aSolution = new Stack<Integer>();

            for(Integer value : oneSolution) {
                aSolution.add(value);
                pv.add(value);
            }

            listSolution.add(aSolution);
            return listSolution;
        }

        for (int cIndex = sIndex; cIndex < grid.getSize(); cIndex++) {
        	int toAdd = listValue[cIndex];
            if(sum + toAdd <= total) {
            	oneSolution.add(toAdd);
                sum += toAdd;
                calculateSum(oneSolution, sum, cIndex + 1, total, size, listSolution, pv);
                sum -= oneSolution.pop();
            }
        }

        return listSolution;
    }
	
	private void initializeConstraint() {
		createRowConstraint();
		createColConstraint();
		createNonetConstraint();
	}
	
	private void createRowConstraint() {
		for(int i = 0; i != grid.getSize(); i++) {
			rowConstraints.add(new Constraint());
		}
		for(Cell aCell : cells) {
			int index = aCell.row();
			rowConstraints.get(index).addVariable(aCell);
		}
	}
	
	private void createColConstraint() {
		for(int i = 0; i != grid.getSize(); i++) {
			colConstraints.add(new Constraint());
		}
		for(Cell aCell : cells) {
			int index = aCell.col();
			colConstraints.get(index).addVariable(aCell);
		}
	}
	
	private void createNonetConstraint() {
		for(int i = 0; i != grid.getSize(); i++) {
			nonetConstraints.add(new Constraint());
		}
		for(Cell aCell : cells) {
			int index = aCell.getNonet();
			nonetConstraints.get(index).addVariable(aCell);
		}
	}
	
	private boolean isCellValid(int row, int col, int nonet, int value) {
		boolean valid = rowConstraints.get(row).isCellValid(value)
				&& colConstraints.get(col).isCellValid(value)
				&& nonetConstraints.get(nonet).isCellValid(value);
		return valid;
	}

} // end of class KillerAdvancedSolver
