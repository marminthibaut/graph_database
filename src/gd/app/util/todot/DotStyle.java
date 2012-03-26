package gd.app.util.todot;

/**
 * Styles attribute for DOT language
 * 
 * @author thibaut
 * @version 0.1
 */
public enum DotStyle {
    /**
     * For nodes and edges
     */
    DASHED,
    /**
     * For nodes and edges
     */
    DOTTED,
    /**
     * For nodes and edges
     */
    SOLID,
    /**
     * For nodes and edges
     */
    INVIS,
    /**
     * For nodes and edges
     */
    BOLD,
    /**
     * For edges only
     */
    TAPERED,
    /**
     * For nodes only
     */
    FILLED,
    /**
     * For nodes only
     */
    DIAGONALS,
    /**
     * For nodes only
     */
    ROUNDED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
