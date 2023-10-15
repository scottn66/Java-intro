import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;

/**
 * Specialized grid layout that keeps cells square. Not intended for general use!
 * We implemented just enough for our purposes.
 */
public class SquareGridLayout extends GridLayout {
    public SquareGridLayout(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public void layoutContainer(Container parent) {
        // Adapted from the original, but just tweaked to ensure everything is square.
        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int nrows = getRows();
            int ncols = getColumns();
            int hgap = getHgap();
            int vgap = getVgap();
            int parentWidth = parent.getWidth();
            int parentHeight = parent.getHeight();
            boolean ltr = parent.getComponentOrientation().isLeftToRight();

            if (ncomponents == 0) {
                return;
            }

            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            // 4370316. To position components in the center we should:
            // 1. get an amount of extra space within Container
            // 2. incorporate half of that value to the left/top position
            // Note that we use trancating division for widthOnComponent
            // The reminder goes to extraWidthAvailable
            int totalGapsWidth = (ncols - 1) * hgap;
            int widthWithoutInsets = parentWidth - (insets.left + insets.right);
            int rawWidthOnComponent = (widthWithoutInsets - totalGapsWidth) / ncols;
            int extraWidthAvailable = (widthWithoutInsets - (rawWidthOnComponent * ncols + totalGapsWidth)) / 2;

            int totalGapsHeight = (nrows - 1) * vgap;
            int heightWithoutInsets = parentHeight - (insets.top + insets.bottom);
            int rawHeightOnComponent = (heightWithoutInsets - totalGapsHeight) / nrows;
            int extraHeightAvailable = (heightWithoutInsets - (rawHeightOnComponent * nrows + totalGapsHeight)) / 2;

            int widthOnComponent = Math.min(rawWidthOnComponent, rawHeightOnComponent);
            int heightOnComponent = widthOnComponent;
            if (ltr) {
                for (int c = 0, x = insets.left + extraWidthAvailable; c < ncols; c++, x += widthOnComponent + hgap) {
                    for (int r = 0, y = insets.top + extraHeightAvailable;
                            r < nrows;
                            r++, y += heightOnComponent + vgap) {
                        int i = r * ncols + c;
                        if (i < ncomponents) {
                            parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
                        }
                    }
                }
            } else {
                for (int c = 0, x = (parentWidth - insets.right - widthOnComponent) - extraWidthAvailable;
                        c < ncols;
                        c++, x -= widthOnComponent + hgap) {
                    for (int r = 0, y = insets.top + extraHeightAvailable;
                            r < nrows;
                            r++, y += heightOnComponent + vgap) {
                        int i = r * ncols + c;
                        if (i < ncomponents) {
                            parent.getComponent(i).setBounds(x, y, widthOnComponent, heightOnComponent);
                        }
                    }
                }
            }
        }
    }
}
