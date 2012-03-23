package gd.app.model;

import java.util.Set;

/**
 * Représentation d'un attribut d'une table
 * 
 * @author thibaut
 * @version 0.1
 */
public class Column {

    private String name;
    private String type;
    // private float precision;
    // TODO prendre en charge la précision des
    // attributs
    private Table table;

    private Set<Constraint> restrictors;

    private Column() {
    }

    /**
     * @return Nom de l'attribut
     */
    public String getName() {
        return name;
    }

    /**
     * @return Type réel de l'attribut (dépend du SGBD)
     */
    public String getType() {
        return type;
    }

    /**
     * @return Précision du type de l'attribut
     */
    /*
     * public float getPrecision() { return precision; }
     */

    /**
     * @return Table mère de l'attribut
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return Ensemble de contraintes affectées sur l'attribut
     */

    public Set<Constraint> getRestrictors() {
        return restrictors;
    }

    /**
     * @param name
     *            Nom d'attribut
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param type
     *            Type d'attribut
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param precision
     *            Précision de l'attribut
     */
    /*
     * public void setPrecision(float precision) { this.precision = precision; }
     */

    /**
     * @param table
     *            Table propriétaire
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @param restrictors
     *            Ensemble de contraintes
     */

    public void setRestrictors(Set<Constraint> restrictors) {
        this.restrictors = restrictors;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() * 29;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Column))
            return false;

        final Column column = (Column) obj;

        if (!column.getName().equals(getName()))
            return true;

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Column ["
                + (name != null ? "name=" + name + ", " : "")
                + (type != null ? "type=" + type + ", " : "") // +
                                                              // "precision="
                // + precision + ", "
                // + (table != null ? "table=" + table + ", " : "")
                + (restrictors != null ? "restrictors=" + restrictors : "")
                + "]";
    }

}
