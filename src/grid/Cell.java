package grid;

public class Cell {
	private int row;
	private int col;
	private int value;
		
	public Cell(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	public int value() {
		return value;
	}
	
	public void setValue(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	public int row() {
		return this.row;
	}
	
	public int col() {
		return this.col;
	}
	
	//getter and setter for value
		
}
