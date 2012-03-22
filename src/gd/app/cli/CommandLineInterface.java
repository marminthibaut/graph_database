package gd.app.cli;

import java.io.IOException;
import org.jdom.JDOMException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import gd.app.model.Table;
import gd.app.util.ParamManager;
import gd.examples.graphviz.ToNeato;
import gd.hibernate.util.HibernateUtil;


/**
 * Command Line Interface class, this class contains the main function to compile
 * the command line program.
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @version 0.1
 */
public class CommandLineInterface {

	/**
	 * Main
	 * 
	 * @param args see the man page.
	 */
	public static void main(String[] args){
		String username = "", password = "", db_name = null, sgbd_type = null, host = "", port = null;

		ParamManager param_manager = new ParamManager(args);
		String arg;

		try {
			while ((arg = param_manager.getNextParam()) != null) {
				if (!ParamManager.isAnOptionName(arg)) {
					if(sgbd_type == null){
						sgbd_type = ParamManager.getOptionName(arg);
					}else{
						db_name = ParamManager.getOptionName(arg);
					}
				} else {
					String param = ParamManager.getOptionName(arg);
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
					case "user":
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
						
						break;
					case "port":
						port = value;
						break;
					case "help":
						printHelp();
						System.exit(0);
					default:
						throw new Exception("wrong option : " + arg);
					}

				}
			}
			
			if(sgbd_type == null || db_name == null)
				throw new Exception("Bad usage");
				
		} catch (Exception e) {
			System.err.println(e.getMessage());
			printHelp();
			System.exit(1);
		}

		Session session = null;
		try {
			session = HibernateUtil.openSession(sgbd_type, host, db_name,
					username, password, port);

			Criteria c = session.createCriteria(Table.class);

			String neato = ToNeato.convertToNeato(c.list());
			System.out.println(neato);

		} catch (JDOMException | IOException | HibernateException e) {
			System.err.println(e.getMessage());
		} finally {
			session.close();
		}

	}
	
	private static void printHelp(){
		System.out.println(
				"Usage:\n" +
				"gd [OPTION...] SGBD_NAME DATABASE_NAME\n" +
				"\n" +
				"Options:\n" +
				"	-u, --user <USERNAME>\n" +
				"		use this username.\n" +
				"	-p, --password [PASSWORD]\n" +
				"		use this password.\n" +
				" 	-h, --host <HOST>\n" +
				"		use this host.\n" +
				"	--port <PORT>\n" +
				"		use this port.\n" +
				"	--help\n" +
				"		print this message.\n" +
				"\n"				
				);
	}

}
