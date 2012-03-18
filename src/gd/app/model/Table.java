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
	private String schema;

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
	public String getSchema() {
		return schema;
	}

	/**
	 * @return Attributs de la table
	 */
	public Set<Column> getAttributes() {
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
	 * @param schema
	 *            Nom de schéma
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @param columns
	 *            Liste d'attributs
	 */
	public void setAttributes(Set<Column> columns) {
		this.columns = columns;
	}

	/**
	 * @param constraints
	 *            Liste de contraintes
	 */
	public void setConstraints(Set<Constraint> constraints) {
		this.constraints = constraints;
	};

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
		return "Table [" + (name != null ? "name=" + name + ", " : "")
				+ (schema != null ? "schema=" + schema + ", " : "")
				+ (columns != null ? "columns=" + columns + ", " : "")
				+ (constraints != null ? "constraints=" + constraints : "")
				+ "]";
	}

}