/**
 * Maze represents a two-dimensional grid of MazeCells, with rows and columns
 * both indexed from 0 to their respective dimensions - 1. Maze instances are
 * defined using a string where '#' represents a wall and '-' represents an open
 * area. Thus, the string:
 *
 * <pre>
 * ######
 * ------
 * ####-#
 * ####-#
 * </pre>
 *
 * <p>...will create a 6 &times; 4 maze with a horizontal &ldquo;hallway&rdquo;
 * running on row 1, and vertical passage running down column 4, from row 1 to
 * row 3.</p>
 */
public class Maze {
    /**
     * The character to use for describing a maze wall.
     */
    private static final char WALL_DESCRIPTOR = '#';

    /**
     * The character to use for describing an open area.
     */
    private static final char OPEN_DESCRIPTOR = '-';

    /**
     * The maze's cells.
     */
    private MazeCell[][] mazeCells;

    /**
     * Creates the maze from the given string. To define a maze with m columns
     * and n rows, the provided string must consist of n m-character sequences
     * separated by whitespace. Each m-character sequence consists of '#'s and
     * '-'s: a '#' represents a "wall" cell while a '-' represents an open cell.
     * An IllegalArgumentException is thrown if the provided string somehow
     * violates these constraints; a NullPointerException is thrown if the
     * provided string is null.
     */
    public Maze(String description) {
        // Break the description up into its individual rows.
        String[] rowStrings = description.trim().split("\\s+");

        // Initial sanity check: empty mazes are not allowed.
        if (rowStrings.length == 0) {
            throw new IllegalArgumentException("Empty maze.");
        }

        mazeCells = new MazeCell[rowStrings.length][rowStrings[0].length()];
        for (int row = 0; row < mazeCells.length; row++) {
            // Pass a couple of consistency checks first.
            if (!rowStrings[row].matches("[" + WALL_DESCRIPTOR + OPEN_DESCRIPTOR + "]+")) {
                throw new IllegalArgumentException("Illegal characters in maze.");
            }

            if (rowStrings[row].length() != rowStrings[0].length()) {
                throw new IllegalArgumentException("Non-rectangular maze.");
            }

            for (int column = 0; column < mazeCells[row].length; column++) {
                mazeCells[row][column] = (rowStrings[row].charAt(column) == OPEN_DESCRIPTOR)
                        ? MazeCell.OPEN :
                          MazeCell.WALL;
            }
        }
    }

    /**
     * Returns the width of the maze.
     */
    public int getMazeWidth() {
        return mazeCells[0].length;
    }

    /**
     * Returns the height of the maze.
     */
    public int getMazeHeight() {
        return mazeCells.length;
    }

    /**
     * Returns the location at the given coordinates.
     */
    public Location getLocation(int row, int column) {
        return new Location(row, column);
    }

    /**
     * Location is a helper class representing a particular row and column
     * within the maze, with methods returning whether or not the position is
     * within the maze, whether or not the position is open, and its neighbors.
     */
    public final class Location {
        /**
         * The row of the location.
         */
        private int row;

        /**
         * The column of the location.
         */
        private int column;

        /**
         * Private constructor --- we only allow Maze objects to create locations.
         */
        private Location(int row, int column) {
            this.row = row;
            this.column = column;
        }

        /**
         * Returns the row of the location.
         */
        public int getRow() {
            return row;
        }

        /**
         * Returns the column of the location.
         */
        public int getColumn() {
            return column;
        }

        /**
         * Returns whether or not the maze location is legal (i.e., within maze bounds).
         */
        public boolean isLegal() {
            return (column >= 0) && (column < getMazeWidth())
                    && (row >= 0) && (row < getMazeHeight());
        }

        /**
         * Returns whether or not the maze location is open (i.e., not a wall).
         */
        public boolean isOpen() {
            return isLegal() && (mazeCells[row][column] == MazeCell.OPEN);
        }

        /**
         * Returns the location above this one.
         */
        public Location getAbove() {
            return new Location(row - 1, column);
        }

        /**
         * Returns the location below this one.
         */
        public Location getBelow() {
            return new Location(row + 1, column);
        }

        /**
         * Returns the location to the left of this one.
         */
        public Location getLeft() {
            return new Location(row, column - 1);
        }

        /**
         * Returns the location to the right of this one.
         */
        public Location getRight() {
            return new Location(row, column + 1);
        }

        /**
         * Returns the cell at this location.
         */
        public MazeCell getMazeCell() {
            return mazeCells[row][column];
        }
    }
}
