/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */
package grid;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * Class implementing the grid for Killer Sudoku.
 * Extends SudokuGrid (hence implements all abstract methods in that abstract
 * class).
 * You will need to complete the implementation for this for task E and
 * subsequently use it to complete the other classes.
 * See the comments in SudokuGrid to understand what each overriden method is
 * aiming to do (and hence what you should aim for in your implementation).
 */
public class KillerSudokuGrid extends SudokuGrid
{
    // TODO: Add your own attributes
	private Cage[] listCage;

    public KillerSudokuGrid() {
        super();

        // TODO: any necessary initialisation at the constructor
    } // end of KillerSudokuGrid()


    /* ********************************************************* */


    @Override
    public void initGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	ArrayList<ArrayList<Integer> > list = new ArrayList<ArrayList<Integer>>(); 
    	File file = new File(filename);
    	Scanner sc = new Scanner(file);
    	
    	while(sc.hasNextLine()) {
    		String filtered = sc.nextLine().replaceAll("[^0-9,]"," ");
    		String[] line = filtered.split("\\,|\\s+");
    		ArrayList<Integer> arrInteger = new ArrayList<Integer>();
    		for(int i = 0; i != line.length; i++)
    		{
    		    arrInteger.add(Integer.parseInt(line[i]));
    		}
    		list.add(arrInteger);
    	}
    	sc.close();
    	
    	initializeGrid(list);
    } // end of initBoard()
    
    @Override
    public void initializeGrid(ArrayList<ArrayList<Integer>> list) {
    	createGrid(list.get(0).get(0));
    	for(int i = 0; i < list.get(1).size(); i++) {
    		value[i] = list.get(1).get(i);
    	}
    	listCage = new Cage[list.get(2).get(0)];

    	for(int i = 3; i != list.size(); i++) {
    		int j = 0;
    		Cage cage = new Cage(list.get(i).get(j));
    		++j;
    		while(j < list.get(i).size()) {
    			Cell cell = new Cell(list.get(i).get(j), list.get(i).get(j+1),0);
    			j += 2;
    			cage.addCell(cell);
    		}
    		listCage[i-3] = cage;	
    	}
    }


    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	File file = new File(filename);
    	PrintWriter output = new PrintWriter(file);
    	for(int row = 0; row != size; row++) {
    		for(int col = 0; col != size; col++) {
    			output.print(grid[row][col] + ",");
    		}
    		output.println();
    	}
    	output.close();
    } // end of outputBoard()


    @Override
    public String toString() {
        // TODO
    	StringBuilder output = new StringBuilder();
    	output.append(size).append("\n");
    	for(int i = 0; i != size; i++) {
    		output.append(value[i]).append(" ");
    	}
    	output.append("\n");
    	for(int i = 0; i != size; i++) {
    		for(int j = 0; j != size; j++) {
    			String value = i+ "," + j + " " + grid[i][j] + "\n";
    			output.append(value);
    		}
    	}
        // placeholder
        return String.valueOf(output);

    } // end of toString()


    @Override
    public boolean validate() {
        // TODO
    	Set<Integer> rChecker = new HashSet<>();
    	Set<Integer> cChecker = new HashSet<>();
    	Set<Integer> bChecker = new HashSet<>();
    	
    	int bRStart = 0;
    	int bCStart = 0;
    	
    	// Validate row and column
    	for(int row = 0; row != size; row++) {
    		for(int col = 0; col != size; col++) {
    			int rNum = grid[row][col];
    			int cNum = grid[col][row];
    			
    			if((rNum != 0 && !rChecker.add(rNum)) 
    					|| (cNum != 0 && !cChecker.add(cNum))) {
    				return false;
    			}
    		}
    		rChecker.clear();
    		cChecker.clear();
    	}

    	while(bRStart < size) {
    		while(bCStart < size) {
	    		for(int row = bRStart; row != bRStart + sqrt; row++) {
	    			for(int col = bCStart; col != bCStart + sqrt; col++) {
	    				int bNum = grid[row][col];
	    				if(bNum != 0 && !bChecker.add(bNum)) {
	    					return false;
	    				}
	    			}
	    		}
	    		bCStart += sqrt;
    			bChecker.clear();
    		}
    		bRStart += sqrt;
    		bCStart = 0;
    	}
    	
    	for(int i = 0; i != listCage.length; i++) {
    		if(!listCage[i].isValid()) {
    			return false;
    		}
    	}
        // placeholder
        return true;
    } // end of validate()
    
	@Override
	public void print() {
		// TODO Auto-generated method stub	
		for(int i = 0; i != size; i++) {
			for(int j = 0; j != size; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	@Override
	public void setValue(int row, int col, int value) {
		grid[row][col] = value;
		for(int i = 0; i != listCage.length; i++) {
			for(int j = 0; j != listCage[i].nCells(); j++) {
				Cell cell = listCage[i].getCell(j);
				if(cell.row() == row && cell.col() == col) {
					cell.setValue(row, col, value);
				}
			}
		}
	}


	@Override
	public boolean validateNewCell(int row, int col, int value) {
		// TODO Auto-generated method stub
		for(int i = 0; i != size; i++) {
			if(grid[row][i] == value) {
				return false;
			}
		}
		for(int i = 0; i != size; i++) {
			if(grid[i][col] == value) {
				return false;
			}
		}
		int bRow = row - row % sqrt;
		int bCol = col - col % sqrt;
		for(int i = bRow; i < (bRow + sqrt); i++) {
			for(int j = bCol; j < (bCol + sqrt); j++) {
				if(grid[i][j] == value) {
					return false;
				}
			}
		}
		for(int i = 0; i != listCage.length; i++) {
			Cage cage = listCage[i];
			for(int j = 0; j != cage.nCells(); j++) {
				Cell cell = cage.getCell(j);
				if(cell.row() == row && cell.col() == col && !cage.isCellValid(j, value)) {
					return false;
				}
			}
		}
		return true;
		
	}
	

} // end of class KillerSudokuGrid
