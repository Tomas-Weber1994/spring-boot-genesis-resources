package com.engeto.genesis_resources.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLiteResponseDTO {
    private Long id;
    private String name;
    private String surname;
}
