package com.engeto.genesis_resources.service.validation;

import com.engeto.genesis_resources.dto.PersonIdResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service validating personId against a mock certification authority API.
 * Normally, these IDs would come from a real external service.
 */
@Service
public class PersonIdService {

    @Value("${personid.service.url}")
    private String validIdsUrl;

    public boolean isValidPersonId(String personId) {
        RestTemplate restTemplate = new RestTemplate();
        PersonIdResponseDTO response = restTemplate.getForObject(validIdsUrl, PersonIdResponseDTO.class);

        if (response == null || response.getValidPersonIds() == null) {
            return false;
        }

        List<String> validIds = response.getValidPersonIds();
        return validIds.contains(personId);
    }
}
