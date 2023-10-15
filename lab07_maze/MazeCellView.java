import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * MazeCellView provides a visual representation of a Cell.
 */
public class MazeCellView extends JComponent {
    /**
     * Image resource for drawing a maze wall.
     */
    private static BufferedImage WALL = null;

    /**
     * Image resource for drawing a maze wall.
     */
    private static BufferedImage HALL = null;

    /**
     * The cell that this view represents.
     */
    private MazeCell mazeCell;

    /**
     * Creates the cell view, initializing it accordingly.
     */
    public MazeCellView(MazeCell mazeCell) {
        this.mazeCell = mazeCell;
    }

    /**
     * Paints an individual maze cell.
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(Graphics g) {
        // Erase the view's current contents.
        g.clearRect(0, 0, getWidth(), getHeight());

        // If the cell is a wall, fill it in.
        if (mazeCell == MazeCell.WALL) {
            if (WALL != null) {
                g.drawImage(WALL, 0, 0, getWidth(), getHeight(), null);
            } else {
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        } else {
            if (HALL != null) {
                g.drawImage(HALL, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }

    /**
     * Returns the minimum size of the cell view.
     *
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(20, 20);
    }

    /**
     * Returns the preferred size of the cell view.
     *
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(40, 40);
    }

    static {
        try {
            WALL = ImageIO.read(new File("wall.png"));
        } catch (IOException ioe) {
            // Fail quietly: image drawing is not required.
        }

        try {
            HALL = ImageIO.read(new File("hall.png"));
        } catch (IOException ioe) {
            // Fail quietly: image drawing is not required.
        }
    }
}
