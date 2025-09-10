package br.dev.sant.sga.infrastructure.exceptions;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;
    private String path;
}
