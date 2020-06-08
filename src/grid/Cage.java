package grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Cage {
	private ArrayList<Cell> cells;
	private int totalValue;
	private List<Stack<Integer>> tmpSolution = new ArrayList<Stack<Integer>>();
	private boolean valid;
	public Set<Integer> rows = new HashSet<>();
	public Set<Integer> cols = new HashSet<>();
	public Set<Integer> nonets = new HashSet<>();
	public Set<Integer> pv;
	
	public Cage(int value) {
		cells = new ArrayList<Cell>();
		totalValue = value;
		valid = false;
	}
	
	public void addCell(Cell cell) {
		cells.add(cell);
		rows.add(cell.row());
		cols.add(cell.col());
		nonets.add(cell.getNonet());
	}
	
	public boolean setRelationship(Set<Integer> aRows, Set<Integer> aCols, Set<Integer> aNonets) {
		if(Collections.disjoint(rows, aRows) && Collections.disjoint(cols, aCols)
				&& Collections.disjoint(nonets, aNonets)) {
			return false;
		}
		return true;
	}
	
	public int getValue() {
		return totalValue;
	}
	
	public Cell getCell(int index) {
		return cells.get(index);
	}
	
	public boolean isValid() {
		int total = 0;
		for(int i = 0; i != cells.size(); i++) {
			int value = cells.get(i).value();
			if(value == 0 && total < totalValue) {
				return true;
			} else {
				total += value;
			}
			
		}
		if(total == totalValue) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isCellValid(int index, int value) {
		if(value > totalValue) {
			return false;
		}
		for(int i = 0; i != cells.size(); i++) {
			if(i != index) {
				int cValue = cells.get(i).value();
				if(cValue == 0) {
					return true;
				} else {
					value += cValue;
					if(value > totalValue) {
						return false;
					}
				}
			}
		}
		if(value == totalValue) {
			return true;
		} else {
			return false;
		}
	}
	
	public int nCells() {
		return cells.size();
	}
	
	public int getPsSize() {
		return tmpSolution.size();
	}

	public void setSolutions(List<Stack<Integer>> ps, Set<Integer> pv) {
		this.pv = new HashSet<Integer>(pv);
		generatePermutation(ps);
	}
	
	public void generatePermutation(List<Stack<Integer>> ps) {
		for(Stack<Integer> as : ps) {
			permute(as, 0);
		}
	}
	
	public void permute(Stack<Integer> as, int depth) {
		if(depth == as.size()-1) {
			Stack<Integer> toAdd = new Stack<Integer>();
			for(Integer value : as) {
				toAdd.add(value);
			}
			tmpSolution.add(toAdd);
		}
		for(int i = depth; i != as.size(); i++) {
			Collections.swap(as, i, depth);
			permute(as, depth + 1);
			Collections.swap(as, depth, i);
		}
	}
	
	public List<Cell> getCellArray() {
		return cells;
	}
	
	public boolean isCageFilled() {
		for(Cell cell : cells) {
			if(cell.value() == 0) {
				return false;
			}
		}
		return true;
	}
	
	public List<Stack<Integer>> getTmpSolution() {
		return tmpSolution;
	}
	
	public void setValid(boolean valid) {
		if(!valid) {
			for(Cell aCell : cells) {
				aCell.setValue(0);
			}
		}
		this.valid = valid;
	}
	
	public boolean checked() {
		return this.valid;
	}
	
	public boolean isRowUnique() {
		return rows.size() == 1 ? true : false;
	}
	
	public boolean isColUnique() {
		return cols.size() == 1 ? true : false;
	}
	
	public boolean isNonetUnique() {
		return nonets.size() == 1 ? true : false;
	}
	
	public int getRow() {
		int row = -1;
		for(Integer nRow : rows) {
			row = nRow;
		}
		return row;
	}
	
	public int getCol() {
		int col = -1;
		for(Integer nCol : cols) {
			col = nCol;
		}
		return col;
	}
	
	public int getNonet() {
		int nonet = -1;
		for(Integer nNonet : nonets) {
			nonet = nNonet;
		}
		return nonet;
	}

	public void removePs(Stack<Integer> removed) {
		tmpSolution.remove(removed);
	}
}
