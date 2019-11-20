//Board classes by Richard Paige, 2019.  

public class Line {

    // A class that provides an enumeration of all 76 possible 
    // winning lines in the game of 3D Tic Tac Toe.  Each line
    // has a set of the four positions comprising the line and
    // a human readable description (name) for the line.


    private long positions;
    private String name;

    private Line(long positions, String name) {
        this.positions = positions;
        this.name = name;
    }

    public long positions() {
        return this.positions;
    }

    public String name() {
        return this.name;
    }

    public boolean equals(Line other) {
        return this.positions == other.positions;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Line && equals((Line) other);
    }

    @Override
    public int hashCode() {
        return ((Long) this.positions).hashCode();
    }

    @Override
    public String toString() {
        return Positions.toString(positions);
    }

    public static Line[] lines() {  // The list of winning lines.
        return Line.lines;
    }

    private static long mask(int x, int y, int z) {
        int position = x + (y << 2) + (z << 4);
        return 1L << position;
    }

    private static Line xLine(int y, int z) {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, y, z);
        }
        return new Line(line, "X-Axis: Y = " + y + ", Z = " + z);
    }

    private static Line yLine(int x, int z) {
        long line = 0;
        for (int y = 0; y < 4; y++) {
            line |= mask(x, y, z);
        }
        return new Line(line, "Y-Axis: X = " + x + ", Z = " + z);
    }

    private static Line zLine(int x, int y) {
        long line = 0;
        for (int z = 0; z < 4; z++) {
            line |= mask(x, y, z);
        }
        return new Line(line, "Z-Axis: X = " + x + ", Y = " + y);
    }

    private static Line xyForwardDiagonal(int z) {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, x, z);
        }
        return new Line(line, "XY-Forward Diagonal: Z = " + z);
    }

    private static Line xyReverseDiagonal(int z) {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask (x, 3-x, z);
        }
        return new Line(line, "XY-Reverse Diagonal: Z = " + z);
    }
        
    private static Line xzForwardDiagonal(int y) {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, y, x);
        }
        return new Line(line, "XZ-Forward Diagonal: Y = " + y);
    }

    private static Line xzReverseDiagonal(int y) {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask (x, y, 3-x);
        }
        return new Line(line, "XZ-Reverse Diagonal: Y = " + y);
    }
        
    private static Line yzForwardDiagonal(int x) {
        long line = 0;
        for (int y = 0; y < 4; y++) {
            line |= mask(x, y, y);
        }
        return new Line(line, "YZ-Forward Diagonal: X = " + x);
    }

    private static Line yzReverseDiagonal(int x) {
        long line = 0;
        for (int y = 0; y < 4; y++) {
            line |= mask (x, y, 3-y);
        }
        return new Line(line, "YZ-Reverse Diagnonal: X = " + x);
    }

    private static Line xyzForwardForwardDiagonal() {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, x, x);
        }
        return new Line(line, "XYZ-Forward-Forward Diagonal");
    }

    private static Line xyzForwardReverseDiagonal() {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, x, 3-x);
        }
        return new Line(line, "XYZ-Forward-Reverse Diagonal");
    }

    private static Line xyzReverseForwardDiagonal() {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, 3-x, x);
        }
        return new Line(line, "XYZ-Reverse-Forward Diagonal");
    }

    private static Line xyzReverseReverseDiagonal() {
        long line = 0;
        for (int x = 0; x < 4; x++) {
            line |= mask(x, 3-x, 3-x);
        }
        return new Line(line, "XYZ-Reverse-Reverse Diagonal");
    }
        

    private static Line[] lines = new Line[76];

    static {
        int count = 0;

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                lines[count++] = zLine(x, y);
            }
        }

        for (int x = 0; x < 4; x++) {
            for (int z = 0; z < 4; z++) {
                lines[count++] = yLine(x, z);
            }
        }

        for (int y = 0; y < 4; y++) {
            for (int z = 0; z < 4; z++) {
                lines[count++] = xLine(y, z);
            }
        }

        for (int x = 0; x < 4; x++) {
            lines[count++] = yzForwardDiagonal(x);
            lines[count++] = yzReverseDiagonal(x);
        }

        for (int y = 0; y < 4; y++) {
            lines[count++] = xzForwardDiagonal(y);
            lines[count++] = xzReverseDiagonal(y);
        }

        for (int z = 0; z < 4; z++) {
            lines[count++] = xyForwardDiagonal(z);
            lines[count++] = xyReverseDiagonal(z);
        }

        lines[count++] = xyzForwardForwardDiagonal();
        lines[count++] = xyzForwardReverseDiagonal();
        lines[count++] = xyzReverseForwardDiagonal();
        lines[count++] = xyzReverseReverseDiagonal();
    }

}
