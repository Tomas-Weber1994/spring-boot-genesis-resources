package com.engeto.genesis_resources.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonIdResponseDTO {
    private List<String> validPersonIds;
}
