package com.assessment.sn.mathapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NumbersQuantifierRequest extends NumbersProcessingRequest {

    @NotNull(message = "Quantifier is mandatory")
    private Integer quantifier;

    public NumbersQuantifierRequest(@NotNull @NotEmpty List<Double> numbers, @NotNull Integer quantifier) {
        super(numbers);
        this.quantifier = quantifier;
    }
}
