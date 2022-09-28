package com.assessment.sn.mathapi.controller;

import com.assessment.sn.mathapi.model.NumbersProcessingRequest;
import com.assessment.sn.mathapi.model.NumbersQuantifierRequest;
import com.assessment.sn.mathapi.model.OperationResult;
import com.assessment.sn.mathapi.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("math")
public class MathController {

    @Autowired
    MathService mathService;

    @PostMapping(path = "/min", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResult<List<Double>>> findMinNumbers(@Valid @RequestBody NumbersQuantifierRequest request) {
        return new ResponseEntity<>(new OperationResult<>(mathService.findMinNumbers(request.getNumbers(),request.getQuantifier())), HttpStatus.OK);
    }

    @PostMapping(path = "/max", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResult<List<Double>>> findMaxNumbers(@Valid @RequestBody NumbersQuantifierRequest request) {
        return new ResponseEntity<>(new OperationResult<>(mathService.findMaxNumbers(request.getNumbers(), request.getQuantifier())), HttpStatus.OK);
    }

    @PostMapping(path = "/avg", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResult<Double>> calculateAverage(@Valid @RequestBody NumbersProcessingRequest request) {
        return new ResponseEntity<>(new OperationResult<>(mathService.calculateAverage(request.getNumbers())), HttpStatus.OK);
    }

    @PostMapping(path = "/median", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResult<Double>>  calculateMedian(@Valid @RequestBody NumbersProcessingRequest request) {
        return new ResponseEntity<>(new OperationResult<>(mathService.calculateMedian(request.getNumbers())), HttpStatus.OK);
    }

    @PostMapping(path = "/percentile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationResult<Double>>  calculatePercentile(@Valid @RequestBody NumbersQuantifierRequest request) {
        return new ResponseEntity<>(new OperationResult<>(mathService.calculatePercentile(request.getNumbers(), request.getQuantifier())), HttpStatus.OK);
    }
}
