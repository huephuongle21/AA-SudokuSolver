package solver;

public class ColumnNode extends ActionNode {
	public int size;
	public String name;
	
	public ColumnNode(String name) {
		super();
		this.name = name;
		this.size = 0;
		this.col = this;
	}
	
	public void cover() {
		removeHorizontal();
		for(ActionNode i = down; i != this; i = i.down) {
			for(ActionNode j = i.right; j != i; j = j.right) {
				j.removeVertical();
				j.col.size--;
			}
		}
	}
	
	public void uncover() {
		for(ActionNode i = up; i != this; i = i.up) {
			for(ActionNode j = i.left; j != i; j = j.left) {
				j.col.size++;
				j.insertVertical();
			}
		}
		insertHorizontal();
	}
}
