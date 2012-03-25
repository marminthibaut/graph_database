package gd.app.util;

import java.util.List;

import org.apache.log4j.Logger;
import gd.app.model.*;
import gd.util.ConvertTypeUtilException;

/**
 * 
 * Classe utilitaire de génération de graphes au langage DOT
 * 
 * @author thibaut
 * 
 */

@Deprecated
public class ToDotUtilOld {

    private static final Logger logger = Logger.getLogger(ToDotUtilOld.class);

    private static DotColors table_bg_color;
    private static DotColors table_font_color;
    private static DotShapes table_shape;
    private static DotColors graph_bg_color;
    private static DotColors column_bg_color;
    private static DotColors column_font_color;
    private static DotShapes column_shape;

    private static DotColors constraint_fk_font_color;
    private static DotColors constraint_fk_arrow_color;

    private static DotColors constraint_arrow_color;
    private static DotColors constraint_bg_color;
    private static DotColors constraint_font_color;

    private static DotShapes constraint_shape;
    private static Double space_scale;
    private static Double font_size_scale;

    static {
        resetConf();
    }

    /**
     * Réinitialise la configuration d'origine
     */
    public static void resetConf() {
        table_bg_color = DotColors.black;
        table_font_color = DotColors.white;
        table_shape = DotShapes.box;

        graph_bg_color = DotColors.white;

        column_bg_color = DotColors.white;
        column_font_color = DotColors.black;
        column_shape = DotShapes.box;

        constraint_fk_font_color = DotColors.darkred;
        constraint_fk_arrow_color = DotColors.red;

        constraint_arrow_color = DotColors.gray;
        constraint_bg_color = DotColors.gray;
        constraint_font_color = DotColors.white;
        constraint_shape = DotShapes.box;

        space_scale = 1.0;
        font_size_scale = 1.0;
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
        return "digraph " + dbname + " {\n" + "   graph [bgcolor=\""
                + graph_bg_color + "\"];\n";
    }

    private static String generateTableContent(Table t)
            throws ToDotUtilException {
        String retour = "";

        logger.debug("Génération de la table " + t.getName());
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

    private static String generateColumnContent(Column c)
            throws ToDotUtilException {
        String retour = "";
        Table t = c.getTable();

        String column_ident = generateColumnIdent(c);
        String table_ident = generateTableIdent(t);

        logger.debug("Génération de la colonne " + c.getTable().getName() + "("
                + c.getName() + ")");

        retour += "   " + column_ident + " [shape=" + column_shape
                + ", color=\"" + column_bg_color + "\", fontcolor=\""
                + column_font_color + "\", label=\"" + c.getName() + "\\n{"
                + c.getType() + "}\", fontsize=" + (font_size_scale * 10)
                + "];\n";

        Boolean isPK = false;
        try {
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
            retour += "   " + column_ident + " -> " + table_ident
                    + " [arrowhead=\"ediamond\", len=" + (space_scale * 1.2)
                    + "];\n";
        } else {
            logger.debug("Colonne n'appartenant pas à une PK");
            retour += "   " + column_ident + " -> " + table_ident
                    + " [arrowsize=0, len=" + (space_scale * 1.2) + "];\n";
        }

        return retour;
    }

    private static String generateConstraintContent(Constraint c)
            throws ToDotUtilException {
        String retour = "";

        Boolean isFK = false;
        try {
            isFK = c.isFK();
        } catch (ConvertTypeUtilException e) {
            logger.error(
                    "Impossible de dérerminer si la contrainte " + c.getName()
                            + " est de type FK", e);
            throw new ToDotUtilException(
                    "Impossible de déterminer si la contrainte " + c.getName()
                            + " est de type FK", e);
        }

        if (isFK) {
            retour += generateConstraintFKContent(c);
        } else {

            logger.debug("Génération de la contrainte " + c.getName());
            retour += generateConstraintIdent(c) + "[shape=" + constraint_shape
                    + ", label=\"" + c.getName() + "\\n" + c.getType()
                    + "\", style=filled, color=\"" + constraint_bg_color
                    + "\", fontsize=" + (font_size_scale * 10)
                    + ", fontcolor=\"" + constraint_font_color + "\"]\n";

            retour += generateConstraintIdent(c) + " -> "
                    + generateColumnIdent(c.getColumn()) + "[color=\""
                    + constraint_arrow_color + "\", len=" + (space_scale * 1.8)
                    + "]\n";
        }
        return retour;
    }

    private static String generateConstraintFKContent(Constraint c) {

        logger.debug("Génération de la contrainte " + c.getName()
                + " de type FK");
        String retour = "";

        retour += generateColumnIdent(c.getColumn()) + " -> "
                + generateColumnIdent(c.getReferences().getColumn())
                + "[label=\"" + c.getName() + "\\n" + c.getType()
                + "\", fontcolor=\"" + constraint_fk_font_color + "\" len="
                + (space_scale * 1.8) + ", fontsize=" + (font_size_scale * 10)
                + ", color=\"" + constraint_fk_arrow_color + "\"]\n";

        return retour;
    }
}
