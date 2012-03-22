package gd.app.cli;
import java.io.FileNotFoundException;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import gd.app.util.ParamManager;
import gd.hibernate.util.HibernateUtil;



public class CommandLineInterface {


	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args) {
		String username = "", password = "", db_name = "", sgbd_type = "",
				host = "", port = "";

		ParamManager param_manager = new ParamManager(args);
		String arg;

		try {
			while ((arg = param_manager.getNextParam()) != null) {
				if (!ParamManager.isAnOptionName(arg)) {
					throw new Exception("wrong option : "+arg);

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
						host = value;
						break;
					case "username":
						username = value;
						break;
					case "password":
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
						throw new Exception("wrong option : "+arg);
					}

				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		System.out.println(host+"/"+sgbd_type+"/"+db_name+"/"+username+"/"+
				password+"/"+port);

		Session session = null;
		try {
			session = HibernateUtil.openSession(host, sgbd_type, db_name, username,
					password);
		} catch (HibernateException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.close();

	}

}
