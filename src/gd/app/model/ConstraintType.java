/**
 * 
 */
package gd.app.model;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @version 0.1
 * 
 */
public enum ConstraintType {
    /**
     * Contrainte de clé primaire
     */
    PK,
    /**
     * Contrainte de clé étrangère
     */
    FK,
    /**
     * Contrainte de type check
     */
    CHECK,
    /**
     * Autre type de contrainte
     */
    OTHER
}
