package gd.app.util;

import java.io.IOException;
import java.util.List;

import org.jdom.JDOMException;

import gd.app.model.*;
import gd.util.ConvertTypeUtil;
import gd.util.ConvertTypeUtil.Thing;

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
    private static String graph_bg_color;
    private static String column_bg_color;
    private static String column_font_color;

    static {
        resetConf();
    }

    /**
     * Réinitialise la configuration d'origine
     */
    public static void resetConf() {
        table_bg_color = "black";
        table_font_color = "white";
        graph_bg_color = "white";
        column_bg_color = "white";
        column_font_color = "black";
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
    public static String convertToNeato(List<?> liste, String sgbdtype,
            String dbname) throws IOException, JDOMException {
        String retour = "digraph " + dbname + " {\n" + "   graph [bgcolor=\""
                + graph_bg_color + "\"];\n";

        for (Object o : liste) {
            if (o instanceof Table) {

                Table t = (Table) o;

                String table_ident = "T" + t.getName();
                retour += "   " + table_ident + "[shape=box, label=\""
                        + t.getName() + "\", color=\"" + table_bg_color
                        + "\", fontcolor=\"" + table_font_color
                        + "\", style=filled];\n";

                for (Column c : t.getColumns()) {
                    String column_ident = "C" + t.getName() + c.getName();
                    retour += "   " + column_ident + " [shape=none, color=\""
                            + column_bg_color + "\", fontcolor=\""
                            + column_font_color + "\", label=\"" + c.getName()
                            + "\\n{" + c.getType() + "}\", fontsize=10];\n";

                    Boolean PKEY = false;
                    // TODO Gérer FKEY !
                    for (Constraint co : c.getRestrictors()) {
                        if (ConvertTypeUtil.convert(sgbdtype, co.getType(),
                                Thing.CONSTRAINT).equals("PRIMARY KEY")) {
                            PKEY = true;
                            retour += "   " + column_ident + " -> "
                                    + table_ident
                                    + " [arrowhead=\"ediamond\", len=1.2];\n";
                        }
                    }

                    if (!PKEY)
                        retour += "   " + column_ident + " -> " + table_ident
                                + " [arrowsize=0, len=1.2];\n";
                }
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
}
