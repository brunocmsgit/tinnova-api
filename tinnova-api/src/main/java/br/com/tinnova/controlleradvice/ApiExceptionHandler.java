package br.com.tinnova.controlleradvice;

import br.com.tinnova.exception.EntidadeNaoEncontradaException;
import br.com.tinnova.exception.ErrorMessage;
import br.com.tinnova.exception.ErrorType;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;
    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = """
            Erro interno inesperado no sistema. Tente novamente e se
            o problema persistir, entre em contato com o administrador do sistema.
            """;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (body == null) {
            body = ErrorMessage.builder()
                    .title(HttpStatus.valueOf(statusCode.value()).getReasonPhrase())
                    .status(statusCode.value())
                    .build();
        } else if (body instanceof String) {
            body = ErrorMessage.builder()
                    .title(String.valueOf(body))
                    .status(statusCode.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        var errorMessage = createErrorBuilder(status, ErrorType.ERRO_DE_SISTEMA, detail).build();

        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        var errorMessage = createErrorBuilder(HttpStatus.valueOf(status.value()), ErrorType.REQUISICAO_INVALIDA, "Requsição inválida - Erro de Sintaxe").build();

        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        BindingResult bindingResult = ex.getBindingResult();

        List<ErrorMessage.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return ErrorMessage.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        ErrorMessage errorMessage = createErrorBuilder((HttpStatus) status, ErrorType.DADOS_INVALIDOS, detail)
                .userMessage(detail)
                .fields(problemFields)
                .build();

        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String detail = String.format("O recurso '%s' não foi encontrado.",
                ex.getRequestURL());
        var errorMessage = createErrorBuilder(HttpStatus.valueOf(status.value()), ErrorType.RECURSO_NAO_ENCONTRADO, detail).build();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), status, request);

    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        var detail = String.format("A propriedade '%s' recebeu o valor '%s'" +
                        ", que é do tipo inválido. Corrija e informe um valor compatível com o tipo '%s'",
                path, ex.getValue(), ex.getTargetType());

        var errorMessage = createErrorBuilder(HttpStatus.valueOf(status.value()), ErrorType.REQUISICAO_INVALIDA, detail).build();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        var errorMessage = createErrorBuilder(HttpStatus.NOT_FOUND, ErrorType.ENTIDADE_NAO_ENCONTRADA, ex.getMessage()).build();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private ErrorMessage.ErrorMessageBuilder createErrorBuilder(HttpStatus status,ErrorType errorType, String detail) {
        return ErrorMessage.builder()
                .status(status.value())
                .type(errorType.getUri())
                .title(errorType.getTitle())
                .detail(detail);
    }

}

