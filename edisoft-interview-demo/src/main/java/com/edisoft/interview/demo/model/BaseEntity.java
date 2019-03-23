package com.edisoft.interview.demo.model;

import com.edisoft.interview.demo.json.view.ShortView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ShortView.class)
    private Long id;
}
