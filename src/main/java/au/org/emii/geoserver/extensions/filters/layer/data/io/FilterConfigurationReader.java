/*
 * Copyright 2014 IMOS
 *
 * The AODN/IMOS Portal is distributed under the terms of the GNU General Public License
 *
 */

package au.org.emii.geoserver.extensions.filters.layer.data.io;

import au.org.emii.geoserver.extensions.filters.layer.data.FilterConfiguration;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class FilterConfigurationReader extends FilterConfigurationIO {

    public FilterConfigurationReader(String dataDirectoryPath) {
        this.dataDirectoryPath = dataDirectoryPath;
    }

    public FilterConfiguration read() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(String.format("%s/%s", dataDirectoryPath, FILTER_CONFIGURATION_FILE_NAME)));

        return new FilterConfiguration(dataDirectoryPath, new FiltersDocumentParser(doc).getFilters());
    }
}
