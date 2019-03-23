package com.edisoft.interview.demo.controller;

import com.edisoft.interview.demo.json.view.ShortView;
import com.edisoft.interview.demo.model.DocumentOriginal;
import com.edisoft.interview.demo.model.DocumentTransformed;
import com.edisoft.interview.demo.repo.DocumentOriginalRepo;
import com.edisoft.interview.demo.repo.DocumentTransformedRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/documents")
public class ApiController {
    @Autowired
    private DocumentTransformedRepo documentTransformedRepo;
    @Autowired
    private DocumentOriginalRepo documentOriginalRepo;

    @ResponseBody
    @RequestMapping(value = "original", method = RequestMethod.GET)
    @JsonView(ShortView.class)
    public Iterable<DocumentOriginal> getDocumentOriginalAll() {
        return documentOriginalRepo.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "original/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String getDocumentOriginalById(@PathVariable Long id) {
        DocumentOriginal documentOriginal = documentOriginalRepo.findById(id).orElse(null);
        if (null != documentOriginal) {
            return documentOriginal.getContent();
        } else {
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "transformed", method = RequestMethod.GET)
    @JsonView(ShortView.class)
    public Iterable<DocumentTransformed> getAll() {
        return documentTransformedRepo.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "transformed/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String getById(@PathVariable Long id) {
        DocumentTransformed documentTransformed = documentTransformedRepo.findById(id).orElse(null);
        if (null != documentTransformed) {
            return documentTransformed.getContent();
        } else {
            return null;
        }
    }
}
