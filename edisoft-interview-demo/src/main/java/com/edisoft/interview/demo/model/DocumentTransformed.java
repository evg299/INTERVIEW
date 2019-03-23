package com.edisoft.interview.demo.model;

import com.edisoft.interview.demo.json.view.ShortView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class DocumentTransformed extends BaseEntity {
    @JsonView(ShortView.class)
    private LocalDateTime creationDateTime;

    @JsonView(ShortView.class)
    private String fileName;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private DocumentOriginal original;
}
