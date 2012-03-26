/**
 * 
 */
package gd.app.model;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @version 0.1
 * 
 */
public enum ColumnType {
    /**
     * Type chaine de caractère
     */
    VARCHAR,
    /**
     * Type caractère
     */
    CHAR,
    /**
     * Type entier
     */
    INTEGER,
    /**
     * Type flottant
     */
    FLOAT,
    /**
     * Type serial
     */
    SERIAL,
    /**
     * Type booleen
     */
    BOOLEAN,
    /**
     * Type date
     */
    DATE,
    /**
     * Type binaire
     */
    BINARY,
    /**
     * Type chaine binaire
     */
    BINARYSTRING,
    /**
     * Type identifieur
     */
    IDENTIFIER,
    /**
     * Type tableau
     */
    ARRAY,
    /**
     * Type géométrique (point, ligne, ...)
     */
    GEOMETRIC,
    /**
     * Type adresse réseau
     */
    NETWORK,
    /**
     * Autre type
     */
    OTHER
}
