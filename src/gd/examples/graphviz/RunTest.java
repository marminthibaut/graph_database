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
public class RunTest {
	private static final Logger logger = Logger.getLogger(RunTest.class);

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("Test de la librairie GraphViz");
		Session s = HibernateUtil.currentSession();

		Criteria c = s.createCriteria(Table.class);

		List<?> liste = c.list();
		System.out.println(ToNeato.convertToNeato(liste));

	}

}
