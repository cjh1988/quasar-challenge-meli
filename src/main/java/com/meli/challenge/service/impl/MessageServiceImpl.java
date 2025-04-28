package com.meli.challenge.service.impl;

import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private static String END_PROCESS_MSG = "::: Finishing the getMessage Process :::";


    @Override
    public String getMessage(List<List<String>> messages) {
        log.info("::: Starting the getMessage Process :::");

        var incomingMessages = messages.getFirst();
        if (!incomingMessages.contains("")) {
            log.info(END_PROCESS_MSG);
            return String.join(" ", incomingMessages);
        }
        for (int idx = 1; idx < messages.size(); idx++) {
            if (!messages.get(idx).contains("")) {
                log.info(END_PROCESS_MSG);
                return String.join(" ", messages.get(idx));
            }

            for (int idx2 = 0; idx2 < messages.get(idx).size(); idx2++) {
                if (incomingMessages.get(idx2).isEmpty() && !messages.get(idx).get(idx2).isEmpty()) {
                    incomingMessages.set(idx2, messages.get(idx).get(idx2));
                }
                if (!incomingMessages.contains("")) {
                    log.info(END_PROCESS_MSG);
                    return String.join(" ", incomingMessages);
                }
            }
        }
        log.info(END_PROCESS_MSG);
        if (incomingMessages.contains(""))
            throw new FieldValidatorException("There's no enough information to process the message", HttpStatus.NOT_FOUND);
        return "";
    }
}
