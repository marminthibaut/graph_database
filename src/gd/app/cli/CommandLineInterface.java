package gd.app.cli;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import gd.app.model.Table;
import gd.util.ImageFrame;
import gd.util.ParamManager;
import gd.app.util.ToDotUtil;
import gd.app.util.ToDotUtilException;
import gd.hibernate.util.HibernateUtil;

/**
 * Command Line Interface class, this class contains the main function to
 * compile the command line program.
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @version 0.1
 */
public class CommandLineInterface {

    /**
     * Main
     * 
     * @param args
     *            see the man page.
     */
    public static void main(String[] args) {
        String username = "", password = "", db_name = null, sgbd_type = null;
        String host = "", port = null, output = null;
        boolean opt_show = false;

        ParamManager param_manager = new ParamManager(args);
        String arg;

        try {
            while ((arg = param_manager.getNextParam()) != null) {
                if (!ParamManager.isAnOptionName(arg)) {
                    if (sgbd_type == null) {
                        sgbd_type = ParamManager.getOptionName(arg);
                    } else {
                        db_name = ParamManager.getOptionName(arg);
                    }
                } else {
                    String param = ParamManager.getOptionName(arg);
                    // @todo manage this next line width ParamManager
                    String value = "";
                    System.out.println("-" + param + "-");

                    if (!param.equals("show") && !param.equals("help")) {
                        value = param_manager.getNextParam();
                        if (value == null)
                            throw new Exception("wrong option : " + param);
                        else if (ParamManager.isAnOptionName(value))
                            throw new Exception("wrong value for " + param
                                    + " option");
                    }

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
                        case "output":
                        case "o":
                            output = value;
                            break;
                        case "show":
                            opt_show = true;
                            break;
                        case "help":
                            printHelp();
                            System.exit(0);
                        default:
                            throw new Exception("wrong option : " + arg);
                    }

                }
            }

            if (sgbd_type == null || db_name == null)
                throw new Exception("Bad usage");

        } catch (Exception e) {
            System.err.println(e.getMessage());
            printHelp();
            System.exit(1);
        }

        Session session = null;
        String dot = "";
        String dir = System.getProperty("user.dir") + "/";

        try {
            session = HibernateUtil.openSession(sgbd_type, host, db_name,
                    username, password, port);

            Criteria c = session.createCriteria(Table.class);
            dot = ToDotUtil.convertToNeato(c.list(), db_name);

            if (output == null && !opt_show) {
                System.out.println(dot);
            } else {
                if (opt_show && output == null)
                    output = "output.png";

                // Write a dot file
                String url_dot_file = dir
                        + output.substring(0, output.lastIndexOf('.')) + ".dot";
                BufferedWriter bw = new BufferedWriter(new FileWriter(
                        url_dot_file));
                bw.write(dot);
                bw.close();

                // Generate a png image from the dot file
                String url_image = dir + output;
                // @todo externalise next command
                String neato_cmd = "neato " + url_dot_file + " -Tpng -o "
                        + url_image;
                Process process = Runtime.getRuntime().exec(neato_cmd);
                if (process.waitFor() == 0) {
                    if (opt_show)
                        new ImageFrame(url_image, db_name);
                } else {
                    System.err.println("Command neato not found or fail : "
                            + neato_cmd);
                }
            }

        } catch (IOException | HibernateException
                | InterruptedException | ToDotUtilException e) {
            System.err.println(e.getMessage());
        } finally {
            session.close();
        }

    }

    private static void printHelp() {
        System.out.println("Usage:\n"
                + "gd [OPTION...] SGBD_NAME DATABASE_NAME\n" + "\n"
                + "Options:\n" + "	-u, --user <USERNAME>\n"
                + "		use this username.\n" + "	-p, --password [PASSWORD]\n"
                + "		use this password.\n" + " 	-h, --host <HOST>\n"
                + "		use this host.\n" + "	--port <PORT>\n"
                + "		use this port.\n" + "     -o, --output <FILE_NAME>\n"
                + "             generate an png image width graphviz\n"
                + "     --show \n"
                + "             open a window with graph representation.\n"
                + "	--help\n" + "		print this message.\n" + "\n");
    }

}
