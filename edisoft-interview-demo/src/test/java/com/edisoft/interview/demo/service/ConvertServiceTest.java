package com.edisoft.interview.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.ElementSelectors;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertServiceTest {
    @Autowired
    private ConvertService convertService;

    @Test
    public void transformXml() throws TransformerException, SAXException, IOException {
        File xmlFile = new File("test_data/original_order.xml");
        File controlXmlFile = new File("test_data/out_example.xml");

        String controlXml = String.join("\n", Files.readAllLines(controlXmlFile.toPath()));
        String transformedXml = convertService.transformXml(xmlFile);

        Diff diff = DiffBuilder.compare(controlXml).withTest(transformedXml)
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
                .checkForSimilar()
                .ignoreWhitespace()
                .build();

        assertFalse(diff.hasDifferences());
    }
}