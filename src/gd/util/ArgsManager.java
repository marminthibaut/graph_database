package gd.util;

/**
 * Classe de gestion des arguments d'une ligne de commande
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @version 0.1
 * 
 */
public class ArgsManager {

    private String[] args;
    private int cursor;

    /**
     * @param args
     *            the array of arguments to manage
     */
    public ArgsManager(String[] args) {
        this.cursor = -1;
        this.args = args;
    }

    /**
     * @return the next parameter
     */
    public String getNextParam() {
        if (++this.cursor < this.args.length)
            return this.args[this.cursor];

        return null;
    }

    /**
     * @param arg
     * @return true if - or -- is an prefix of arg
     */
    public static boolean isAnOptionName(String arg) {
        return arg.charAt(0) == '-';
    }

    /**
     * @param arg
     * @return the parameter arg without prefix - or --
     */
    public static String getOptionName(String arg) {
        if (arg.charAt(1) == '-')
            return arg.substring(2);
        else if (arg.charAt(0) == '-')
            return arg.substring(1);
        else
            return arg;

    }

}
