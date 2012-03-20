package gd.hibernate.util;

import gd.examples.personne.RunExample;

import java.net.URL;
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
	
	private static final Logger logger = Logger.getLogger(RunExample.class);

	private static final Map<SGBD, SessionFactory> sessionFactory = new HashMap<SGBD, SessionFactory>();

	static {
		try {
			URL urlPostgresql = HibernateUtil.class
					.getResource("/gd/hibernate/conf/postgresql/hibernate.cfg.xml");
			URL urlMysql = HibernateUtil.class
					.getResource("/gd/hibernate/conf/mysql/hibernate.cfg.xml");
			URL urlOracle = HibernateUtil.class
					.getResource("/gd/hibernate/conf/oracle/hibernate.cfg.xml");
			
			logger.debug("Chargement du fichier de configuration Postgresql...\n"+urlPostgresql.toString());
			
			sessionFactory.put(SGBD.POSTGRESQL, new Configuration()
			.configure(urlPostgresql).buildSessionFactory());
			
			logger.debug("Chargement du fichier de configuration Mysql...\n"+urlMysql.toString());
			
			sessionFactory.put(SGBD.MYSQL, new Configuration()
			.configure(urlMysql).buildSessionFactory());
			
			logger.debug("Chargement du fichier de configuration Oracle...\n"+urlOracle.toString());
			
			sessionFactory.put(SGBD.ORACLE, new Configuration()
			.configure(urlOracle).buildSessionFactory());
			
			logger.debug("Chargement des configurations hibernate terminé");
			
			
			
		} catch (HibernateException ex) {
			logger.fatal("Erreur lors du chargement des fichiers de configuration Hibernate :\n"+ex.getMessage());
			throw new RuntimeException("Problème de configuration : "
					+ ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("javadoc")
	public static final ThreadLocal<Session> sessionPostgresql = new ThreadLocal<Session>();
	@SuppressWarnings("javadoc")
	public static final ThreadLocal<Session> sessionOracle = new ThreadLocal<Session>();
	@SuppressWarnings("javadoc")
	public static final ThreadLocal<Session> sessionMysql = new ThreadLocal<Session>();

	/**
	 * @param type Type de SGBD (Enum SGBD)
	 * @return Current Session
	 * @throws Exception
	 */
	public static Session currentSession(SGBD type) throws Exception {
		Session s = (Session) getThreadLocalSession(type).get();

		if (s == null) {
			logger.debug("session de type "+type.toString()+" absent, creation de la session.");
			s = sessionFactory.get(type).openSession();
			getThreadLocalSession(type).set(s);
		}

		return s;
	}

	/**
	 * @param type Type du SGBD (Enum SGBD)
	 * @throws Exception 
	 */
	public static void closeSession(SGBD type) throws Exception {
		logger.debug("fermeture de la session "+type.toString());
		Session s = (Session) getThreadLocalSession(type).get();
		getThreadLocalSession(type).set(null);
		if (s != null)
			s.close();
	}

	private static ThreadLocal<Session> getThreadLocalSession(SGBD type)
			throws Exception {
		if (type.equals(SGBD.POSTGRESQL))
			return sessionPostgresql;
		else if (type.equals(SGBD.MYSQL))
			return sessionMysql;
		else if (type.equals(SGBD.ORACLE))
			return sessionOracle;

		throw new Exception("SGBD not implemented");
	}
}