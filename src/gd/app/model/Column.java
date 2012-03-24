package gd.app.model;

import gd.util.ConvertTypeUtil;
import gd.util.ConvertTypeUtil.Thing;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import org.jdom.JDOMException;

/**
 * Représentation d'un attribut d'une table
 * 
 * @author thibaut
 * @version 0.1
 */
public class Column implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2783675353811975370L;
    private String name;
    private String type;
    // private float precision;
    // TODO prendre en charge la précision des
    // attributs
    private Table table;
    private Set<Constraint> constraints;

    /**
     * 
     */
    public Column() {
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
     * @return Contraintes de la colonne
     */
    public Set<Constraint> getConstraints() {
        return constraints;
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
     * @param constraints
     *            Table propriétaire
     */
    public void setConstraints(Set<Constraint> constraints) {
        this.constraints = constraints;
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

        if (!column.getName().equals(getName())
                || !column.getTable().getName().equals(table.getName()))
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
        return "Column [" + (name != null ? "name=" + name + ", " : "")
                + (type != null ? "type=" + type + ", " : "") // +
                                                              // "precision="
                // + precision + ", "
                // + (table != null ? "table=" + table + ", " : "")
                + "]";
    }

    /**
     * @return Retourne vrai si la column est contrainte par une PK, faux sinon
     */
    public boolean isPK() {
        // TODO Gérer les PK
        return false;
    }

    /**
     * @return Type générique de la colonne, obtenue grâce à l'utilitaire de
     *         conversion (ConvertTypeUtil)
     * @throws IOException
     *             En cas d'erreur de lecteur du fichier XML de conversion
     * @throws JDOMException
     *             En cas d'erreur de parsing du fichier XML de conversion
     */
    public String getGenericType() throws IOException, JDOMException {
        return ConvertTypeUtil.convert(table.getSgbdtype(), type, Thing.COLUMN);
    }

}
