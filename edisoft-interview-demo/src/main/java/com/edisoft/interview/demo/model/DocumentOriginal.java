package com.edisoft.interview.demo.model;

import com.edisoft.interview.demo.json.view.ShortView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class DocumentOriginal extends BaseEntity {
    @JsonView(ShortView.class)
    private LocalDateTime creationDateTime;

    @JsonView(ShortView.class)
    private String fileName;

    @Lob
    private String content;

    @OneToMany(mappedBy = "original")
    private List<DocumentTransformed> documentTransformedList;
}
