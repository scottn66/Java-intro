import javax.swing.JPanel;

/**
 * MazeView provides a visual representation of a Maze.
 */
public class MazeView extends JPanel {
    /**
     * Creates the maze view.
     */
    public MazeView(Maze maze) {
        // Build the view from the given maze.
        setLayout(new SquareGridLayout(maze.getMazeHeight(), maze.getMazeWidth()));
        for (int row = 0; row < maze.getMazeHeight(); row++) {
            for (int column = 0; column < maze.getMazeWidth(); column++) {
                add(new MazeCellView(maze.getLocation(row, column).getMazeCell()));
            }
        }
    }
}
