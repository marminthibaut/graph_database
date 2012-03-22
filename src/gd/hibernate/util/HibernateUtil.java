package gd.hibernate.util;

import java.io.File;
import java.io.FileNotFoundException;
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

	private static String createConfPath(String type) {
		String url = System.getProperty("user.dir") + "/bin/gd/conf/hibernate/"
				+ type.toString().toLowerCase() + "/hibernate.cfg.xml";
		logger.debug("Génération de l'url de configuration pour " + type + "\n"
				+ url);
		return url;
	}

	private static String generateURI(String sgbdtype, String host,
			String database, String port) {
		String uri;
		if (port != null)
			uri = "jdbc:" + sgbdtype + "://" + host + ":" + port + "/"
					+ database;
		else
			uri = "jdbc:" + sgbdtype + "://" + host + "/" + database;

		logger.debug("Génération d'une uri JDBC \n" + uri);
		return uri;
	}

	/**
	 * Ouvre une session sans définition du port
	 * 
	 * @param sgbdtype
	 * @param host
	 * @param database
	 * @param username
	 * @param password
	 * @return nouvelle session
	 * @throws FileNotFoundException
	 * @throws HibernateException
	 */
	public static Session openSession(String sgbdtype, String host,
			String database, String username, String password)
			throws FileNotFoundException, HibernateException {
		return HibernateUtil.openSession(sgbdtype, host, database, username,
				password, null);
	}

	/**
	 * 
	 * Ouvre une session JDBC
	 * 
	 * @param sgbdtype
	 *            Type de sgbd (respectant la norme jdbc)
	 * @param host
	 * @param database
	 * @param username
	 * @param password
	 * @param port
	 * @return Nouvelle session
	 * @throws FileNotFoundException
	 * @throws HibernateException
	 */
	public static Session openSession(String sgbdtype, String host,
			String database, String username, String password, String port)
			throws FileNotFoundException, HibernateException {

		logger.debug("Ouverture d'une session de type " + sgbdtype);

		File f = new File(HibernateUtil.createConfPath(sgbdtype));
		if (!f.exists()) {
			throw new FileNotFoundException("Fichier de configuration du SGBD "
					+ sgbdtype.toString().toLowerCase() + "non trouvé");
		}

		Configuration cfg = new Configuration().configure(f);
		cfg.setProperty("hibernate.connection.url",
				HibernateUtil.generateURI(sgbdtype, host, database, port));

		cfg.setProperty("hibernate.connection.username", username);
		cfg.setProperty("hibernate.connection.password", password);

		logger.debug("Session de type " + sgbdtype + " ouverte.");
		return cfg.buildSessionFactory().openSession();

	}
}