package com.meli.challenge.service;

import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMessage_Success() {
        var messages = new ArrayList<List<String>>(List.of( new ArrayList<>(List.of("", "es", "", "")), new ArrayList<>(List.of("este", "", "un", "")), new ArrayList<>(List.of("", "", "", "mensaje"))));

        assertEquals("este es un mensaje", messageService.getMessage(messages));
    }

    @Test
    void testGetMessage_IncompleteMessage_ThrowsException() {
        var messages = new ArrayList<List<String>>(List.of( new ArrayList<>(List.of("", "es", "", "")), new ArrayList<>(List.of("este", "", "", "")), new ArrayList<>(List.of("", "", "", ""))));

        var exception = assertThrows(FieldValidatorException.class, () -> messageService.getMessage(messages));

        assertEquals("There's no enough information to process the message", exception.getMessage());
    }

    @Test
    void testGetMessage_AlreadyCompleteMessage() {
        var messages = List.of(
                List.of("este", "es", "un", "mensaje")
        );

        var result = messageService.getMessage(messages);

        assertEquals("este es un mensaje", result);
    }
}
