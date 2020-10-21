package com.service.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidProposalResponseException extends RuntimeException {
    public InvalidProposalResponseException() {
        super("Proposal response doesn't exists");
    }
}
