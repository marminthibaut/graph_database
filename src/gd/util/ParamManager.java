package gd.util;

/**
 * @author Clément Sipieter <csipieter@gmail.com>
 * 
 */
public class ParamManager {

    private String[] args;
    private int cursor;

    /**
     * @param args
     *            the array of arguments to manage
     */
    public ParamManager(String[] args) {
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
