package gd.example;

import gd.util.*;

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

	// private static final Logger logger = Logger.getLogger(RunTest.class);
	private static final Logger logger = Logger.getLogger("gd.example");

	/**
	 * @param args
	 * @throws HibernateException
	 */
	public static void main(String[] args) throws HibernateException {

		logger.debug("Execution de la méthode de gd.example");

		Session s = HibernateUtil.currentSession();

		// addExample(s);
		showExample(s);

		HibernateUtil.closeSession();

	}

	/**
	 * Exemple d'ajout d'un utilisateur (Jean-Claude Dusse id=0)
	 * 
	 * @param s
	 */
	public static void addExample(Session s) {
		logger.debug("Méthode addExample");
		Transaction tx = s.beginTransaction();

		Personne personne = new Personne();
		personne.setNom("Jean-Claude");
		personne.setPrenom("Dusse");
		logger.debug("Méthode addExample -> save()");
		s.save(personne);

		logger.debug("Méthode addExample --> commit()");
		tx.commit();
	}

	/**
	 * Exemple d'affichage des Personnes
	 * 
	 * @param s
	 */
	public static void showExample(Session s) {
		logger.debug("Méthode showExample");
		Query q = s.createQuery("from Personne");

		List<?> list = q.list();

		logger.debug("Méthode showExample --> affichage");
		for (Object p : list) {
			if (p instanceof Personne) {
				System.out.println(p);
			}
		}
	}
}
