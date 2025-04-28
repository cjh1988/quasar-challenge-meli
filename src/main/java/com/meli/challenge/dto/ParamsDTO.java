package com.meli.challenge.dto;

import com.meli.challenge.model.Satellite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParamsDTO {

    private HashMap<String, Satellite> satelliteHashMap;
    private List<String> satellitesNames;
}
