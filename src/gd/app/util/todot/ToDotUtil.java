package gd.app.util.todot;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import gd.app.model.*;
import gd.app.util.GraphvizCmd;
import gd.util.ConvertTypeUtilException;

/**
 * 
 * Classe utilitaire de génération de graphes au langage DOT
 * 
 * @author thibaut
 * 
 */
public class ToDotUtil {

    private static final Logger logger = Logger.getLogger(ToDotUtil.class);

    private static DotColor table_border_color;
    private static DotColor table_bg_color;
    private static DotColor table_font_color;

    private static DotColor graph_bg_color;

    private static DotColor constraint_fk_font_color;
    private static DotColor constraint_fk_arrow_color;
    private static DotStyle constraint_fk_arrow_style;

    private static DotColor constraint_arrow_color;
    private static DotStyle constraint_arrow_style;
    private static DotColor constraint_bg_color;
    private static DotColor constraint_border_color;
    private static DotColor constraint_font_color;
    private static DotStyle constraint_style;

    private static DotShape constraint_shape;

    static {
        resetStyle();
    }

    /**
     * Réinitialise la configuration d'origine
     */
    public static void resetStyle() {
        table_bg_color = DotColor.WHITE;
        table_border_color = DotColor.BLACK;
        table_font_color = DotColor.BLACK;

        graph_bg_color = DotColor.WHITE;

        constraint_fk_font_color = DotColor.DARKRED;
        constraint_fk_arrow_color = DotColor.DARKRED;
        constraint_fk_arrow_style = DotStyle.SOLID;

        constraint_arrow_color = DotColor.BLACK;
        constraint_arrow_style = DotStyle.DOTTED;
        constraint_bg_color = DotColor.GOLD;
        constraint_font_color = DotColor.SADDLEBROWN;
        constraint_border_color = DotColor.BLACK;
        constraint_shape = DotShape.NOTE;
        constraint_style = DotStyle.FILLED;
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
     * @throws ToDotUtilException
     * 
     */
    public static String convertToDot(List<?> liste, String dbname)
            throws ToDotUtilException {

        logger.debug("Génération d'un graphe ToNeato");
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
     * @param gv_cmd
     * @param dot_file_path
     * @param img_file_path the output file.
     * @return 0 if system call exited correctly, another value otherwise.
     */
    public static int dotToSvg(GraphvizCmd gv_cmd, String dot_file_path,
            String img_file_path) {
        return dotToImg(gv_cmd, dot_file_path, img_file_path, "svg");
    }

    /**
     * @param gv_cmd
     * @param dot_file_path
     * @param img_file_path the output file.
     * @return 0 if system call exited correctly, another value otherwise.
     */
    public static int dotToPng(GraphvizCmd gv_cmd, String dot_file_path,
            String img_file_path) {
        return dotToImg(gv_cmd, dot_file_path, img_file_path, "png");
    }

    private static int dotToImg(GraphvizCmd gv_cmd, String dot_file_path,
            String img_file_path, String image_type) {
        int return_value = -1;
        String cmd = gv_cmd.toString() + " " + dot_file_path + " -T"
                + image_type + " -o " + img_file_path;
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmd);
            return_value = process.waitFor();
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return return_value;
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
        logger.debug("Génération du Header");
        return "digraph " + dbname + " {\n" + "   rankdir=LR;"
                + "   graph [bgcolor=\"" + graph_bg_color + "\"];"
                + "   node [shape=\"record\"];\n";
    }

    private static String generateTableContent(Table t)
            throws ToDotUtilException {
        String retour = "";

        logger.debug("Génération de la table " + t.getName());
        retour += generateTableIdent(t)
                + "[shape=\"plaintext\", style=filled, color="
                + table_border_color + ", fillcolor=" + table_bg_color
                + ", fontcolor=" + table_font_color + ", label=<"
                + "<table cellspacing=\"0\" border=\"1\" cellborder=\"1\">"
                + "<tr><td cellpadding=\"10\"><b>" + t.getName()
                + "</b></td></tr>";

        for (Column c : t.getColumns()) {
            retour += generateColumnContent(c);
        }

        retour += "</table>\n>];";

        for (Constraint c : t.getConstraints()) {
            retour += generateConstraintContent(c);
        }

        return retour;
    }

    private static String generateColumnContent(Column c)
            throws ToDotUtilException {
        String retour = "";
        Boolean isPK = false;
        try {
            logger.debug("Generation de la colonne " + c.getTable().getName()
                    + "." + c.getName() + " de type " + c.getGenericType()
                    + " (" + c.getType() + ")");

            retour += "\n     <tr><td port=\"" + generateColumnIdent(c)
                    + "\" align=\"left\" cellpadding=\"7\" border=\"0\">";

            isPK = c.isPK();
        } catch (ConvertTypeUtilException e) {
            logger.error("Impossible de déterminer si la colonne "
                    + c.getTable().getName() + "(" + c.getName()
                    + "appartient à une PK", e);
            throw new ToDotUtilException(
                    "Impossible de déterminer si la colonne "
                            + c.getTable().getName() + "(" + c.getName()
                            + ") appartient à une PK", e);
        }

        if (isPK) {
            logger.debug("Colonne appartenant à une PK");
            retour += "[PK]";
        }

        try {
            retour += c.getName() + ": " + c.getGenericType() + "</td></tr>";
        } catch (ConvertTypeUtilException e) {
            throw new ToDotUtilException(
                    "Erreur lors de la génération DOT de la colonne "
                            + c.getName(), e);
        }

        return retour;
    }

    private static String generateConstraintContent(Constraint c)
            throws ToDotUtilException {
        String retour = "";

        try {
            if (c.isFK()) {
                retour += generateConstraintArrow(c);
            } else if (!c.isPK()) {

                logger.debug("Génération de la contrainte " + c.getName()
                        + " de type " + c.getGenericType() + "(" + c.getType()
                        + ")");
                retour += generateConstraintIdent(c) + " [shape="
                        + constraint_shape + ", style=" + constraint_style
                        + ", fillcolor=" + constraint_bg_color + ", color="
                        + constraint_border_color + ", fontcolor="
                        + constraint_font_color + ", label=\""
                        + c.getGenericType() + "\\n" + c.getName() + "\"];";

                retour += generateConstraintArrow(c);
            }
        } catch (ConvertTypeUtilException e) {
            logger.error(
                    "Impossible de dérerminer si la contrainte " + c.getName()
                            + " est de type FK", e);
            throw new ToDotUtilException(
                    "Impossible de déterminer si la contrainte " + c.getName()
                            + " est de type FK", e);
        }
        return retour;
    }

    private static String generateConstraintArrow(Constraint c)
            throws ConvertTypeUtilException {
        String retour = "";

        if (c.isFK()) {
            retour = generateTableIdent(c.getTable()) + ":"
                    + generateColumnIdent(c.getColumn()) + " -> "
                    + generateTableIdent(c.getReferences().getTable()) + ":"
                    + generateColumnIdent(c.getReferences().getColumn())
                    + " [label=\"" + c.getName() + "\", color=\""
                    + constraint_fk_arrow_color + "\", style=\""
                    + constraint_fk_arrow_style + "\", fontcolor=\""
                    + constraint_fk_font_color + "\"];";
        } else {
            retour = generateConstraintIdent(c) + " -> "
                    + generateTableIdent(c.getColumn().getTable()) + ":"
                    + generateColumnIdent(c.getColumn()) + " [style="
                    + constraint_arrow_style + ", color=\""
                    + constraint_arrow_color + "\" arrowsize=0, len=2];";
        }

        return retour;
    }
}
