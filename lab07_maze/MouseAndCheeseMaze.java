import java.awt.Point;

/**
 * MouseAndCheeseMaze is the primary model object used by a maze solver. In
 * addition to a particular maze, it manages the location of a mouse (or rat)
 * and a piece of cheese. It then uses a MazeWalker to update the state of the
 * maze.
 */
public class MouseAndCheeseMaze {
    /**
     * Lists the possible results of the maze-solving algorithm.
     */
    public enum Result {
        /**
         * Indicates that the maze's walker is still looking for the cheese.
         */
        STILL_LOOKING,

        /**
         * Indicates that the maze's walker has found the cheese.
         */
        CHEESE_FOUND,

        /**
         * Indicates that the maze's walker has deemed the cheese to be
         * unreachable.
         */
        CHEESE_NOT_FOUND
    }

    /**
     * The maze to be solved.
     */
    private Maze maze;

    /**
     * The location of the mouse/rat.
     */
    private Point mouse;

    /**
     * The location of the cheese.
     */
    private Point cheese;

    /**
     * The walker that directs how the mouse should travel around the maze.
     */
    private MazeWalker mazeWalker;

    /**
     * Creates the MouseAndCheeseMaze.
     */
    public MouseAndCheeseMaze(Maze maze, int mouseRow, int mouseColumn, int cheeseRow, int cheeseColumn) {
        this.maze = maze;

        // Make sure that the mouse and cheese coordinates are open.
        if (!maze.getLocation(mouseRow, mouseColumn).isOpen() || !maze.getLocation(cheeseRow, cheeseColumn).isOpen()) {
            throw new IllegalArgumentException("Illegal rat or cheese coordinates: "
                    + "they must be within the maze and in open cells.");
        }

        mouse = new Point(mouseColumn, mouseRow);
        cheese = new Point(cheeseColumn, cheeseRow);
        mazeWalker = new MazeWalker(maze, cheeseRow, cheeseColumn);
    }

    /**
     * Returns the receiver's maze.
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Returns the solver's current mouse location.
     */
    public Point getMouse() {
        return mouse;
    }

    /**
     * Returns the solver's current cheese location.
     */
    public Point getCheese() {
        return cheese;
    }

    /**
     * Returns the receiver's maze walker.
     */
    public MazeWalker getMazeWalker() {
        return mazeWalker;
    }

    /**
     * Sets the solver's new mouse location. Assorted checks are made to ensure
     * that the new location is legal: it should be adjacent to the current
     * location, and it should be on an open maze cell.
     */
    public void setMouseLocation(int row, int column) {
        Maze.Location proposedLocation = maze.getLocation(row, column);
        if (!proposedLocation.isOpen()) {
            throw new IllegalArgumentException(ratString(row, column) + " is not allowed!");
        }

        if (mouse != null) {
            // Adjacency check.
            String adjacencyExceptionMessage = ratString(row, column) + " is not adjacent!";
            if (column == this.mouse.x) {
                if ((row < this.mouse.y - 1) || (row > this.mouse.y + 1)) {
                    throw new IllegalArgumentException(adjacencyExceptionMessage);
                }
            } else if (row == this.mouse.y) {
                if ((column < this.mouse.x - 1) || (column > this.mouse.x + 1)) {
                    throw new IllegalArgumentException(adjacencyExceptionMessage);
                }
            } else {
                throw new IllegalArgumentException(adjacencyExceptionMessage);
            }
        }

        mouse = new Point(column, row);
    }

    /**
     * Updates the state of the maze. This involves advancing the mouse to the
     * next location then checking whether (a) the mouse has found the cheese,
     * or (b) the cheese has been determined to be unreachable.
     */
    public Result updateMazeState() {
        // Grab the current location, and declare a variable for the next one.
        Maze.Location currentLocation = maze.getLocation(mouse.y, mouse.x);
        Maze.Location nextLocation = null;

        // Walk the maze.
        MazeWalker.WalkerState walkerState = mazeWalker.areWeThereYet(mouse.y, mouse.x);
        switch (walkerState) {
            case THERE_ALREADY:
                return Result.CHEESE_FOUND;

            case IMPOSSIBLE_TO_GET_THERE:
                return Result.CHEESE_NOT_FOUND;

            case MOVE_LEFT:
                nextLocation = currentLocation.getLeft();
                break;

            case MOVE_UP:
                nextLocation = currentLocation.getAbove();
                break;

            case MOVE_RIGHT:
                nextLocation = currentLocation.getRight();
                break;

            case MOVE_DOWN:
                nextLocation = currentLocation.getBelow();
                break;

            default:
                // No-op, just here for completeness.
                break;
        }

        // Set the new location.
        setMouseLocation(nextLocation.getRow(), nextLocation.getColumn());
        return Result.STILL_LOOKING;
    }

    private String ratString(int row, int column) {
        return "Rat location (row " + row + ", column " + column + ")";
    }
}
