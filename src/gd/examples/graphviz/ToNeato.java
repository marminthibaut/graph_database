package gd.examples.graphviz;

import java.util.List;

import gd.app.model.*;

/**
 * 
 * Méthode qui construit une String au langage DOT pour générer le graphe du
 * schéma relationnel.
 * 
 * @author thibaut
 * 
 */
public class ToNeato {

	/**
	 * @param liste
	 * @return Dot string
	 */
	public static String convertToNeato(List<?> liste) {
		String retour = "digraph mon_graphe {\n"
				+ "   graph [bgcolor=white];\n";

		for (Object o : liste) {
			if (o instanceof Table) {

				Table t = (Table) o;

				String table_ident = "T" + t.getName();
				retour += "   "
						+ table_ident
						+ "[shape=box, label=\""
						+ t.getName()
						+ "\", color=black, fontcolor=\"#ffffff\", style=filled];\n";

				for (Column c : t.getColumns()) {
					String column_ident = "C" + t.getName() + c.getName();
					retour += "   "
							+ column_ident
							+ " [shape=none, color=white, fontcolor=black, label=\""
							+ c.getName() + "\\n{" + c.getType()
							+ "}\", fontsize=10];\n";

					Boolean PKEY = false;
					// TODO Gérer FKEY !
					for (Constraint co : c.getRestrictors()) {
						if (co.getType().equals("PRIMARY KEY")) {
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
}
