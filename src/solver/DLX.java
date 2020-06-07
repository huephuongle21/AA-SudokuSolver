package solver;

import java.util.ArrayList;

public class DLX {
	private ColumnNode header;
	private ArrayList<ActionNode> tmpSolution = new ArrayList<ActionNode>();
	private ArrayList<ActionNode> returnSolution;
	
	public DLX(int[][] matrix) {
		header = createDLX(matrix);
	}
	
	private ColumnNode createDLX(int[][] matrix) {
		int nCol = matrix[0].length;
		ColumnNode header = new ColumnNode("header");
		ArrayList<ColumnNode> listColN = new ArrayList<ColumnNode>();
		
		for(int index = 0; index != nCol; index++) {
			ColumnNode colN = new ColumnNode(Integer.toString(index));
			listColN.add(colN);
			header = (ColumnNode) header.linkHorizontal(colN);
		}
		
		header = header.right.col;
		 
		for(int[] rMatrix : matrix) {
			ActionNode prev = null;
			for(int i = 0; i != nCol; i++) {
				if(rMatrix[i] == 1) {
					ColumnNode colN = listColN.get(i);
					ActionNode actionN = new ActionNode(colN);
					if(prev == null) {
						prev = actionN;	
					}
					colN.up.linkVertical(actionN);
					prev = prev.linkHorizontal(actionN);
					colN.size++;
					
				}
				
			}
			header.size = nCol;
		}
		return header;
	}
	
	public void solve() {
		if(header.right == header) {
			returnSolution = new ArrayList<ActionNode>(tmpSolution);		
		} else {
			ColumnNode colN = chooseCol();
			colN.cover();
			for(ActionNode i = colN.down; i != colN; i = i.down) {
				tmpSolution.add(i);
				
				for(ActionNode j = i.right; j != i; j = j.right) {
					j.col.cover();
				}
				solve();
				
				i = tmpSolution.remove(tmpSolution.size()-1);
				colN = i.col;
				
				for(ActionNode n = i.left; n != i; n = n.left) {
					n.col.uncover();
				}
			}
			colN.uncover();
		}	
	}
	
	private ColumnNode chooseCol() {
		ColumnNode colN = null;
		int s = Integer.MAX_VALUE;
		for(ColumnNode i = (ColumnNode) header.right; i != header; i = (ColumnNode) i.right) {
			if(i.size < s) {
				colN = i;
				s = i.size;
			}
		}
		return colN;
	}
	
	public ArrayList<ActionNode> getSolution() {
		return this.returnSolution;
	}
}
