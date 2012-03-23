package gd.app.model;

import java.util.Set;

/**
 * Objet de représentation d'une contrainte.
 * 
 * @author thibaut
 * @version 0.1
 * 
 */
public class Constraint {

    private String name;
    private Table table;
    private String type;
    private Column column;

    private Constraint references;
    private Set<Constraint> referenced_by;

    private Constraint() {
    }

    /**
     * @return Nom de la contrainte
     */
    public String getName() {
        return name;
    }

    /**
     * @return Table à laquelle appartient cette contrainte
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return Type réel de la contrainte (non générique)
     */
    public String getType() {
        return type;
    }

    /**
     * @return Colonne propriétaire propriétaire de la contrainte
     */
    public Column getColumn() {
        return column;
    }

    /**
     * @return Contrainte référencée par la contrainte de type 'FOREIGN_KEY'
     */

    public Constraint getReferences() {
        return references;
    }

    /**
     * @return Colonnes concernées par cette contrainte.
     */
    public Set<Column> getConstraintColumns() {
        return null;
    }

    /**
     * @param referenced_by
     *            the referenced_by to set
     */
    public void setReferenced_by(Set<Constraint> referenced_by) {
        this.referenced_by = referenced_by;
    }

    /**
     * @param name
     *            Nom de contrainte
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param table
     * @param type
     *            Type de contrainte
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @param type
     *            Type de contrainte
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param column
     *            Colonne propriétaire
     */
    public void setColumn(Column column) {
        this.column = column;
    }

    /**
     * @param references
     *            Contrainte à référencer (en cas de contrainte de type
     *            'FOREIGN_KEY')
     */

    public void setReferences(Constraint references) {
        this.references = references;
    }

    /**
     * @return the referenced_by
     */
    public Set<Constraint> getReferenced_by() {
        return referenced_by;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() * 29;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Constraint))
            return false;

        final Constraint constraint = (Constraint) obj;

        if (!constraint.getName().equals(getName()))
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "Constraint ["
                + (name != null ? "name=" + name + ", " : "")
                + (type != null ? "type=" + type + ", " : "")
                // + (column != null ? "column=" + column + ", " : "")
                + (references != null ? "references=" + references.getName()
                        + ", " : "")
                // + (columns_restricted != null ?
                // "columns_restricted="
                // + columns_restricted
                // : "")
                + "]";
    };

}
