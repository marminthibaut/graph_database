/**
 * 
 */
package gd.app.model.convertor;

import gd.app.model.ColumnType;
import gd.hibernate.util.SGBD;

/**
 * @author thibaut version 0.1
 */
public class ColumnTypeConvertor {

	/**
	 * @param truetype
	 * @param sgbdtype
	 * @return le type de colonne générique pour le SGBD donné
	 */
	public static ColumnType concertFromTruetype(String truetype, SGBD sgbdtype) {

		ColumnType c = null;
		switch (sgbdtype) {
		case POSTGRESQL:
			c = ColumnTypeConvertor.convertFromTruetypeToPostgresql(truetype);
			break;
		// TODO implementer les autres convertisseurs
		default:
			break;
		}
		return c;
	}

	private static ColumnType convertFromTruetypeToPostgresql(String truetype) {

		ColumnType c = ColumnType.OTHER;

		switch (truetype) {
		case "character varying":
		case "text":
			c = ColumnType.VARCHAR;
			break;
		case "char":
		case "character":
			c = ColumnType.CHAR;
			break;
		case "smallint":
		case "integer":
		case "bigint":
		case "decimal":
		case "numeric":
			c = ColumnType.INTEGER;
			break;
		case "real":
		case "double precision":
		case "money":
			c = ColumnType.FLOAT;
			break;
		case "serial":
		case "bigserial":
			c = ColumnType.SERIAL;
			break;
		case "bytes":
			c = ColumnType.BINARY;
			break;
		case "timestamp":
		case "interval":
		case "date":
		case "time":
			c = ColumnType.DATE;
			break;
		case "boolean":
			c = ColumnType.BOOLEAN;
			break;
		case "point":
		case "line":
		case "lseg":
		case "box":
		case "path":
		case "polygon":
		case "circle":
			c = ColumnType.GEOMETRIC;
			break;
		case "cidr":
		case "inet":
		case "macaddr":
			c = ColumnType.NETWORK;
			break;
		case "bit":
		case "bit varying":
			c = ColumnType.BINARYSTRING;
			break;
		case "oid":
		case "regproc":
		case "regprocedure":
		case "regoper":
		case "regoperator":
		case "regclass":
		case "regtype":
			c = ColumnType.IDENTIFIER;
			break;
		default:
			break;
		}
		return c;
	}

	private ColumnTypeConvertor() {
	};

}
