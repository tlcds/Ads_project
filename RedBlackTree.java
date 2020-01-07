
import java.util.*;

public class RedBlackTree{
	// root
	private RBNode root;
	// node color
	private static final boolean Red = false;
	private static final boolean Black = true;

	public RedBlackTree() {root = null;}

	public RBNode getroot() {
		return(this.root);
}

	// ************************************************************************************************
	// Print (buildingNum)
	// ************************************************************************************************

	public void Print(int buildingNum) {
		RBNode rbnode = this.root;
		while(rbnode != null) {
			int curr = rbnode.getBuildingNum();
			if(buildingNum > curr) {
				rbnode = rbnode.right;
			}else if(buildingNum < curr) {
				rbnode = rbnode.left;
			}else {
				System.out.printf("BuildingNum: %d, Executed_Time: %d, Total_Time: %d\n",
						rbnode.getBuildingNum(), rbnode.getExecuted_time(), rbnode.getTotal_time());
				return;
			}
		}
		System.out.println("(0,0,0)");
	}

	// ************************************************************************************************
	// Print (buildingNum1, buildingNum2)
	// ************************************************************************************************

	public void Print(int buildingNum1, int buildingNum2) {

		String output = "";

		Stack<RBNode> s = new Stack<>();
		RBNode rbnode = this.root;

		while(rbnode != null || s.size() > 0) {
			while(rbnode != null) {
				s.push(rbnode);
				rbnode = rbnode.left;
			}
			rbnode = s.pop();

			if(rbnode.getBuildingNum() >= buildingNum1 && rbnode.getBuildingNum() <= buildingNum2) {
				output += ("(" + Integer.toString(rbnode.getBuildingNum()) + ","
						+ Integer.toString(rbnode.getExecuted_time()) + ","
								+ Integer.toString(rbnode.getTotal_time()) + "),");
			}

			rbnode = rbnode.right;
		}
		if(output.length() == 0) {
			System.out.println("(0,0,0)");
		}else {
			System.out.println(output.substring(0,output.length()-1));
		}

	}
	// ************************************************************************************************
	// Insert
	// ************************************************************************************************

	public void insert (Triplet triplet) {
		RBNode node = new RBNode(triplet, Red, null, null, null);
		int buildingNum = triplet.getBuildingNum();
		RBNode n = this.root;
		RBNode m = null;

		if(n == null) this.root = node;
		//
		while(n != null) {
			m = n;
			int curr = n.getBuildingNum();
			if(curr < buildingNum) {
				n = n.right;
			}else if(curr > buildingNum) {
				n = n.left;
			}else {
				throw new IllegalArgumentException("The building already exists.");
			}
		}

		node.parent = m;
		//set the parent node of the inserted node
		if(m != null) {
			if(m.getBuildingNum() < buildingNum) {
				m.right = node;
			}else {
				m.left = node;
			}
		}else {
			this.root = node;
		}
		// fix up the tree after insertion
		RBNode pp = m;
		while(pp!= null && isRed(pp)) {
			RBNode gp = pp.parent;
			if(gp == null) break;
			if(pp == gp.left) {
				//LYr
				if(gp.right != null && isRed(gp.right)) {
					gp.right.color = Black;
					gp.color = Red;
					pp.color = Black;
					node = gp;
					pp = node.parent;
					continue;
				}
				//LRb
				if(pp.right == node) {
					rotateL(pp);
					RBNode temp = pp;
					pp = node;
					node = temp;
				}
				//LLb
				pp.color = Black;
				gp.color = Red;
				rotateR(gp);
				pp = node.parent;
			} else {
				//RYr
				if(gp.left != null && isRed(gp.left)) {
					gp.left.color = Black;
					gp.color = Red;
					pp.color = Black;
					node = gp;
					pp = node.parent;
					continue;
				}
				//RLb
				if(pp.left == node) {
					rotateR(pp);
					RBNode temp = pp;
					pp = node;
					node = temp;
				}
				//RRb
				pp.color = Black;
				gp.color = Red;
				rotateL(gp);
				pp = node.parent;
			}
		}
		this.root.color = Black;
	}


	// ************************************************************************************************
	// Delete
	// ************************************************************************************************

