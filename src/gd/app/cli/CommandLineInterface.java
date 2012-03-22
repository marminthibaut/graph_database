package gd.app.cli;

import java.io.FileNotFoundException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import gd.app.model.Table;
import gd.app.util.ParamManager;
import gd.examples.graphviz.ToNeato;
import gd.hibernate.util.HibernateUtil;

public class CommandLineInterface {

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String username = "", password = "", db_name = "", sgbd_type = "", host = "", port = null;

		ParamManager param_manager = new ParamManager(args);
		String arg;

		try {
			while ((arg = param_manager.getNextParam()) != null) {
				if (!ParamManager.isAnOptionName(arg)) {
					throw new Exception("wrong option : " + arg);

				} else {
					String param = ParamManager.getOptionName(arg);
					System.out.println(param);
					String value = param_manager.getNextParam();
					if (value == null)
						throw new Exception("wrong option");
					else if (ParamManager.isAnOptionName(value))
						throw new Exception("wrong value for " + param
								+ " option");

					switch (param) {
					case "host":
					case "h":
						host = value;
						break;
					case "username":
					case "u":
						username = value;
						break;
					case "password":
					case "p":
						password = value;
						break;
					case "dbname":
						db_name = value;
						break;
					case "sgbd_type":
						sgbd_type = value;
						break;
					case "port":
						port = value;
						break;
					default:
						throw new Exception("wrong option : " + arg);
					}

				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		Session session = null;
		try {
			session = HibernateUtil.openSession(sgbd_type, host, db_name,
					username, password, port);

			Criteria c = session.createCriteria(Table.class);

			String neato = ToNeato.convertToNeato(c.list());
			System.out.println(neato);

		} catch (HibernateException | FileNotFoundException e) {
			System.err.println(e.getMessage());
		} finally {
			session.close();
		}

	}

}
