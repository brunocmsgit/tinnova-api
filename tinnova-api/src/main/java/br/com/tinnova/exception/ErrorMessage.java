package br.com.tinnova.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Builder
public class ErrorMessage {

    private final Integer status;

    private final String type;

    private final String title;

    private final String detail;

    private String userMessage;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {

        private String name;
        private String userMessage;

    }

}
