package grid;

import java.util.ArrayList;

public class Cage {
	private ArrayList<Cell> listCells;
	private int totalValue;
	
	public Cage(int value) {
		listCells = new ArrayList<Cell>();
		totalValue = value;
	}
	
	public void addCell(Cell cell) {
		listCells.add(cell);
	}
	
	public int getValue() {
		return totalValue;
	}
	
	public Cell getCell(int index) {
		return listCells.get(index);
	}
	
	public boolean isValid() {
		int total = 0;
		for(int i = 0; i != listCells.size(); i++) {
			int value = listCells.get(i).value();
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
		for(int i = 0; i != listCells.size(); i++) {
			if(i != index) {
				int cValue = listCells.get(i).value();
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
		return listCells.size();
	}
}
