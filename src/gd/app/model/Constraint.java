package gd.app.model;

import gd.app.model.convertor.ConstraintTypeConvertor;
import gd.hibernate.util.HibernateUtil;

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
	private String truetype;
	private ConstraintType type;
	private Column column;

	// private Constraint references;

	private Set<Column> attributeRestrictorsMirror;

	private Constraint() {
	}

	/**
	 * @return Nom de la contrainte
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Type réel de la contrainte (non générique)
	 */
	public String getTruetype() {
		return truetype;
	}

	/**
	 * @return Type générique de la contrainte
	 */
	public ConstraintType getType() {
		return type;
	}

	/**
	 * @return Colonne propriétaire propriétaire de la contrainte
	 */
	public Column getColumn() {
		return column;
	}

	/**
	 * @return Contrainte référencée par la contrainte de truetype 'FOREIGN_KEY'
	 */
	/*
	 * public Constraint getReferences() { return references; }
	 */

	/**
	 * @return Ensemble des attributs sur lesquels s'applique la contrainte
	 */
	public Set<Column> getAttributeRestrictorsMirror() {
		return attributeRestrictorsMirror;
	}

	/**
	 * @param name
	 *            Nom de contrainte
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param type
	 *            Type de contrainte
	 */
	public void setTruetype(String type) {
		this.truetype = type;
		this.type = ConstraintTypeConvertor.convertFromTruetype(type,
				HibernateUtil.getCurrentSGBD());
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
	 *            Contrainte à référencer (en cas de contrainte de truetype
	 *            'FOREIGN_KEY')
	 */
	/*
	 * public void setReferences(Constraint references) { this.references =
	 * references; }
	 */

	/**
	 * @param attributeRestrictorsMirror
	 *            Liste d'attributs sur lesquels s'applique la contrainte
	 */
	public void setAttributeRestrictorsMirror(
			Set<Column> attributeRestrictorsMirror) {
		this.attributeRestrictorsMirror = attributeRestrictorsMirror;
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
		return "Constraint [" + (name != null ? "name=" + name + ", " : "")
				+ (truetype != null ? "truetype=" + truetype + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				// + (column != null ? "column=" + column + ", " : "")
				// + (references != null ? "references=" + references + ", " :
				// "")
				// + (attributeRestrictorsMirror != null ?
				// "attributeRestrictorsMirror="
				// + attributeRestrictorsMirror
				// : "")
				+ "]";
	};

}
