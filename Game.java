import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

//Runs two person pentago with an AI.
//Randomly determines which player goes first.
public class Game {
	static Board b;
	static int currentTurn;
	static int startTurn;
	static String p1;
	static String p2;
	static char p1Color;
	static char p2Color;
	static File output;
	static int stuck;
	static ArrayList<String> turns;
	static Board bestChoice;
	static String winner;
	static ArrayList<Board> allMoves;
	static String currentAIMove;

	// Setups the game with given values.
	public static void main(String[] args) throws IOException {
		b = new Board();
		bestChoice = new Board();
		currentAIMove = "";
		turns = new ArrayList<String>();
		allMoves = new ArrayList<Board>();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Player 1 Name (player who moves first)");
		String username = scanner.nextLine();
		p1 = username;
		System.out.print("Player 2 Name");
		username = scanner.nextLine();
		p2 = username;
		System.out.print("Player 1 Token Color (B or W)");
		username = scanner.nextLine();
		p1Color = username.charAt(0);
		System.out.print("Player 2 Token Color (B or W)");
		username = scanner.nextLine();
		p2Color = username.charAt(0);
		int r = (int) Math.floor((Math.random() * 2) + 1);
		System.out.println("Player to Move Next (1 or 2): " + r);
		currentTurn = r;
		startTurn = r;
		playGame();
		writeMoveToFile();
		scanner.close();
	}

	// AI will always be called p1
	// The entire game loop until a player has won or a tie occurs.
	public static void playGame() {
		while (b.gameOver(p1Color) == -1 && b.getHeuristic(p1Color) != 10 & b.getHeuristic(p1Color) != -10) {
			allMoves.add(b);
			b.draw();
			if (currentTurn == 1) {
				TreeNode choiceTree = createTree(1);
				//alphabeta(choiceTree, 1, -10000000, 10000000, true);
				minMax(choiceTree,1,true);
				b = bestChoice;
				currentTurn = 2;
				turns.add(currentAIMove);
			} else {
				currentTurn = 1;
				System.out.println("Human move");
				Scanner scanner = new Scanner(System.in);
				String moveInfo = scanner.nextLine();
				moveInfo.toLowerCase();
				int x = Character.getNumericValue(moveInfo.charAt(0));
				int y = Character.getNumericValue(moveInfo.charAt(2));
				int blockRotate = Character.getNumericValue(moveInfo.charAt(4));
				char rotation = moveInfo.charAt(5);
				turns.add(moveInfo.substring(0, 6));
				b = b.addMove(p2Color, x, y);
				if (rotation == 'r') {
					b = b.twistRight(blockRotate);
				} else {
					b = b.twistLeft(blockRotate);
				}
			}
		}
		if (b.gameOver(p1Color) == 0) {
			winner = "The winner is p1, the AI.";
		}
		if (b.gameOver(p1Color) == 1) {
			winner = "The winner is p2, the human.";
		}
		if (b.gameOver(p1Color) == 2) {
			winner = "The game is a tie";
		}
	}

	public static int minMax(TreeNode node, int depth, boolean max) {
		if (depth == 0 || node.currentBoard.gameOver(p1Color) != -1) {
			if (node.currentBoard.gameOver(p1Color) != -1) {
				bestChoice = node.currentBoard; // Saves the best board
												// position.
			}
			return node.currentBoard.getHeuristic(p1Color);
		}
		if (max) {
			int best = -1000;
			for (TreeNode child : node.children) {
				int v = minMax(child, depth - 1, false);
				if (v > best && depth == 1) {
					bestChoice = child.currentBoard;
					currentAIMove = child.previousMove;
				}

				best = Math.max(best, v);
			}
			return best;
		} else {
			int best = 1000;
			for (TreeNode child : node.children) {
				int v = minMax(child, depth - 1, false);
				best = Math.min(best, v);
			}
			return best;
		}
	}

	
	
	// Returns the value of the best choice.
	// Also saves the best board position to a field.
	public static int alphabeta(TreeNode node, int depth, int a, int b, boolean max) {
		if (depth == 0 || node.currentBoard.gameOver(p1Color) != -1) {
			if (node.currentBoard.gameOver(p1Color) != -1) {
				bestChoice = node.currentBoard; // Saves the best board
												// position.
			}
			return node.currentBoard.getHeuristic(p1Color);
		}
		if (max) {
			int v = -1000;
			for (TreeNode child : node.children) {
				int c = alphabeta(child, depth - 1, a, b, false);
				if (c > v && depth == 1) {
					bestChoice = child.currentBoard;
					currentAIMove = child.previousMove;
				}
				v = Math.max(v, c);
				a = Math.max(a, v);
				if (b <= a) {
					break;
				}
			}
			return v;
		} else {
			int v = 1000;
			for (TreeNode child : node.children) {
				v = Math.min(v, alphabeta(child, depth - 1, a, b, true));
				b = Math.min(b, v);
				if (b <= a) {
					break;
				}
			}
			return v;
		}
	}

	// Creates entire tree based on current state of the game.
	public static TreeNode createTree(int maxDepth) {
		TreeNode root = new TreeNode(b, 0, p2Color, 0);
		treeHelper(0, maxDepth, root);
		return root;
	}

	// Helper function for building full tree for alpha beta pruning.
	public static void treeHelper(int currentDepth, int maxDepth, TreeNode node) {
		Queue<TreeNode> toExpand = new LinkedList<TreeNode>();
		toExpand.add(node);
		for (int i = 0; i < maxDepth; i++) {
			int levelSize = toExpand.size();
			for (int levelCount = 0; levelCount < levelSize; levelCount++) {
				TreeNode expand = toExpand.poll();
				expand.expand();
				for (TreeNode temp : expand.children) {
					toExpand.add(temp);
				}
			}
		}
	}

	// Writes entire game log to file.
	public static void writeMoveToFile() throws IOException {
		PrintStream fileStream = new PrintStream(new File("Output.txt"));
		fileStream.println(p1);
		fileStream.println(p2);
		fileStream.println(p1Color);
		fileStream.println(p2Color);
		fileStream.println(startTurn);
		// fileStream.println(".......");
		// fileStream.println(".......");
		// fileStream.println(".......");
		// fileStream.println(".......");
		// fileStream.println(".......");
		// fileStream.println(".......");
		// if (startTurn == 1) {
		// startTurn = 0;
		// } else {
		// startTurn = 1;
		// }
		// FIX THE COORDINATES FROM AI SIDE
		for (int i = 0; i < turns.size(); i++) {
			fileStream.println(startTurn);
			Board boardMove = allMoves.get(i);
			for (int y = 0; y < 6; y++) {
				fileStream.println(boardMove.data[0][y] + "" + boardMove.data[1][y] + "" + boardMove.data[2][y] + ""
						+ boardMove.data[3][y] + "" + boardMove.data[4][y] + "" + boardMove.data[5][y]);
			}
			for (int j = 0; j <= i; j++) {
				fileStream.println(turns.get(j));
			}
			if (startTurn == 1) {
				startTurn = 0;
			} else {
				startTurn = 1;
			}
			fileStream.println();
		}
		fileStream.println(winner);
		fileStream.close();
	}
}
