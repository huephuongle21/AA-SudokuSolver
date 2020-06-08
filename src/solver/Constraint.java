package solver;

import java.util.ArrayList;
import java.util.List;

import grid.Cell;

public class Constraint {
	private List<Cell> variables = new ArrayList<Cell>();
	
	public Constraint() {
		
	}

	public void addVariable(Cell aCell) {
		variables.add(aCell);
	}
	
	public boolean isCellValid(int value) {
		for(Cell aCell : variables ) {
			int toCheck = aCell.value();
			if(toCheck != 0 && toCheck == value) {
				return false;
			}
		}
		return true;
	}
}
