package gd.app.model;

import java.util.Set;

/**
 * Représentation d'une Table
 * 
 * @author thibaut
 * @version 0.1
 * 
 */
public class Table {

    private String name;
    private String catalog;

    private Set<Column> columns;
    private Set<Constraint> constraints;

    private Table() {
    }

    /**
     * @return Nom de la table
     */
    public String getName() {
        return name;
    }

    /**
     * @return Nom du Schéma
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * @return Attributs de la table
     */
    public Set<Column> getColumns() {
        return columns;
    }

    /**
     * @return Contraintes affectée à la table
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * @param name
     *            Nom de table
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param catalog
     *            Nom de schéma
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    /**
     * @param columns
     *            Liste d'attributs
     */
    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    /**
     * @param constraints
     *            Liste de contraintes
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
        if (!(obj instanceof Table))
            return false;

        final Table table = (Table) obj;

        if (!table.getName().equals(getName()))
            return true;

        return false;
    }

    @Override
    public String toString() {
        String retour = "Table{";
        retour += "\n    name = " + name;
        retour += "\n    catalog = " + catalog;
        retour += "\n    Columns = [";

        for (Column c : columns) {
            retour += "\n        Column = [";
            retour += "\n            name = " + c.getName();
            retour += "\n            type = " + c.getType();
            if (c.getConstraints() != null)
                for (Constraint co : c.getConstraints()) {
                    retour += "\n            constraints = <Set <Constraint>>"
                            + co.getName();
                }
            retour += "\n        ]";
        }
        retour += "\n    ]";

        retour += "\n    Constraints = [";
        for (Constraint c : constraints) {
            retour += "\n        Constraint = [";
            retour += "\n            name = " + c.getName();
            retour += "\n            type = " + c.getType();
            retour += "\n            column = <Column>"
                    + c.getColumn().getTable().getName() + "." + c.getColumn().getName();
            if (c.getReferences() != null)
                retour += "\n            references = <Constraint>"
                        + c.getReferences().getName();
            retour += "\n            referenced_by = <Set <Constraint>>";
            if (c.getReferenced_by() != null)
                for (Constraint r : c.getReferenced_by()) {
                    retour += r.getName() + " ";
                }
        }
        retour += "\n    ]";

        retour += "}";

        return retour;
    }
}