import java.util.ArrayList;

//Creates a TreeNode with children as possible moves.
public class TreeNode {
	int turn; // Keeps track if it is a max or min node. 1 = max 0 = min
	char color; // Color of
	Board currentBoard;
	int val;
	ArrayList<TreeNode> children;
	int depth;
	String previousMove;

	//Creates a TreeNode with given values.
	public TreeNode(Board b, int turn, char col, int d) {
		children = null;
		currentBoard = b;
		color = col;
		depth = d;
		previousMove = "";
		if (turn == 1) {
			val = b.getHeuristic(color); // Pass color of AI/p1
		} else {
			char colorSwitch;
			if (color == 'b') { // Switches color for child node
				colorSwitch = 'w';
			} else {
				colorSwitch = 'b';
			}
			val = b.getHeuristic(colorSwitch); // Pass color of AI/p1
		}
	}

	//Expands current node and creates the children.
	public void expand() {
		// switch color since it is expanding children
		char colorSwitch;
		if (color == 'b') { // Switches color for child node
			colorSwitch = 'w';
		} else {
			colorSwitch = 'b';
		}
		int turnSwitch; // Switches turn to for child node
		if (turn == 0) {
			turnSwitch = 1;
		} else {
			turnSwitch = 0;
		}
		children = currentBoard.getPossibleMoves(colorSwitch, turnSwitch, depth + 1);
	}

}
