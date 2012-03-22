package gd.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 * 
 * Utilitaire de convertion des realtypes en fonction du type de SGBD
 * 
 * @author thibaut
 * @version 0.1
 * 
 */
public class ConvertTypeUtil {
	private static final Logger logger = Logger
			.getLogger(ConvertTypeUtil.class);

	private static final Map<String, HashMap<String, String>> columnconverts = new HashMap<String, HashMap<String, String>>();
	private static final Map<String, HashMap<String, String>> constraintconverts = new HashMap<String, HashMap<String, String>>();

	/**
	 * 
	 * "Chose" à convertir
	 * 
	 * @author thibaut
	 * 
	 */
	public enum Thing {
		/**
		 * Convertir un type de colonne
		 */
		COLUMN,

		/**
		 * Convertir un type de contrainte
		 */
		CONSTRAINT
	}

	private static String generateConfURL(String sgbdtype, Thing convertType) {

		String url = System.getProperty("user.dir") + "/bin/gd/conf/types/"
				+ sgbdtype + "/" + convertType.toString().toLowerCase()
				+ "Types.xml";
		logger.debug("Génération d'une url de conf de types de columns : \n"
				+ url);
		return url;
	}

	private static HashMap<String, String> createConvert(String sgbdtype,
			Thing convertType) throws IOException, JDOMException {

		Document doc = XmlUtil.parseXmlFile(ConvertTypeUtil.generateConfURL(
				sgbdtype, convertType));

		Element racine = doc.getRootElement();

		List<?> listeGenericTypes = racine.getChildren("genericType");

		Iterator<?> igt = listeGenericTypes.iterator();

		HashMap<String, String> con = new HashMap<String, String>();

		while (igt.hasNext()) {
			Element genericType = (Element) igt.next();
			String genericTypeString = genericType.getAttributeValue("name");

			Iterator<?> it = genericType.getChildren("type").iterator();
			while (it.hasNext()) {
				Element trueType = (Element) it.next();
				con.put(trueType.getText(), genericTypeString);
				logger.debug("Ajout d'un type " + trueType.getText() + " -- "
						+ genericTypeString);
			}

		}

		return con;
	}

	private static HashMap<String, String> getConverts(String sgbdtype,
			Thing convertType) throws IOException, JDOMException {

		HashMap<String, String> con = null;

		switch (convertType) {
		case COLUMN:
			con = columnconverts.get(sgbdtype);
			break;
		case CONSTRAINT:
			con = constraintconverts.get(sgbdtype);
			break;
		}

		if (con == null) {
			con = ConvertTypeUtil.createConvert(sgbdtype, convertType);

			switch (convertType) {
			case COLUMN:
				columnconverts.put(sgbdtype, con);
				break;
			case CONSTRAINT:
				constraintconverts.put(sgbdtype, con);
			default:
				break;
			}
		}

		return con;
	}

	/**
	 * Convertit un realtype en generictype du sgbd passé en paramètre
	 * 
	 * @param sgbdtype
	 *            Type de SGBD
	 * @param type
	 *            Realtype à convertir
	 * @param convertType
	 *            Chose à convertir (Colonne ou contrainte)
	 * @return Type générique
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static String convert(String sgbdtype, String type, Thing convertType)
			throws IOException, JDOMException {
		HashMap<String, String> con = getConverts(sgbdtype, convertType);
		String converted = con.get(type);
		if (converted == null)
			converted = "OTHER";
		return converted;
	}
}
