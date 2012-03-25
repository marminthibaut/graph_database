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
public class ToDotUtil {

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
        return "digraph " + dbname + " {\n" + "   rankdir=LR;"
                + "   graph [bgcolor=\"" + graph_bg_color + "\"];"
                + "   node [shape=\"record\"];\n";
    }

    private static String generateTableContent(Table t)
            throws ToDotUtilException {
        String retour = "";

        logger.debug("Génération de la table " + t.getName());
        retour += generateTableIdent(t) + "[shape=\"plaintext\", label=<"
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
                retour += generateConstraintFKContent(c);
            } else if (!c.isPK()) {

                logger.debug("Génération de la contrainte " + c.getName()
                        + " de type " + c.getGenericType() + "(" + c.getType()
                        + ")");
                retour += generateConstraintIdent(c)
                        + " [shape=note, style=filled, fillcolor=gold, color=black, fontcolor=saddlebrown, label=\""
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

    private static String generateConstraintFKContent(Constraint c)
            throws ConvertTypeUtilException {

        logger.debug("Génération de la contrainte " + c.getName()
                + " de type FK");
        String retour = "";

        retour += generateConstraintArrow(c) + " [label=\"" + c.getName()
                + "\", len=2];";

        return retour;
    }

    private static String generateConstraintArrow(Constraint c)
            throws ConvertTypeUtilException {
        String retour = "";

        if (c.isFK()) {
            retour = generateTableIdent(c.getTable()) + ":"
                    + generateColumnIdent(c.getColumn()) + " -> "
                    + generateTableIdent(c.getReferences().getTable()) + ":"
                    + generateColumnIdent(c.getReferences().getColumn());
        } else {
            retour = generateConstraintIdent(c) + " -> "
                    + generateTableIdent(c.getColumn().getTable()) + ":"
                    + generateColumnIdent(c.getColumn())
                    + " [style=dotted, arrowsize=0, len=2];";
        }

        return retour;
    }
}
