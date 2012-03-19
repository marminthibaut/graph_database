package gd.hibernate.util;

import java.net.URL;

import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * Classe utilitaire d'Hibernate. Cette classe charge le fichier de
 * configuration Hibernate et initialise de singleton SessionFactory. Elle
 * permet également de récupérer la sessions courrante, ou de fermer la session
 * courante.
 * 
 * @author thibaut
 * @version 0.1
 * 
 */
public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			// URL du fichier de configuration Hibernate
			URL url = HibernateUtil.class.getResource("PSQL.hibernate.cfg.xml");
			// Crée la SessionFactory
			sessionFactory = new Configuration().configure(url)
					.buildSessionFactory();
		} catch (HibernateException ex) {
			throw new RuntimeException("Problème de configuration : "
					+ ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("javadoc")
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	/**
	 * @return Current Session
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		Session s = (Session) session.get();
		// Ouvre une nouvelle Session, si ce Thread n'en a aucune
		if (s == null) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	/**
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null)
			s.close();
	}
}