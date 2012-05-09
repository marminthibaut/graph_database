package gd.app.util;

import gd.util.XmlUtil;

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
 * @author Thibaut Marmin <marminthibaut@gmail.com>
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
     * @author Thibaut Marmin
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
            Thing convertType) throws ConvertTypeUtilException {

        Document doc;
        try {
            doc = XmlUtil.parseXmlFile(ConvertTypeUtil.generateConfURL(
                    sgbdtype, convertType));
        } catch (IOException e) {
            throw new ConvertTypeUtilException("Fail to open config file \""
                    + ConvertTypeUtil.generateConfURL(sgbdtype, convertType)
                    + "\".", e);
        } catch (JDOMException e) {
            throw new ConvertTypeUtilException(
                    "XML Parsing error. Verify your config file syntax \""
                            + ConvertTypeUtil.generateConfURL(sgbdtype,
                                    convertType) + "\"", e);
        }

        Element racine = doc.getRootElement();

        List<?> listeGenericTypes = racine.getChildren("genericType");

        Iterator<?> igt = listeGenericTypes.iterator();

        HashMap<String, String> con = new HashMap<String, String>();

        while (igt.hasNext()) {
            Element genericType = (Element) igt.next();
            String genericTypeString = genericType.getAttributeValue("name")
                    .toUpperCase();

            Iterator<?> it = genericType.getChildren("type").iterator();
            while (it.hasNext()) {
                Element trueType = (Element) it.next();
                con.put(trueType.getText().toLowerCase(), genericTypeString);
                logger.debug("Ajout d'un type "
                        + trueType.getText().toLowerCase() + " -- "
                        + genericTypeString);
            }

        }

        return con;
    }

    private static HashMap<String, String> getConverts(String sgbdtype,
            Thing convertType) throws ConvertTypeUtilException {

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
     * @throws ConvertTypeUtilException
     */
    public static String convert(String sgbdtype, String type, Thing convertType)
            throws ConvertTypeUtilException {
        HashMap<String, String> con = getConverts(sgbdtype, convertType);
        String converted = con.get(type.toLowerCase());
        if (converted == null)
            converted = "OTHER";
        return converted;
    }
}
