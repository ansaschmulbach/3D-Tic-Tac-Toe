//Node classes by Viansa S.

import java.util.Iterator;

//represents a Node (with a board and heuristic)
public class Node implements Iterable<Node>, Comparable<Node> {
    
    Board board;
    Board.Mark mark;
    int heuristic;
    
    
    public Node() {
    	this.board = new Board();
    	this.mark = Board.Mark.X;
    	heuristic = heuristic();
    }
    
    public Node(Board board, Board.Mark mark) {
        this.board = board;
        this.mark = mark;
    }
    
    //returns a linear approximation of the utility of the board
    public int heuristic() {
        Board.Mark other = mark.other();
        int count = 0;
        for (Line l : Line.lines()) {
            if (board.occupied(l.positions(), other) == 0) {
                count += board.occupied(l.positions(), mark);
            }
        }
        
        return count;
    }

    public int heuristicDebug() {
        Board.Mark other = mark.other();
        System.out.print("Mark: " + mark + "\t" + "Other: " + other);
        int count = 0;
        for (Line l : Line.lines()) {
            if (board.occupied(l.positions(), other) == 0) {
                count += board.occupied(l.positions(), mark);
            }
        }
        
        return count;
    }
    
    @Override
    public Iterator<Node> iterator() {
        return new NodeIterator();
    }
    
    //Iterator iterates over node's next possible states
    public class NodeIterator implements Iterator<Node> {
        long emptyPositions;
        int position;
        
        public NodeIterator() {
            emptyPositions = ~(board.x | board.o);
            position = 0;
        }
        
        @Override
        public boolean hasNext() {
            //return true if there's empty spaces that haven't been filled
            return emptyPositions != 0;
        }

        
        @Override
        public Node next() {
            //return next possible node
    
            while (!Positions.contains(emptyPositions, position)) {
                position++;
            }
            
            
            emptyPositions = Positions.remove(emptyPositions, position);
            Board b = new Board(board);
            b.set(position, mark);
            Node node = new Node(b, mark.other());
            position++;
            return node;
        }
        
    }
    
    public int compareTo(Node other) {
    	return this.heuristic - other.heuristic;
    }
    
    public boolean equals(Node other) {
    	return this.board.equals(other.board);
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other != null && other instanceof Node) {
    		return equals((Node)other);
    	}
    	return false;
    }
    

}