	public void delete (int buildingNum) {
		// search for the node by buildingNum
		RBNode rbnode = this.root;
		while(rbnode != null) {
			int curr = rbnode.getBuildingNum();
			if(buildingNum > curr) {
				rbnode = rbnode.right;
			}else if(buildingNum < curr) {
				rbnode = rbnode.left;
			}else {
				break;
			}
		}

		RBNode xc, xp;
		boolean color;

		// If rbnode has both left and right child nodes
		if(rbnode.left != null && rbnode.right != null) {
			// make x be the least node of rbnode's left subtree.
			RBNode x = rbnode;
			x = x.right;
			while(x.left != null) {
				x = x.left;
			}

			// If rbnode is not the root (has parent node),
			// make the parent node of rbnode the new parent node of x
			if(rbnode != null && rbnode.parent != null) {
				if(rbnode.parent.left == rbnode) {
					rbnode.parent.left = x;
				}else {
					rbnode.parent.right = x;
				}
			}else {
				this.root = x;
			}


			xc = x.right;
			xp = x.parent;
			color = x.color;

			if(rbnode == xp) {
				xp = x;
			}else {
				// If x has right child node
				// make the parent of x (xp) the new parent of x's right child node
				if(xc != null) {
					xc.parent = xp;
				}
				xp.left = xc;
				x.right = rbnode.right;
				rbnode.right.parent = x;
			}

			x.parent = rbnode.parent;
			x.color = rbnode.color;
			x.left = rbnode.left;
			rbnode.left.parent = x;
			//If the x is a black node,
			//it would break the rule of Red Black Tree.
			//Hence, we need to fix up the tree by deleteHelper method.
			if(color == Black) { deleteHelper(xc,xp);}

			rbnode = null;
			return;
		}

		// If rbnode only have one child node
		// we can simply make rbnode's subtree the new subtree of rbnode's parent node
		if(rbnode.right != null) {
			xc = rbnode.right;
		}else {
			xc = rbnode.left;
		}

		xp = rbnode.parent;
		color = rbnode.color;

		if(xc != null) {
			xc.parent = xp;
		}
		// if rbnode is not root
		if(xp != null) {
			if(xp.left == rbnode) {
				xp.left = xc;
			}else {
				xp.right = xc;
			}
		}else {
			this.root = xc;
		}
		//If the rbnode is a black node,
		//it would break the rule of Red Black Tree.
		//Hence, we need to fix up the tree by deleteHelper method.
		if(color == Black) { deleteHelper(xc,xp);}
		rbnode = null;
	}


	private void deleteHelper(RBNode rbnode, RBNode parent) {
		RBNode n;
		while((rbnode == null || !isRed(rbnode)) && rbnode != this.root) {
			if(parent.left == rbnode) {
				// rbnode is left child node of parent
				n = parent.right;
				if(isRed(n)) {
					// case 1: n is red
					n.color = Black;
					parent.color = Red;
					rotateL(parent);
					n = parent.right;
				}

				if((n.left == null || !isRed(n.left)) &&
				   (n.right == null || !isRed(n.right))) {
				   	// case2: n is black and has two black child nodes
					n.color = Red;
					rbnode = parent;
					parent = rbnode.parent;
				}else {
					if(n.right == null || !isRed(n.right)) {
						//case3: n is black and has red left child node and black right child node
						n.left.color = Black;
						n.color = Red;
						rotateR(n);
						n = parent.right;
					}
					//case4: n is black and has red right child node
					n.color = parent.color;
					parent.color = Black;
					n.right.color = Black;
					rotateL(parent);
					rbnode = this.root;
					break;
				}
			}else {
				// rbnode is right child node of parent
				n = parent.left;
				// case1: n is red
				if(isRed(n)) {
					n.color = Black;
					parent.color = Red;
					rotateR(parent);
					n = parent.left;
				}
				// case2: n is black and has two black child nodes
				if((n.left == null || !isRed(n.left)) &&
				   (n.right == null || !isRed(n.right))) {
					n.color = Red;
					rbnode = parent;
					parent = rbnode.parent;
				}else {
					if(n.left == null || !isRed(n.left)) {
						//case3: n is black and has red right child node and black left child node
						n.right.color = Black;
						n.color = Red;
						rotateL(n);
						n = parent.left;
					}
					//case4: n is black and has red right child node
					n.color = parent.color;
					parent.color = Black;
					n.left.color = Black;
					rotateR(parent);
					rbnode = this.root;
					break;
				}
			}
		}
		if(rbnode != null) {
			rbnode.color = Black;
		}
	}


	// ************************************************************************************************
	// Other Methods
	// ************************************************************************************************

	// check if the color of RBNode is red
	private boolean isRed(RBNode rbnode) {
		return((rbnode != null) && (rbnode.color == Red)) ? true : false;}

	/* Left Rotate
		     /              /
		   node            r
		   /  \     =>    / \
          l    r        node r2
              / \       / \
	         r1  r2    l  r1
	*/

	private void rotateL (RBNode node) {
		RBNode r = node.right;
		node.right = r.left;
		if(r.left != null) {r.left.parent = node;}
		r.parent = node.parent;

		if(node.parent == null) {
			this.root = r;
		}else {
			//check node is the right/left child of its parent node
			//make the parent node the parent of r
			if(node.parent.right == node) {
				node.parent.right = r;
			}else {node.parent.left = r;}
		}
		r.left = node;
		node.parent = r;
	}

	/* Right Rotate
		     /              /
		   node            l
		   /  \     =>    / \
          l    r        l1  node
         / \                /  \
	    l1 l2              l2   r
	*/
	private void rotateR (RBNode node) {
		RBNode l = node.left;
		node.left = l.right;
		if(l.right != null) {l.right.parent = node;}

		l.parent = node.parent;

		if(node.parent == null) {
			this.root = l;
		}else {
			//check node is the right/left child of its parent node
			//make the parent node the parent of l
			if(node.parent.right == node) {
				node.parent.right = l;
			}else {node.parent.left = l;}
		}
		l.right = node;
		node.parent = l;

	}
}
