package br.com.tinnova.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    REQUISICAO_INVALIDA("/requisicao-invalida", "Requisição inválida - Bad Request"),

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),

    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),

    DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

    private String uri;
    private String title;

    ErrorType(String path, String title) {
        this.uri = "http://localhost:8080" +path;
        this.title = title;
    }

}
