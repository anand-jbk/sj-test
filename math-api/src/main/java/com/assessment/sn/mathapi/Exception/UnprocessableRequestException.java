package com.assessment.sn.mathapi.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UnprocessableRequestException extends RuntimeException{

    private String message;
    private List<String> details;
}
