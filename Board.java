import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

//Creates one instance of the board object.
public class Board {
	public final char[][] data;
	// Create previous move field for game to keep track of all moves.

	// Creates a blank board for the start of game.
	public Board() {
		data = new char[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				data[i][j] = '.';
			}
		}
	}

	// Creates a new board based on 2d-array data.
	// Used to output new Board objects.
	public Board(char[][] temp) {
		data = temp;
	}

	// Copies data from board into a new 2d-array list
	public char[][] copyData() {
		char[][] temp = new char[6][6];
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 6; x++) {
				temp[x][y] = data[x][y];
			}
		}
		return temp;
	}

	// Returns a board twisted to the right.
	public Board twistRight(int blockNumber) {
		char[][] newBoard = new char[6][6];
		int x = 0;
		int y = 0;
		if (blockNumber == 2) {
			x = 3;
		} else if (blockNumber == 3) {
			y = 3;
		} else if (blockNumber == 4) {
			x = 3;
			y = 3;
		}
		newBoard[0 + x][0 + y] = data[0 + x][2 + y];
		newBoard[1 + x][0 + y] = data[0 + x][1 + y];
		newBoard[2 + x][0 + y] = data[0 + x][0 + y];

		newBoard[0 + x][1 + y] = data[1 + x][2 + y];
		newBoard[1 + x][1 + y] = data[1 + x][1 + y];
		newBoard[2 + x][1 + y] = data[1 + x][0 + y];

		newBoard[0 + x][2 + y] = data[2 + x][2 + y];
		newBoard[1 + x][2 + y] = data[2 + x][1 + y];
		newBoard[2 + x][2 + y] = data[2 + x][0 + y];
		if (blockNumber != 1) {
			for (int tx = 0; tx < 3; tx++) {
				for (int ty = 0; ty < 3; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}
		if (blockNumber != 2) {
			for (int tx = 3; tx < 6; tx++) {
				for (int ty = 0; ty < 3; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}
		if (blockNumber != 3) {
			for (int tx = 0; tx < 3; tx++) {
				for (int ty = 3; ty < 6; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}
		if (blockNumber != 4) {
			for (int tx = 3; tx < 6; tx++) {
				for (int ty = 3; ty < 6; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}

		Board a = new Board(newBoard);
		return a;
	}

	// Twists the chosen block to the left.
	public Board twistLeft(int blockNumber) {
		char[][] newBoard = new char[6][6];
		int x = 0;
		int y = 0;
		if (blockNumber == 2) {
			x = 3;
		} else if (blockNumber == 3) {
			y = 3;
		} else if (blockNumber == 4) {
			x = 3;
			y = 3;
		}
		newBoard[0 + x][0 + y] = data[2 + x][0 + y];
		newBoard[1 + x][0 + y] = data[2 + x][1 + y];
		newBoard[2 + x][0 + y] = data[2 + x][2 + y];

		newBoard[0 + x][1 + y] = data[1 + x][0 + y];
		newBoard[1 + x][1 + y] = data[1 + x][1 + y];
		newBoard[2 + x][1 + y] = data[1 + x][2 + y];

		newBoard[0 + x][2 + y] = data[0 + x][0 + y];
		newBoard[1 + x][2 + y] = data[0 + x][1 + y];
		newBoard[2 + x][2 + y] = data[0 + x][2 + y];
		if (blockNumber != 1) {
			for (int tx = 0; tx < 3; tx++) {
				for (int ty = 0; ty < 3; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}
		if (blockNumber != 2) {
			for (int tx = 3; tx < 6; tx++) {
				for (int ty = 0; ty < 3; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}
		if (blockNumber != 3) {
			for (int tx = 0; tx < 3; tx++) {
				for (int ty = 3; ty < 6; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}
		if (blockNumber != 4) {
			for (int tx = 3; tx < 6; tx++) {
				for (int ty = 3; ty < 6; ty++) {
					newBoard[tx][ty] = data[tx][ty];
				}
			}
		}

		Board a = new Board(newBoard);
		return a;
	}

	// Gets all places where a letter can be placed.
	public ArrayList<TreeNode> getPossibleMoves(char p, int turn, int childDepth) {
		Map<Board, String> movesAndCoordinates = new HashMap<Board, String>();

		Set<Board> addLocations = new HashSet<Board>();
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 6; x++) {
				if (data[x][y] == '.') {
					char[][] temp = copyData();
					temp[x][y] = p;
					Board b = new Board(temp);
					addLocations.add(b);
					String coordinates = x + "/" + y; // FIX THIS IN DISPLAY
														// AREA!!!!!!
														// COORDINATES HAVE TO
														// BE IN FORMAT
					movesAndCoordinates.put(b, coordinates);
				}
			}
		}

		ArrayList<TreeNode> children = new ArrayList<TreeNode>();
		for (Board b : addLocations) {
			String coordin = movesAndCoordinates.get(b);
			if (b.gameOver('w') != -1) {
				TreeNode over = new TreeNode(b, turn, p, childDepth);
				over.previousMove = coordin;
				children.add(over);
			} else {
				Board oneLeft = b.twistLeft(1);
				TreeNode a1 = new TreeNode(oneLeft, turn, p, childDepth);
				a1.previousMove = coordin + " 1L";
				children.add(a1);
				Board oneRight = b.twistRight(1);
				TreeNode a2 = new TreeNode(oneRight, turn, p, childDepth);
				a2.previousMove = coordin + " 1R";
				children.add(a2);
				Board twoLeft = b.twistLeft(2);
				TreeNode a3 = new TreeNode(twoLeft, turn, p, childDepth);
				a3.previousMove = coordin + " 2L";
				children.add(a3);
				Board twoRight = b.twistRight(2);
				TreeNode a4 = new TreeNode(twoRight, turn, p, childDepth);
				a4.previousMove = coordin + " 2R";
				children.add(a4);
				Board threeLeft = b.twistLeft(3);
				TreeNode a5 = new TreeNode(threeLeft, turn, p, childDepth);
				a5.previousMove = coordin + " 3L";
				children.add(a5);
				Board threeRight = b.twistRight(3);
				TreeNode a6 = new TreeNode(threeRight, turn, p, childDepth);
				a6.previousMove = coordin + " 3R";
				children.add(a6);
				Board fourLeft = b.twistLeft(4);
				TreeNode a7 = new TreeNode(fourLeft, turn, p, childDepth);
				a7.previousMove = coordin + " 4L";
				children.add(a7);
				Board fourRight = b.twistRight(4);
				TreeNode a8 = new TreeNode(fourRight, turn, p, childDepth);
				a8.previousMove = coordin + " 4R";
				children.add(a8);
			}
		}
		return children;
	}

	// Adds a move to a board and returns it
	public Board addMove(char player, int boardPosition, int blockNumber) {
		char[][] temp = data;
		int x = 0;
		int y = 0;
		if (boardPosition == 1 || boardPosition == 4 || boardPosition == 7) {
			x = 0;
		} else if (boardPosition == 2 || boardPosition == 5 || boardPosition == 8) {
			x = 1;
		} else if (boardPosition == 3 || boardPosition == 6 || boardPosition == 9) {
			x = 2;
		}

		if (boardPosition == 1 || boardPosition == 2 || boardPosition == 3) {
			y = 0;
		} else if (boardPosition == 4 || boardPosition == 5 || boardPosition == 6) {
			y = 1;
		} else if (boardPosition == 7 || boardPosition == 8 || boardPosition == 9) {
			y = 2;
		}

		if (blockNumber == 2) {
			x = x + 3;
		} else if (blockNumber == 3) {
			y = y + 3;
		} else if (blockNumber == 4) {
			x = x + 3;
			y = y + 3;
		}

		temp[x][y] = player;
		return new Board(temp);
	}

	// Returns true when either player has 5+ in a row or the game board is full
	// and no one
	// has won.
	public int gameOver(char p1Color) { //0 = p1 win. 1 = p2 win
										// 2 = tie. -1 = not over
		int out = -1;
		int count = 0;
		int bothWon = 0;
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 6; x++) {
				if (data[x][y] != '.') {
					count++;
				}
			}
		}
		if (getHoriz('w') >= 5 || getVertical('w') >= 5 || getDiagonalUp('w') >= 5 || getDiagonalDown('w') >= 5) {
			bothWon++;
			if (p1Color == 'w') {
				out = 0;
			} else {
				out = 1;
			}
		}

		if (getHoriz('b') >= 5 || getVertical('b') >= 5 || getDiagonalUp('b') >= 5 || getDiagonalDown('b') >= 5) {
			bothWon++;
			if (p1Color == 'b') {
				out = 0;
			} else {
				out = 1;
			}
		}
		if (count == 36 || bothWon == 2) {
			out = 2; // TIE
		}
		return out;
	}

	
	public static void main(String[] args) {
		Board b = new Board();
		b.addMove('w', 1, 1);
		b.addMove('w', 5, 1);
		b.addMove('w', 9, 1);
		b.addMove('w', 1, 4);
		b.addMove('w', 4, 4);
		System.out.println(b.gameOver('w'));
		
		System.out.println(b.getHoriz('w'));
		System.out.println(b.getHeuristic('w'));
		b.draw();
	}
	
	
	
	
	
	
	
	
	
	
	// Returns the value of the given board. 10 if AI wins, -10 if human wins.
	// Else it is the difference between the longest straight between the
	// players.
	public int getHeuristic(char AIColor) {
		int h;
		int temp1 = Math.max(getHoriz('b'), getVertical('b'));
		int temp2 = Math.max(getDiagonalDown('b'), getDiagonalUp('b'));
		int bMax = Math.max(temp1, temp2);

		int temp3 = Math.max(getHoriz('w'), getVertical('w'));
		int temp4 = Math.max(getDiagonalDown('w'), getDiagonalUp('w'));
		int wMax = Math.max(temp3, temp4);
		if (AIColor == 'b') {
			if (wMax >= 5) { // Human wins
				return -10;
			} else if (bMax >= 5) { // AI wins
				return 10;
			}
			h = bMax - wMax;
		} else {
			if (wMax >= 5) { // AI wins
				return 10;
			} else if (bMax >= 5) { // Human wins
				return -10;
			}
			h = wMax - bMax;
		}
		return h;
	}

	// Gets the maximum length horizontal for the player.
	public int getHoriz(char player) {
		boolean looking = false;
		int max = 0;
		int count = 0;
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 6; x++) {
				if (looking == false && data[x][y] == player) {
					looking = true;
					count = 1;
				} else if (looking == true && data[x][y] == player) {
					count++;
				} else {
					if (count > max) {
						max = count;
					}
					looking = false;
					count = 0;
				}
			}
		}
		return max - 1;
	}

	// Gets the maximum diagonal down for the player.
	public int getDiagonalDown(char player) {
		int max = 0;
		for (int y = 0; y < 6; y++) {
			for (int x = 0; x < 6; x++) {
				if (data[x][y] == player) {
					// System.out.println("X " + x + " Y " + y);
					int count = 1;
					int xcheck = x;
					int ycheck = y;
					while (xcheck < 6 && ycheck < 6 && data[xcheck][ycheck] == player) {
						count++;
						xcheck++;
						ycheck++;
					}
					if (count > max) {
						max = count;
					}
				}
			}
		}
		return max - 1;
	}

	// Gets the maximum diagonal up for the player.
	public int getDiagonalUp(char player) {
		int max = 0;
		for (int y = 5; y >= 0; y--) {
			for (int x = 0; x < 6; x++) {
				if (data[x][y] == player) {
					// System.out.println("X " + x + " Y " + y);
					int count = 1;
					int xcheck = x;
					int ycheck = y;
					while (xcheck < 6 && ycheck >= 0 && data[xcheck][ycheck] == player) {
						count++;
						xcheck++;
						ycheck--;
					}
					if (count > max) {
						max = count;
					}
				}
			}
		}
		return max - 1;
	}

	// Gets the maximum vertical for the player.
	public int getVertical(char player) {
		boolean looking = false;
		int max = 0;
		int count = 0;
		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 6; y++) {
				if (looking == false && data[x][y] == player) {
					looking = true;
					count = 1;
				} else if (looking == true && data[x][y] == player) {
					count++;
				} else {
					if (count > max) {
						max = count;
					}
					looking = false;
					count = 0;
				}
			}
		}
		return max - 1;

	}

	// Draws the board in console.
	public void draw() {
		for (int y = 0; y < 6; y++) {
			System.out.println(data[0][y] + "" + data[1][y] + "" + data[2][y] + "" + data[3][y] + "" + data[4][y] + ""
					+ data[5][y]);
		}
		System.out.println();
	}
}
