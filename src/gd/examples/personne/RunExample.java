package gd.examples.personne;

import gd.hibernate.util.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.*;

/**
 * Classe d'exemple d'utilisation d'Hibernate et de log4j Requiere la creation
 * de deux tables : Personne(id integer, nom varchar, prenom varchar)
 * Adresse(id_personne integer, numero integer, rue varchar)
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

		logger.debug("Execution de la méthode de gd.examples.personne");

		Session s = HibernateUtil.currentSession(SGBD.POSTGRESQL);

		// addExample(s);
		showExample(s);

		HibernateUtil.closeSession(SGBD.POSTGRESQL);

	}

	/**
	 * Exemple d'ajout d'un utilisateur (Jean-Claude Dusse id=0)
	 * 
	 * @param s
	 */
	public static void addExample(Session s) {
		logger.debug("Méthode addExample");
		Transaction tx = s.beginTransaction();

		Personne personne = new Personne(2);
		personne.setNom("René");
		personne.setPrenom("Dusse");
		personne.setNumero(34);
		personne.setRue("machine");
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
				logger.debug(p);
			}
		}
	}
}
