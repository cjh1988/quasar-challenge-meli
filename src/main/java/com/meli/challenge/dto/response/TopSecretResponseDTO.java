package com.meli.challenge.dto.response;

import com.meli.challenge.dto.PointDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretResponseDTO {

    private PointDTO position;
    private String message;
}
