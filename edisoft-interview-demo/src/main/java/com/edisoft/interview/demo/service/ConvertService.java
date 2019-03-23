package com.edisoft.interview.demo.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@Service
public class ConvertService {
    private static final String XSL_FILE = "idoc2order.xsl";

    private Transformer transformer;
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder builder;

    @PostConstruct
    private void init() throws TransformerConfigurationException, ParserConfigurationException {
        ClassLoader classLoader = getClass().getClassLoader();
        File xsl = new File(classLoader.getResource(XSL_FILE).getFile());

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformer = transformerFactory.newTransformer(new StreamSource(xsl));

        builder = factory.newDocumentBuilder();
    }


    public String transformXml(File xmlFile) throws IOException, SAXException, TransformerException {
        StringWriter writer = new StringWriter();
        Document document = builder.parse(xmlFile);
        DOMSource source = new DOMSource(document);
        transformer.transform(source, new StreamResult(writer));
        return writer.toString();
    }
}
