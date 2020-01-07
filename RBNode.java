

public class RBNode{

	Triplet key;
	boolean color;
	RBNode left, right, parent;

	public RBNode(Triplet triplet, boolean color, RBNode left, RBNode right, RBNode parent) {
		this.key = triplet;
		this.color = color;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}

	public Triplet getkey() { return key;}
	public int getBuildingNum() { return key.getBuildingNum();}
	public int getExecuted_time() { return key.getExecuted_time();}
	public int getTotal_time() { return key.getTotal_time();}


}
