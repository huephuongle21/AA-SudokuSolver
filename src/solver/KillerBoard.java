package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import grid.Cage;
import grid.Cell;

public class KillerBoard {
	private List<Cell> cells = new ArrayList<Cell>();
	private List<Cage> cages = new ArrayList<Cage>();
	
	public KillerBoard(Cage[] cage) {
		for(Cage aCage : cage) {
			cages.add(aCage);
		}
		for(Cage aCage : cages) {
			cells.addAll(aCage.getCellArray());
		}
		sortCellValue();
		sortCageValue();
		sortCage();
	}

	private void sortCageValue() {
		List<Stack<Integer>> toRemove = new ArrayList<Stack<Integer>>();
		for(Cage aCage : cages) {
			for(int i = 0; i != aCage.getCellArray().size(); i++) {
				int index = i;
				Cell cell = aCage.getCell(index);
				if(!cell.isExclusiveEmpty()) {
					for(Stack<Integer> as : aCage.getTmpSolution()) {
						if(cell.isContain(as.get(index))) {
							toRemove.add(as);
						}
					}
				}
			}
			if(toRemove.size() != 0) {
				for(Stack<Integer> removed : toRemove) {
					aCage.removePs(removed);
				}
			}
		}
	}
	
	private void sortCellValue() {
		for(Cage aCage : cages) {
			if(aCage.pv.size() == aCage.nCells()) {
				if(aCage.isRowUnique()) {
					sortRow(aCage.getRow(), aCage.pv, aCage.getCellArray());
				}
				if(aCage.isColUnique()) {
					sortCol(aCage.getCol(), aCage.pv, aCage.getCellArray());
				}
				if(aCage.isNonetUnique()) {
					sortNonet(aCage.getNonet(), aCage.pv, aCage.getCellArray());
				}
			}
		}
	}
	
	
	private void sortRow(int row, Set<Integer> pv, List<Cell> lCells) {
		for(Cell cell : cells) {
			if(!isCellEqual(lCells, cell) && cell.row() == row) {
				cell.addExclusive(pv);
			}
		}
	}
	
	private void sortCol(int col, Set<Integer> pv, List<Cell> lCells) {
		for(Cell cell : cells) {
			if(!isCellEqual(lCells, cell) && cell.col() == col) {
				cell.addExclusive(pv);
			}
		}
	}
	
	private boolean isCellEqual(List<Cell> cells, Cell cell) {
		boolean equal = false;
		for(Cell aCell : cells) {
			if(cell.equals(aCell) ) {
				equal = true;
			}
		}
		return equal;
	}
	
	private void sortNonet(int nonet, Set<Integer> pv, List<Cell> lCells) {
		for(Cell cell : cells) {
			if(!isCellEqual(lCells, cell) && cell.getNonet() == nonet) {
				cell.addExclusive(pv);
			}
		}
	}
	
	public void sortCage() {
		Collections.sort(cages, new Comparator<Cage>() {
		    @Override
		    public int compare(Cage lhs, Cage rhs) {
		    	if(lhs.setRelationship(rhs.rows, rhs.cols, rhs.nonets)) {
		    		return lhs.getPsSize() < rhs.getPsSize() ? -1 : (lhs.getPsSize() > rhs.getPsSize()) ? 1 : 0;
		    	} else {
		    		return 0;
		    	}
		    }		
		});
	}
	
	public List<Cage> getCages() {
		return cages;
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
}
