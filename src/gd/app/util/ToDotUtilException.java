package gd.app.util;

/**
 * Classe Exception personnalisée pour ToDotUtilOld
 * 
 * @author thibaut
 * @version 0.1
 */
public class ToDotUtilException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2933926376718682581L;

    /**
     * Constructeur sans paramètre
     */
    public ToDotUtilException() {
        super();
    }

    /**
     * Constructeur avec message en paramètre
     * 
     * @param s
     *            Message
     */
    public ToDotUtilException(String s) {
        super(s);
    }

    /**
     * 
     * Constructeur avec message et exception déclancheuse en paramètre
     * 
     * @param s
     *            Message
     * @param e
     *            Excpetion déclancheuse
     */
    public ToDotUtilException(String s, Exception e) {
        super(s, e);
    }
}
