/**
 * 
 */
package gd.app.model;

/**
 * @author thibaut
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
