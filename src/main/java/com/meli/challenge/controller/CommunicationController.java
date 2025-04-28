package com.meli.challenge.controller;

import com.meli.challenge.dto.ErrorDTO;
import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.dto.request.TopSecretSplitRequestDTO;
import com.meli.challenge.dto.response.TopSecretResponseDTO;
import com.meli.challenge.service.CommunicationService;
import com.meli.challenge.service.SatelliteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Tag(name = "comunication")
@RequiredArgsConstructor
public class CommunicationController {

    private final CommunicationService communicationService;
    private final SatelliteService satelliteService;

    @Operation(
            summary = "Return a top secret message",
            description = "Deliver a top secret message based on the satellites' data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TopSecretResponseDTO.class))),

            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)))})
    @PostMapping("/topsecret")
    public ResponseEntity<TopSecretResponseDTO> processCommunication(@RequestBody TopSecretRequestDTO request) {
        return ResponseEntity.ok(communicationService.processMessage(request));
    }

    @Operation(
            summary = "Update data for a specific satellite",
            description = "Update data for a specific satellite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Void.class))),

            @ApiResponse(responseCode = "404", description = "Satellite not Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)))})
    @PostMapping("/topsecret_split/{satellite_name}")
    public ResponseEntity<Void> updateSatelliteData(@PathVariable(name = "satellite_name") String satelliteName,@RequestBody TopSecretSplitRequestDTO request) {
        satelliteService.updateSatellite(satelliteName, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Update data for a specific satellite",
            description = "Update data for a specific satellite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TopSecretResponseDTO.class))),

            @ApiResponse(responseCode = "404", description = "Satellite not Found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDTO.class)))})
    @GetMapping("/topsecret_split/{satellite_name}")
    public ResponseEntity<TopSecretResponseDTO> processMessageSplit(@PathVariable(name = "satellite_name") String satelliteName) {
        return ResponseEntity.ok(communicationService.processMessageSplit(satelliteName));
    }
}
