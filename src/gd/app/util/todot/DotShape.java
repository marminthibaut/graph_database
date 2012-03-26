package gd.app.util.todot;

/**
 * Shapes disponibles dans le langage DOT
 * 
 * @author thibaut
 * @version 0.1
 */
@SuppressWarnings("javadoc")
public enum DotShape {
    BOX,POLYGON,ELLIPSE,OVAL,CIRCLE,POINT,EGG,TRIANGLE,PLAITEXT,DIAMOND,TRAPEZIUM,PARALLELOGRAM,HOUSE,PENTAGON,HEXAGON,SEPTAGON,OCTAGON,DOUBLECIRCLE,DOUBLEOCTAGON,TRIPLEOCTAGON,INVTRIANGLE,INVTRAPEZIUM,INVHOUSE,MDIAMOND,MSQUARE,MCIRCLE,RECT,RECTANGLE,SQUARE,NONE,NOTE,TAB,FOLDER,BOX3D,COMPONENT;
    
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
