package com.meli.challenge.service;

import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.dto.response.TopSecretResponseDTO;

public interface CommunicationService {

    TopSecretResponseDTO processMessage(TopSecretRequestDTO request);

    TopSecretResponseDTO processMessageSplit(String name);
}
