package com.meli.challenge.dto.response;

import com.meli.challenge.model.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretResponseDTO {

    private Point position;
    private String message;
}
