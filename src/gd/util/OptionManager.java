package gd.util;

/**
 * Classe de gestion des arguments d'une ligne de commande
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @version 0.1
 * 
 */
public class OptionManager {

    private String[] options;
    private int cursor;

    /**
     * @param options
     *            the array of arguments to manage
     */
    public OptionManager(String[] options) {
        this.cursor = -1;
        this.options = options;
    }

    /**
     * @return the next parameter
     */
    public String getNextParam() {
        if (++this.cursor < this.options.length)
            return this.options[this.cursor];

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
