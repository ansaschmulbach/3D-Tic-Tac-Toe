//Main class by Viansa S.  

import java.util.Scanner;

//main class
public class TicTacToe {

	//testing method
	public static void testNext(Node n) {
		int count = 0;
		for (Node node : n) {
			count++;
			System.out.println("Node " + count + ": ");
			node.board.print();
			System.out.println("\t Heuristic\t" + node.heuristicDebug());
		}
	}

	//loop to play the whole game (as the X position)
	public static void playX(Scanner scan) {
		Board.Mark mark = Board.Mark.X;
		Node current = new Node();
		int turnsTaken = 0;
		while (!current.board.isFull()) {
			if (current.board.hasWon(mark)) {
				System.out.println("I have won!");
				break;
			} else if (current.board.hasWon(mark.other())) {
				System.out.println("I have been beat!");
				break;
			}
			current = nextXMove(current, turnsTaken);
			if (current.board.hasWon(mark)) {
				System.out.println("I have won!");
				break;
			} else if (current.board.hasWon(mark.other())) {
				System.out.println("I have been beat!");
				break;
			}
			System.out.println("Enter your move: ");
			int playerMove = Position.position(scan.nextLine());
			Board b = new Board(current.board);
			b.set(playerMove, mark.other());
			current = new Node(b, mark);

		}

	}

	//loop to play the whole game (as the O position)
	public static void playO(Scanner scan) {
		Board.Mark mark = Board.Mark.O;
		Node current = new Node();
		int turnsTaken = 0;
		while (!current.board.isFull()) {

			if (current.board.hasWon(mark)) {
				System.out.println("I have won!");
				break;
			} else if (current.board.hasWon(mark.other())) {
				System.out.println("I have been beat!");
				break;
			}
			System.out.println("Enter your move: ");
			int playerMove = Position.position(scan.nextLine());
			Board b = new Board(current.board);
			b.set(playerMove, mark.other());
			current = new Node(b, mark);
			if (current.board.hasWon(mark)) {
				System.out.println("I have won!");
				break;
			} else if (current.board.hasWon(mark.other())) {
				System.out.println("I have been beat!");
				break;
			}
			current = nextOMove(current, turnsTaken);
			turnsTaken += 2;
		}

	}

	
	//finds the next best move for X
	public static Node nextXMove(Node node, int turnsTaken) {
		int maximum = Integer.MIN_VALUE;
		Node next = null;
		int count = 0;
		for (Node n : node) {
			int i = minimum(n, Board.Mark.X, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 4);
			if (i > maximum) {
				next = n;
				maximum = i;
			}
			count++;
			loadingBar(count, turnsTaken);
		}
		next.board.print();
		System.out.println("My move: " + Position.toString(Positions.position(~node.board.x & next.board.x)));
		return next;

	}

	//finds the next best move for O
	public static Node nextOMove(Node node, int turnsTaken) {
		int maximum = Integer.MIN_VALUE;
		Node next = null;
		int count = 0;
		for (Node n : node) {
			int i = minimum(n, Board.Mark.O, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 4);
			if (i > maximum) {
				next = n;
				maximum = i;
			}
			count++;
			loadingBar(count, turnsTaken);
		}
		next.board.print();
		System.out.println("My move: " + Position.toString(Positions.position(~node.board.o & next.board.o)));
		return next;

	}

	// d is for cutoff
	//mini function in minimax, returns the minimum of the top choices
	public static int minimum(Node node, Board.Mark mark, int alpha, int beta, int d, int cutoff) {
		int minimum = Integer.MAX_VALUE;
		// if leaf node test case-- is it a win? a lose?
		// if it's a win return __?
		if (node.board.hasWon(mark)) {
			return Integer.MAX_VALUE;
		}
		if (node.board.isFull()) {
			return 0;
		}

		if (cutoff == d) {
			return node.heuristic();
		}

		//commented section is for move ordering
		/**
		 * Node[] nodeArray = new Node[64]; int i = 0; for (Node n : node) {
		 * nodeArray[i] = n; i++; }
		 * 
		 * Arrays.sort(nodeArray, 0, i);
		 **/

		for (Node n : node) {
			minimum = Integer.min(maximum(n, mark, alpha, beta, d + 1, cutoff), minimum);
			//alpha beta pruning
			if (minimum <= alpha) {
				return minimum;
			}
			beta = Integer.min(beta, minimum);

		}

		d++;
		return minimum;
	}

	// d is for cutoff
	//max function in minimax, returns the minimum of the top choices
	public static int maximum(Node node, Board.Mark mark, int alpha, int beta, int d, int cutoff) {
		int maximum = Integer.MIN_VALUE;

		// if leaf node test case-- is it a win? a lose?
		if (node.board.hasWon(mark.other())) {
			return Integer.MIN_VALUE;
		}
		if (node.board.isFull()) {
			return 0;
		}

		if (cutoff == d) {
			return node.heuristic();
		}

		/**
		 * Node[] nodeArray = new Node[64]; int i = 0; for (Node n : node) {
		 * nodeArray[i] = n; i++; }
		 **/

		//Arrays.sort(nodeArray, 0, i);

		for (Node n : node) {
			maximum = Integer.max(minimum(n, mark, alpha, beta, d + 1, cutoff), maximum);
			//alpha beta pruning
			if (maximum >= beta) {
				return maximum;
			}
			alpha = Integer.max(alpha, maximum);

		}

		d++;
		return maximum;
	}

	public static void loadingBar(int prog, int turnsTaken) {
		int size = 30;
		float perc = (float) prog / (64 - turnsTaken);
		int cur = (int) (size * perc);
		String bar = "[";
		for (int i = 0; i < size; i++) {
			if (i < cur) {
				bar += "=";
			} else if (i == cur) {
				bar += ">";
			} else {
				bar += " ";

			}
		}
		System.out.print("\r" + bar + "] (" + (int) (perc * 100) + "%)  ");

	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Board b = new Board();
		// testNext(new Node(b, Board.Mark.X));
		System.out.println("Which position would you like to play? ");
		String mark = scan.next() + scan.nextLine();
		
		if (mark.equals("X")) {
			playO(scan);
		} else {
			playX(scan);
		}

		// testNext(n2);
	}

}
