package com.imaginea.cloudjee.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

/**
 * This class get data from Product.xml file. 
 * 
 * Should be used and updated for reading data from xml file.
 * 
 * @author krishnakumarnellore
 *
 */
public class ReaderUtil {

	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(ReaderUtil.class.getName());

	public List<String> getFileContentList(String attributeName)
	{
		List<String> elementNode = new ArrayList<String>();
		try
		{
			String xmlFileName = this.getClass().getClassLoader().getResource("product.xml").getFile();
			log.info("Got configuration file path "+xmlFileName);
			XMLConfiguration config = new XMLConfiguration(xmlFileName);			
			elementNode.add(config.getString(attributeName));
			log.info("Got attribute value for attribute name "+ attributeName);
		}
		catch(ConfigurationException cex)
		{
			log.error("Got exception while reading from configuration file, exception is "+ cex);
		}

		return elementNode;
	}

}