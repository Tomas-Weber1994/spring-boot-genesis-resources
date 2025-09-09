package com.engeto.genesis_resources.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Surname must not be empty")
    private String surname;

    @Pattern(regexp = "[a-zA-Z0-9]{12}", message = "Person ID must be 12 alphanumeric characters")
    private String personId;

    private String uuid;
}
