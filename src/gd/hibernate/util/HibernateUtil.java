package gd.hibernate.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(HibernateUtil.class);
	private static SessionFactory sessionFactory = null;
	private static SGBD currentSGBD = null;
	private static final Map<SGBD, String> confsUrls = new HashMap<SGBD, String>();

	static {
		for (SGBD type : SGBD.values()) {
			String url = System.getProperty("user.dir")
					+ "/bin/gd/hibernate/conf/" + type.toString().toLowerCase()
					+ "/hibernate.cfg.xml";
			logger.debug("Chargement du fichier de configuration "
					+ type.toString().toLowerCase());
			File f = new File(url);
			if (f.canRead() == true) {
				logger.debug("Fichier de configuration trouvé : " + url);
				confsUrls.put(type, url);
			} else {
				logger.warn("Fichier de configuration "
						+ type.toString().toLowerCase() + " manquant.\n" + url);
			}
		}
	}

	private static void configureSessionFactory(SGBD type) {
		try {
			if (currentSGBD == null)
				logger.debug("Première configuration du sessionFactory en "
						+ type.toString().toLowerCase());
			else
				logger.debug("Re-configuration du sessionFactory de type "
						+ currentSGBD.toString().toLowerCase() + " en "
						+ type.toString().toLowerCase());

			HibernateUtil.closeSession();

			String url = confsUrls.get(type);

			if (url == null)
				throw new FileNotFoundException("Type de SGBD "
						+ type.toString().toLowerCase() + " non disponible");

			File f = new File(url);
			if (!f.exists())
				throw new FileNotFoundException(
						"Fichier de configuration du SGBD "
								+ type.toString().toLowerCase()
								+ "non disponible");

			sessionFactory = new Configuration().configure(f)
					.buildSessionFactory();
			currentSGBD = type;

		} catch (HibernateException ex) {
			logger.warn("Problème de configuration : " + ex.getMessage());
			throw new RuntimeException("Problème de configuration : "
					+ ex.getMessage(), ex);
		} catch (FileNotFoundException ex) {
			logger.warn("Problème de configuration : " + ex.getMessage());
			throw new RuntimeException("Problème de configuration : "
					+ ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("javadoc")
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();

	/**
	 * @param type
	 * @return Current Session
	 * @throws HibernateException
	 */
	public static Session currentSession(SGBD type) throws HibernateException {
		logger.debug("Demande de session de type "
				+ type.toString().toLowerCase());
		Session s = (Session) session.get();
		// Ouvre une nouvelle Session, si ce Thread n'en a aucune ou si elle
		// n'est pas du bon type.
		if (s == null || !currentSGBD.equals(type)) {
			logger.debug("Nécéssite une configuration du sessionFactory");

			configureSessionFactory(type);

			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	/**
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		logger.debug("Fermeture de la session");
		currentSGBD = null;
		if (session != null) {
			Session s = (Session) session.get();
			session.set(null);
			if (s != null)
				s.close();
		}
	}

	/**
	 * Retourne le type de SGBD courant.
	 * 
	 * @return SGBD enum
	 */
	public static SGBD getCurrentSGBD() {
		return currentSGBD;
	}
}