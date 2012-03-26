package gd.app.model;

import gd.util.ConvertTypeUtil;
import gd.util.ConvertTypeUtil.Thing;
import gd.util.ConvertTypeUtilException;

import java.io.Serializable;
import java.util.Set;

/**
 * Objet de représentation d'une contrainte.
 * 
 * @author thibaut
 * @version 0.1
 * 
 */
public class Constraint implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4031745260635303864L;
    private String name;
    private Table table;
    private String type;
    private String check_clause;
    private Column column;

    private Constraint references;
    private Set<Constraint> referenced_by;

    /**
     * 
     */
    public Constraint() {
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
     * @return Clause de la contrainte de type check
     */
    public String getCheck_clause() {
        return check_clause;
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
     * @param check_clause
     *            Clause de la contrainte
     */
    public void setCheck_clause(String check_clause) {
        this.check_clause = check_clause;
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

        if (!constraint.getName().equals(getName())
                || !constraint.getColumn().equals(getColumn()))
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
    }

    /**
     * @return Vrai si la contrainte est de type FK, faux sinon
     * @throws ConvertTypeUtilException
     */
    public boolean isFK() throws ConvertTypeUtilException {
        return getGenericType().equals("FK");
    };

    /**
     * @return Vrai si la contrainte est de type PK, faux sinon
     * @throws ConvertTypeUtilException
     */
    public boolean isPK() throws ConvertTypeUtilException {
        return getGenericType().equals("PK");
    };

    /**
     * @return Vrai si la contrainte est de type CHECK, faux sinon
     * @throws ConvertTypeUtilException
     */
    public boolean isCheck() throws ConvertTypeUtilException {
        return getGenericType().equals("CHECK");
    };

    /**
     * @return Type générique de la contrainte, obtenue grâce à l'utilitaire de
     *         conversion (ConvertTypeUtil)
     * @throws ConvertTypeUtilException
     */
    public String getGenericType() throws ConvertTypeUtilException {
        return ConvertTypeUtil.convert(table.getSgbdtype(), type,
                Thing.CONSTRAINT);
    }

}
