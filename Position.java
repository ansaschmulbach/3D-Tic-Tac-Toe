//Viansa Schmulbach
//Mr. Paige
//3D Tic Tac Toe
//Nov. 19 2019

public class Position {

    // Board positions can be viewed as a triple (x,y,z) representing the
    // the 3D coordinates of each square on the 4x4x4 Tic-Tac-Toe board.
    // For efficency, these coordinates are stored as an index into a
    // canonical ordering of the board positions.  Furthermore, this
    // representation is exposed as a concrete data type (int) in order
    // to avoid the memory allocation overhead of creating objects of
    // a java class.


    public static int position(int x, int y, int z) {
        assert x >= 0 && x < 4;
        assert y >= 0 && y < 4;
        assert z >= 0 && z < 4;
        return x + (y << 2) + (z << 4);
    }

    public static int position(String s) {
        String[] fields = s.split("[, ]");

        if (fields.length != 3) {
            throw new IllegalArgumentException(s);
        }

        int x = Integer.parseInt(fields[0]);
        int y = Integer.parseInt(fields[1]);
        int z = Integer.parseInt(fields[2]);

        if (x < 0 || x >= 4) throw new IllegalArgumentException(s);
        if (y < 0 || y >= 4) throw new IllegalArgumentException(s);
        if (z < 0 || z >= 4) throw new IllegalArgumentException(s);

        return position(x, y, z);
    }

    public static int x(int position) {
        assert position >= 0 && position < 64;
        return position & 3;
    }

    public static int y(int position) {
        assert position >= 0 && position < 64;
        return (position >> 2) & 3;
    }

    public static int z(int position) {
        assert position >= 0 && position < 64;
        return (position >> 4);
    }

    public static String toString(int position) {
        return toString(x(position), y(position), z(position));
    }

    public static String toString(int x, int y, int z) {
        return "(" + x + "," + y + "," + z + ")";
    }
}
