import java.io.FileNotFoundException;
import java.io.IOException;

import grid.KillerSudokuGrid;
import grid.StdSudokuGrid;
import grid.SudokuGrid;
import solver.AlgorXSolver;
import solver.BackTrackingSolver;
import solver.DancingLinksSolver;
import solver.KillerBackTrackingSolver;
import solver.StdSudokuSolver;
import solver.SudokuSolver;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SudokuGrid std = new StdSudokuGrid();
		SudokuGrid killer = new KillerSudokuGrid();
		AlgorXSolver solveX = new AlgorXSolver();
		DancingLinksSolver dancingLink = new DancingLinksSolver();
		KillerBackTrackingSolver kBacktrack = new KillerBackTrackingSolver();
		BackTrackingSolver backtrack = new BackTrackingSolver();
		
		try {
			std.initGrid("1.in");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		std.print();
//		backtrack.solve(std);
//		System.out.println();
//		std.print();
//		
		std.print();
		dancingLink.solve(std);
		std.print();
		
	}

}
