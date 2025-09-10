package com.engeto.genesis_resources.service.validation;

import com.engeto.genesis_resources.dto.PersonIdResponseDTO;
import com.engeto.genesis_resources.exception.PersonIdServerUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service validating personId against a mock certification authority API.
 * Normally, these IDs would come from a real external service.
 */
@Service
@Slf4j
public class PersonIdService {

    @Value("${personid.service.url}")
    private String validIdsUrl;

    public boolean isValidPersonId(String personId) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            PersonIdResponseDTO response = restTemplate.getForObject(validIdsUrl, PersonIdResponseDTO.class);

            if (response == null || response.getValidPersonIds() == null) {
                log.warn("PersonId API returned null or empty response");
                return false;
            }

            List<String> validIds = response.getValidPersonIds();
            return validIds.contains(personId);

        } catch (RestClientException e) {
            throw new PersonIdServerUnavailableException(
                    "PersonId service not reachable, please try restart.", e
            );
        }
    }
}
