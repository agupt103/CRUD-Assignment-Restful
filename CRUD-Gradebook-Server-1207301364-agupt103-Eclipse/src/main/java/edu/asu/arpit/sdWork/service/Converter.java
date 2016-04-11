package edu.asu.arpit.sdWork.service;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class Converter {
	 public static String convertFromObjectToXml(Object source, Class... type) {
	        
	        String result;
	        StringWriter sw = new StringWriter();
	        try {
	            JAXBContext context     = JAXBContext.newInstance(type);
	            Marshaller  marshaller  = context.createMarshaller();
	            marshaller.marshal(source, sw);
	            result = sw.toString();
	        } catch (JAXBException e) {
	            throw new RuntimeException();
	        }

	        return result;
	    }

}
