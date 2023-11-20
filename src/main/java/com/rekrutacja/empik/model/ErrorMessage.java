package com.rekrutacja.empik.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
}
