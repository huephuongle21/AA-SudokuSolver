package solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import grid.Cage;
import grid.Cell;

public class KillerBoard {
	private List<Cell> cells = new ArrayList<Cell>();
	private List<Cage> cages = new ArrayList<Cage>();
	
	public KillerBoard(Cage[] cage) {
		for(Cage aCage : cage) {
			cages.add(aCage);
		}
		sortCage();
		for(Cage aCage : cages) {
			cells.addAll(aCage.getCellArray());
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
