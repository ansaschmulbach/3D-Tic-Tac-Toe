//Viansa Schmulbach
//Mr. Paige
//3D Tic Tac Toe
//Nov. 19 2019

public class Positions {

    // A class to represent a set of positions on the 3D Tic-Tac-Toe board.
    // This set can be viewed as a 64-element boolean array (value value of
    // true in the ith position indicates that the ith position is in the
    // set.  For efficiency, these sets are stored as a long (each of the
    // 64-bits of the long representing one element of the array).  This
    // representation is exposed as a concrete data type (long) in order
    // to avoid the memory allocation overhead of creating objects of
    // a java class.


    // Is a specified position in the set?

    public static boolean contains(long positions, int position) {
        assert position >= 0 && position < 64;
        long mask = 1L << position;
        return (positions & mask) != 0;
    }

    public static boolean contains(long positions, int x, int y, int z) {
        return contains(positions, Position.position(x, y, z));
    }


    // Add a specified position to the set:

    public static long add(long positions, int position) {
        assert position >= 0 && position < 64;
        long mask = 1L << position;
        return positions | mask;
    }

    public static long add(long positions, int x, int y, int z) {
        return add(positions, Position.position(x, y, z));
    }


    // Remove a specified position from the set:

    public static long remove(long positions, int position) {
        assert position >= 0 && position < 64;
        long mask = 1L << position;
        return positions & ~mask;
    }

    public static long remove(long positions, int x, int y, int z) {
        return remove(positions, Position.position(x, y, z));
    }


    // How many positions are in the set:

    public static int count(long positions) {
        int count = 0;
        for (long mask = 1; mask != 0; mask <<= 1) {
            if ((positions & mask) != 0) count++;
        }
        return count;
    }


    // Basic set operations:

    public static long complement(long these) {
        return ~these;
    }

    public static long intersection(long these, long those) {
        return these & those;
    }

    public static long union(long these, long those) {
        return these | those;
    }


    public static String toString(long positions) {
        String result = "{";
        boolean first = true;

        for (int position = 0; position < 64; position++) {
            if (contains(positions, position)) {
                if (!first) result += ", ";
                result += Position.toString(position);
                first = false;
            }
        }

        result += "}";
        return result;
    }
    
    public static int position(long positions) {
    	for (int i = 0; i < 64; i++) {
    		if (1 << i == positions) {
    			return i;
    		}
    	}
    	return 0;
    }
    
}
