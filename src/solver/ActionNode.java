package solver;

public class ActionNode {
	public ActionNode left, right, up, down;
	public ColumnNode col;
	
	public ActionNode() {
		left = right = up = down = this;
	}
	
	public ActionNode(ColumnNode col) {
		this();
		this.col = col;
	}
	
	public ActionNode linkVertical(ActionNode node) {
		node.down = down;
		node.down.up = node;
		node.up = this;
		this.down = node;
		return node;
	}
	
	public ActionNode linkHorizontal(ActionNode node) {
		node.right = right;
		node.right.left = node;
		node.left = this;
		this.right = node;
		return node;
	}
	
	public void removeHorizontal() {
		this.right.left = left;
		this.left.right = right;
	}
	
	public void removeVertical() {
		this.up.down = down;
		this.down.up = up;
	}
	
	public void insertVertical() {
		this.left.right = this;
		this.right.left = this;
	}
	
	public void insertHorizontal() {
		this.up.down = this;
		this.down.up = this;
	}
}
