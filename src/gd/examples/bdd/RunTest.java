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
public class RunTest {

	private static final Logger logger = Logger.getLogger(RunTest.class);

	/**
	 * @param args
	 * @throws HibernateException
	 */
	public static void main(String[] args) throws HibernateException {

		logger.debug("Execution de la méthode de gd.examples.bdd");

		Session s = HibernateUtil.currentSession();

		// addExample(s);
		showExample(s);

		HibernateUtil.closeSession();

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
				System.out.println(p);
			}
		}

	}
}
