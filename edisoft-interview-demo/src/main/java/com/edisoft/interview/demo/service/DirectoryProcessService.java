package com.edisoft.interview.demo.service;

import com.edisoft.interview.demo.model.DocumentOriginal;
import com.edisoft.interview.demo.model.DocumentTransformed;
import com.edisoft.interview.demo.repo.DocumentOriginalRepo;
import com.edisoft.interview.demo.repo.DocumentTransformedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Service
public class DirectoryProcessService {
    @Value("${input.file.directory}")
    private String inputDir;

    @Autowired
    private ConvertService convertService;
    @Autowired
    private DocumentTransformedRepo documentTransformedRepo;
    @Autowired
    private DocumentOriginalRepo documentOriginalRepo;

    @Scheduled(fixedDelay = 1000)
    public void processFiles() throws InterruptedException {
        for (File file : new File(inputDir).listFiles()) {
            if (file.isFile() && file.getName().endsWith(".xml")) {
                try {
                    String originalXml = String.join("\n", Files.readAllLines(file.toPath()));
                    String transformedXml = convertService.transformXml(file);
                    saveDocuments(file, originalXml, transformedXml);
                } catch (IOException | SAXException | TransformerException e) {
                    e.printStackTrace();
                    continue;
                }

                while (!file.delete()) {
                    Thread.sleep(1);
                }
            }
        }
    }

    @Transactional
    void saveDocuments(File file, String originalXml, String transformedXml) {
        DocumentOriginal documentOriginal = new DocumentOriginal();
        documentOriginal.setFileName(file.getName());
        documentOriginal.setCreationDateTime(LocalDateTime.now());
        documentOriginal.setContent(originalXml);
        documentOriginalRepo.save(documentOriginal);

        DocumentTransformed documentTransformed = new DocumentTransformed();
        documentTransformed.setFileName(file.getName());
        documentTransformed.setCreationDateTime(LocalDateTime.now());
        documentTransformed.setContent(transformedXml);
        documentTransformed.setOriginal(documentOriginal);
        documentTransformedRepo.save(documentTransformed);
    }
}
