package ru.sputnik.interview.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
public class Product {
    public static final int MIN_SIZE = 42;
    public static final int MAX_SIZE = 54;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Place place;

    @NotNull
    @Column(name = "kind_of_clothes")
    private KindOfClothes kindOfClothes;

    @Min(MIN_SIZE)
    @Max(MAX_SIZE)
    private int size;

    @NotNull
    private BigDecimal cost;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Color color;

    @Column(length = 4096)
    private String description;
}
