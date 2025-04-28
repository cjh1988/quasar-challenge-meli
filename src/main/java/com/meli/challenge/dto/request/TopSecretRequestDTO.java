package com.meli.challenge.dto.request;

import com.meli.challenge.dto.SatelliteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopSecretRequestDTO {

    List<SatelliteDTO> satellites;
}
