package ru.bs.interview.demo.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{user.name.notNull}")
    @Size(min = 3, message = "{user.name.minSize}")
    private String name;

    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull
    @DecimalMin(value = "0.0", message = "{user.weight.minWeight}")
    private Double weight;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
}
