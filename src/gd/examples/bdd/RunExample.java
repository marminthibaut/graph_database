package gd.examples.bdd;

import gd.app.model.Table;
import gd.hibernate.util.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.*;

/**
 * Classe d'exemple d'utilisation d'Hibernate et de log4j
 * 
 * @author thibaut
 * 
 */
public class RunExample {

	private static final Logger logger = Logger.getLogger(RunExample.class);

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		logger.debug("Execution de la méthode de gd.examples.bdd");

		Session s = HibernateUtil.openSession("postgresql", "localhost",
				"test", "test", "test");

		// addExample(s);
		showExample(s);

		s.close();

	}

	/**
	 * Exemple d'affichage de la BDD
	 * 
	 * @param s
	 */
	public static void showExample(Session s) {
		logger.debug("Méthode showExample");
		Criteria q = s.createCriteria(Table.class);

		List<?> list = q.list();

		logger.debug("Méthode showExample --> affichage");

		for (Object p : list) {
			if (p instanceof Table) {
				logger.debug(p);
			}
		}

	}
}
