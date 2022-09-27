package com.assessment.sn.mathapi.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NumbersProcessingRequest {

    @NotEmpty(message = "Numbers can not be empty")
    @NotNull(message = "Numbers are mandatory")
    private List<Double> numbers;

}
