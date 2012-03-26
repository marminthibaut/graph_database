package gd.util;

/**
 * Classe exception personnalisée pour l'utilitaire de conversion des types
 * ConvertTypeUtil
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @version 0.1
 */
public class ConvertTypeUtilException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 6913897661879084780L;

    /**
     * Constructeur par défaut
     */
    public ConvertTypeUtilException() {
        super();
    }

    /**
     * Constructeur avec message personnalisé en paramètre
     * 
     * @param s
     *            Message
     */
    public ConvertTypeUtilException(String s) {
        super(s);
    }

    /**
     * 
     * Constructeur avec message personnalisé et Exception déclancheuse en
     * paramètre
     * 
     * @param s
     *            Message
     * @param e
     *            Exception déclancheuse
     */
    public ConvertTypeUtilException(String s, Exception e) {
        super(s, e);
    }
}
