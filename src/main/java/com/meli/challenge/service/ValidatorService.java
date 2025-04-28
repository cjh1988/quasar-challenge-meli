package com.meli.challenge.service;

import com.meli.challenge.dto.ParamsDTO;
import com.meli.challenge.dto.request.TopSecretRequestDTO;

public interface ValidatorService {

    void topSecretRequestValidation(TopSecretRequestDTO requestDTO);

    ParamsDTO requestMessageSplit(String name);
}
