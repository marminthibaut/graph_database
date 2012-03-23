package gd.util;

import java.io.*;

import org.apache.log4j.Logger;
import org.jdom.*;
import org.jdom.input.*;

/**
 * 
 * Utilitaire de parsing XML
 * 
 * @author thibaut
 * 
 */
public class XmlUtil {

    private static final Logger logger = Logger.getLogger(XmlUtil.class);

    /**
     * 
     * Parse le fichier passé en paramètre et retourne le document jDom
     * 
     * @param path
     * @return Document jDom
     * @throws IOException
     * @throws JDOMException
     */
    public static Document parseXmlFile(String path) throws IOException,
            JDOMException {
        SAXBuilder sxb = new SAXBuilder();
        Document document = null;

        try {
            document = sxb.build(new File(path));
        } catch (IOException e) {
            logger.warn("Erreur d'ouverture du fichier xml " + path + "\n"
                    + e.getMessage());
            throw e;
        } catch (JDOMException e) {
            logger.warn("Erreur de parsing du fichier xml " + path + "\n"
                    + e.getMessage());
            throw e;
        }

        return document;
    }
}
