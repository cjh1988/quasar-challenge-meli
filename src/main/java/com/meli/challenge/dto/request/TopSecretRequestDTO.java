package com.meli.challenge.dto.request;

import com.meli.challenge.model.Satellite;
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

    List<Satellite> satellites;
}
