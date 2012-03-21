package gd.examples.graphviz;

import org.apache.log4j.Logger;
import gd.app.model.*;
import gd.hibernate.util.*;

import java.util.List;
import org.hibernate.*;

/**
 * Classe de test de GraphViz. Compiler la String de retour avec la commande :
 * neato -Tps [fichier entrée] -o [fichier sortie .ps]
 * 
 * @author thibaut
 */
public class RunExample {
	private static final Logger logger = Logger.getLogger(RunExample.class);

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		logger.debug("Execution de la méthode de gd.examples.graphviz");

		Session s = HibernateUtil.currentSession(SGBD.POSTGRESQL);

		Criteria c = s.createCriteria(Table.class);

		List<?> liste = c.list();
		String neato = ToNeato.convertToNeato(liste);
		logger.debug("Graphviz code : \n" + neato);

		HibernateUtil.closeSession();
	}

}
