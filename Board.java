//Viansa Schmulbach
//Mr. Paige
//3D Tic Tac Toe
//Nov. 19 2019

public class Board {

    public static enum Mark {
    
        X("X") { public Mark other() { return O; }},
        O("O") { public Mark other() { return X; }},
        BLANK(".");

        private String image;

        private Mark(String image) {
            this.image = image;
        }

        public String toString() {
            return image;
        }

        public Mark other() {
            return null;
        }
    }


    public long x;  // Positions of the X's
    public long o;  // Positions of the O's

    public Board() {
        this.x = 0;
        this.o = 0;
    }

    public Board(Board board) {  // Copy constructor
        this.x = board.x;
        this.o = board.o;
    }

    public Board(String s) {     // Inverse of toString()
        this.x = 0;
        this.o = 0;
        int position = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch(c) {
                case '.':
                    this.set(position, Mark.BLANK);
                    position++;
                    break;

                case 'x':
                case 'X':
                    this.set(position, Mark.X);
                    position++;
                    break;

                case 'o':
                case 'O':
                    this.set(position, Mark.O);
                    position++;
                    break;

                case ' ':  // Row divider
                case '|':  // Level divider
                    break;
                
                default:
                    throw new IllegalArgumentException("Bad board marker: " + c);
            }
        }
    }

    
    // Selector/settor methods to determine/alter the contents of a board position.

    public boolean isEmpty(int position) {
        return ! Positions.contains(this.x | this.o, position);
    }

    public boolean isEmpty(int x, int y, int z) {
        return isEmpty(Position.position(x, y, z));
    }

    public Mark get(int position) {
        if (Positions.contains(this.x, position)) return Mark.X;
        if (Positions.contains(this.o, position)) return Mark.O;
        return Mark.BLANK;
    }

    public Mark get(int x, int y, int z) {
        return get(Position.position(x, y, z));
    }


    public void set(int position, Mark mark) {
        switch (mark) {
            case X:
                this.x = Positions.add(this.x, position);
                this.o = Positions.remove(this.o, position);
                break;

            case O:
                this.x = Positions.remove(this.x, position);
                this.o = Positions.add(this.o, position);
                break;
    
            case BLANK:
                this.x = Positions.remove(this.x, position);
                this.o = Positions.remove(this.o, position);
                break;

            default:
                throw new IllegalArgumentException(mark.toString());
        }
    }

    public void set(int x, int y, int z, Mark mark) {
        set(Position.position(x, y, z), mark);
    }


    public void clear(int position) {
        this.x = Positions.remove(this.x, position);
        this.o = Positions.remove(this.o, position);
    }

    public void clear(int x, int y, int z) {
        clear(Position.position(x, y, z));
    }


    public int occupied(long positions, Mark player) {

        // Returns the number of positions occupied by the specified 
        // player within a given set of board positions.  Useful
        // when combined with the Lines class to determine the
        // number of markers along given line.

        switch (player) {
            case X: return Positions.count(this.x & positions);
            case O: return Positions.count(this.o & positions);
            case BLANK: return Positions.count (~(this.x | this.o) & positions);
            default: throw new IllegalArgumentException(player.toString());
        }
    }

    public boolean hasWon(Board.Mark mark) {
    	for (Line line : Line.lines()) {
    		if(occupied(line.positions(), mark) == 4) return true; 
    	}
    	return false;
    }

    public boolean isFull() {
    	return ~(this.x | this.o) == 0;
    }
    
    // Matching equality and hashCode methods.

    public boolean equals(Board other) {
        return this.x == other.x && this.o == other.o;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Board && equals((Board) other);
    }

    @Override
    public int hashCode() {
        return ((Long) this.x).hashCode() + ((Long) this.o).hashCode();
    }


    // toString produces a string that can be passed to the constructor.

    @Override
    public String toString() {
        String result = "";

        for (int position = 0; position < 64; position++) {
            if (position > 0) {
                if (position % 16 == 0) {  // New level
                    result += '|';
                } else if (position % 4 == 0) {
                    result += ' ';         // New row
                }
            }
            result += get(position);
        }
        return result;
    }


    // A more readable display of the 3D tic tac toe board.

    public void print() {
        System.out.println();
        for (int level = 0; level < 4; level++) {
            System.out.print(" Z = " + level + "    ");
        }
        System.out.println();
        for (int row = 3; row >= 0; row--) {
            for (int level = 0; level < 4; level++) {
                for (int column = 0; column < 4; column++) {
                    System.out.print(get(column, row, level));
                    System.out.print(" ");
                }
                System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public int heuristic(Mark mark) {
        Board.Mark other = mark.other();
        int count = 0;
        for (Line l : Line.lines()) {
            if (occupied(l.positions(), other) == 0) {
                count += occupied(l.positions(), mark);
            }
        }
        
        return count;
    }

    
}
