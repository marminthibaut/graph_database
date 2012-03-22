/**
 * 
 */
package gd.app.model.convertor;

import gd.app.model.ConstraintType;
import gd.hibernate.util.SGBD;

/**
 * @author thibaut
 * 
 */
public class ConstraintTypeConvertor {

	/**
	 * @param truetype
	 * @param sgbdtype
	 * @return le type de contrainte générique pour le SGBD donné
	 */
	public static ConstraintType convertFromTruetype(String truetype,
			SGBD sgbdtype) {

		ConstraintType c = null;
		switch (sgbdtype) {
		case POSTGRESQL:
			c = ConstraintTypeConvertor
					.convertFromTruetypeToPostgresql(truetype);
			break;
		// TODO implementer les autres convertisseurs
		default:
			break;
		}
		return c;
	}

	private static ConstraintType convertFromTruetypeToPostgresql(
			String truetype) {

		ConstraintType c = ConstraintType.OTHER;

		switch (truetype) {
		case "FOREIGN KEY":
			c = ConstraintType.FK;
			break;
		case "PRIMARY KEY":
			c = ConstraintType.PK;
			break;
		case "CHECK":
			c = ConstraintType.CHECK;
			break;
		default:
			break;
		}

		return c;
	}

}
