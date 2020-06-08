package grid;

import java.util.HashSet;
import java.util.Set;

public class Cell {
	
	private int row;
	private int col;
	private int finalValue;
	private int nonet;
	public Set<Integer> exclusive = new HashSet<Integer>();
		
	public Cell(int row, int col, int sqrt) {
		this.row = row;
		this.col = col;
		this.finalValue = 0;
		setNonet(sqrt);
	}
	
	public Cell(int row, int col, int nonet, int value) {
		this.row = row;
		this.col = col;
		this.finalValue = value;
		this.nonet = nonet;
	}
	
	public int value() {
		return finalValue;
	}
	
	public void setValue(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.finalValue = value;
	}
	
	public int row() {
		return this.row;
	}
	
	public int col() {
		return this.col;
	}
	
	public int getNonet() {
		return this.nonet;
	}
	
	public void setNonet(int sqrt) {
		int horizontal = (int) Math.floor(row/sqrt);
		int vertical = (int) Math.floor(col/sqrt);
		this.nonet = horizontal*sqrt + vertical;
	}
	
	public void setValue(int value) {
		this.finalValue = value;
	}
	
	public boolean isCell(int row, int col, int nonet) {
		if(row == this.row && col == this.col && nonet == this.nonet) {
			return true;
		} else {
			return false;
		}
	}
	
	public Cell getDeepCopy(){
		Cell copy = new Cell(row, col, nonet, finalValue);
		return copy;
	}

	public void addExclusive(Set<Integer> pv) {
		for(Integer value : pv) {
			exclusive.add(value);
		}
	}

	public boolean isExclusiveEmpty() {
		return (exclusive.size() == 0) ? true : false;
	}

	public boolean isContain(int value) {
		return exclusive.contains(value);
	}
}
