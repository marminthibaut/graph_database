package gd.app.util;

import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;

import gd.app.model.*;

/**
 * 
 * Classe utilitaire de génération de graphes au langage DOT
 * 
 * @author thibaut
 * 
 */
public class ToDotUtil {

    private static String table_bg_color;
    private static String table_font_color;
    private static String table_shape;
    private static String graph_bg_color;
    private static String column_bg_color;
    private static String column_font_color;
    private static String column_shape;
    private static String constraint_fk_font_color;
    private static String constraint_bg_color;
    private static String constraint_font_color;
    private static String constraint_shape;

    static {
        resetConf();
    }

    /**
     * Réinitialise la configuration d'origine
     */
    public static void resetConf() {
        table_bg_color = "black";
        table_font_color = "white";
        table_shape = "box";
        graph_bg_color = "white";
        column_bg_color = "none";
        column_font_color = "black";
        column_shape = "none";

        constraint_fk_font_color = "red";
        constraint_bg_color = "gray";
        constraint_font_color = "blue";
        constraint_shape = "none";
    }

    /**
     * 
     * Méthode qui construit une String au langage DOT pour générer le graphe du
     * schéma relationnel.
     * 
     * @param liste
     * @param sgbdtype
     *            Type de sgbd (postgresql, oracle, mysql)
     * @param dbname
     *            Nom de la base de données
     * @return Dot string Graphe au langage DOT valide
     * 
     * @throws JDOMException
     *             Lors de l'erreur de parsing du fichier de translate des types
     *             génériques
     * @throws IOException
     *             Lors de l'erreur d'ouverture du fichier de translate des
     *             types génériques
     */
    public static String convertToNeato(List<?> liste, String dbname)
            throws IOException, JDOMException {
        String retour = generateHeader(dbname);

        for (Object o : liste) {
            if (o instanceof Table) {

                Table t = (Table) o;

                retour += generateTableContent(t);
            }
        }

        retour += "}";
        return retour;
    }

    /**
     * @return the table_bg_color
     */
    public static String getTable_bg_color() {
        return table_bg_color;
    }

    /**
     * @return the table_font_color
     */
    public static String getTable_font_color() {
        return table_font_color;
    }

    /**
     * @return the graph_bg_color
     */
    public static String getGraph_bg_color() {
        return graph_bg_color;
    }

    /**
     * @return the column_bg_color
     */
    public static String getColumn_bg_color() {
        return column_bg_color;
    }

    /**
     * @return the column_font_color
     */
    public static String getColumn_font_color() {
        return column_font_color;
    }

    /**
     * @param table_bg_color
     *            the table_bg_color to set
     */
    public static void setTable_bg_color(String table_bg_color) {
        ToDotUtil.table_bg_color = table_bg_color;
    }

    /**
     * @param table_font_color
     *            the table_font_color to set
     */
    public static void setTable_font_color(String table_font_color) {
        ToDotUtil.table_font_color = table_font_color;
    }

    /**
     * @param graph_bg_color
     *            the graph_bg_color to set
     */
    public static void setGraph_bg_color(String graph_bg_color) {
        ToDotUtil.graph_bg_color = graph_bg_color;
    }

    /**
     * @param column_bg_color
     *            the column_bg_color to set
     */
    public static void setColumn_bg_color(String column_bg_color) {
        ToDotUtil.column_bg_color = column_bg_color;
    }

    /**
     * @param column_font_color
     *            the column_font_color to set
     */
    public static void setColumn_font_color(String column_font_color) {
        ToDotUtil.column_font_color = column_font_color;
    }

    private static String generateTableIdent(Table t) {
        return "T" + t.getName();
    }

    private static String generateColumnIdent(Column c) {
        return "C" + c.getTable().getName() + c.getName();
    }

    private static String generateConstraintIdent(Constraint c) {
        return "W" + c.getTable().getName() + c.getName();
    }

    private static String generateHeader(String dbname) {
        return "digraph " + dbname + " {\n" + "   graph [bgcolor=\""
                + graph_bg_color + "\"];\n";
    }

    private static String generateTableContent(Table t) throws IOException,
            JDOMException {
        String retour = "";

        retour += "   " + generateTableIdent(t) + "[shape=" + table_shape
                + ", label=\"" + t.getName() + "\", color=\"" + table_bg_color
                + "\", fontcolor=\"" + table_font_color
                + "\", style=filled];\n";

        for (Column c : t.getColumns()) {
            retour += generateColumnContent(c);
        }

        for (Constraint c : t.getConstraints()) {
            retour += generateConstraintContent(c);
        }

        return retour;
    }

    private static String generateColumnContent(Column c) {
        String retour = "";
        Table t = c.getTable();

        String column_ident = generateColumnIdent(c);
        String table_ident = generateTableIdent(t);

        retour += "   " + column_ident + " [shape=" + column_shape
                + ", color=\"" + column_bg_color + "\", fontcolor=\""
                + column_font_color + "\", label=\"" + c.getName() + "\\n{"
                + c.getType() + "}\", fontsize=10];\n";

        if (c.isPK())
            retour += "   " + column_ident + " -> " + table_ident
                    + " [arrowhead=\"ediamond\", len=1.2];\n";

        else
            retour += "   " + column_ident + " -> " + table_ident
                    + " [arrowsize=0, len=1.2];\n";

        return retour;
    }

    private static String generateConstraintContent(Constraint c)
            throws IOException, JDOMException {
        String retour = "";
        if (c.isFK()) {
            retour += generateConstraintFKContent(c);
        } else {
            retour += generateConstraintIdent(c) + "[shape=" + constraint_shape
                    + ", label=\"" + c.getName() + "\\n" + c.getType()
                    + "\" color=\"" + constraint_font_color + "\"]\n";

            retour += generateConstraintIdent(c) + " -> "
                    + generateColumnIdent(c.getColumn()) + "[color=\""
                    + constraint_bg_color + "\" len=1.8]\n";
        }
        return retour;
    }

    private static String generateConstraintFKContent(Constraint c) {
        String retour = "";
        
        retour += generateColumnIdent(c.getColumn()) + " -> "
                + generateColumnIdent(c.getReferences().getColumn())
                + "[label=\"" + c.getName() + "\\n" + c.getType()
                + "\" color=\"" + constraint_fk_font_color + "\" len=1.8]\n";

        return retour;
    }
}
