# Desafio Tinnova

### Tecnologias
- Java 20
- Spring Boot 3
- Spring Data
- Lombok
- Flyway
- MySQL
- ModelMapper
- HATEOAS
- Swagger

### Ambiente
- Importar projeto Maven
- Criar uma base de dados com o nome **tinnova**
- Alterar usuário e senha no application.properties para acesso a base
- Executar projeto como Spring Boot
- O Flyway irá criar as tabelas

### Considerações
- Link para o Swagger: http://localhost:8080/swagger-ui/index.html
- Classe de Testes: [VeiculoServiceTest.java](src%2Ftest%2Fjava%2Fbr%2Fcom%2Ftinnova%2Fservice%2FVeiculoServiceTest.java)
- Collection Postman: [TinnovaAPI - Veiculos.postman_collection.json](TinnovaAPI%20-%20Veiculos.postman_collection.json)

### Endpoints ("/veiculos")
- POST (Resquest/ Response)
```
{
    "veiculo": "Uno mille",
    "marca": "Fiat",
    "ano": 2005,
    "descricao": "1.0 Gasolina",
    "vendido" : true
}
```
```
{
    "id": 1
    "veiculo": "Uno mille",
    "marca": "Fiat",
    "ano": 2005,
    "descricao": "1.0 Gasolina",
    "vendido" : true
}
```
- PUT e PATCH /{id} (Resquest/ Response)
```
{
    "veiculo": "Uno mille",
    "marca": "Fiat",
    "ano": 2005,
    "descricao": "1.0 Gasolina",
    "vendido" : true
}
```
```
{
    "id": 1
    "veiculo": "Uno mille",
    "marca": "Fiat",
    "ano": 2005,
    "descricao": "1.0 Gasolina",
    "vendido" : true
}
```

- GET /{id} (Response)
```
{
    "id": 4,
    "veiculo": "S10 Cabine Dupla",
    "marca": "Chevrolet",
    "ano": 2021,
    "descricao": "2.4 Diesel",
    "vendido": true,
    "created": "2023-08-26T23:59:33.770907",
    "updated": "2023-09-04T16:30:07.136275",
    "_links": {
        "listaVeiculos": {
            "href": "http://localhost:8080/veiculos"
        }
    }
}
```
- GET (Response)

```
[
    {
        "id": 4,
        "veiculo": "S10 Cabine Dupla",
        "marca": "Chevrolet",
        "ano": 2021,
        "descricao": "2.4 Diesel",
        "vendido": true,
        "created": "2023-08-26T23:59:33.770907",
        "updated": "2023-09-04T16:30:07.136275",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/veiculos/4"
            }
        ]
    }
]
```

- GET (Response) 
``` 
    /vendidos 
    /nao-vendidos 
    /ultima-semana
    /marca-ano?marca={marca}&ano={ano}
```

```
[
    {
        "id": 4,
        "veiculo": "S10 Cabine Dupla",
        "marca": "Chevrolet",
        "ano": 2021,
        "descricao": "2.4 Diesel",
        "vendido": true,
        "created": "2023-08-26T23:59:33.770907",
        "updated": "2023-09-04T16:30:07.136275",
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/veiculos/4"
            }
        ]
    }
]
```

- GET /{id} (Response)

```
{
    "id": 4,
    "veiculo": "S10 Cabine Dupla",
    "marca": "Chevrolet",
    "ano": 2021,
    "descricao": "2.4 Diesel",
    "vendido": true,
    "created": "2023-08-26T23:59:33.770907",
    "updated": "2023-09-04T16:30:07.136275",
    "links": [
        {
            "rel": "self",
            "href": "http://localhost:8080/veiculos/4"
        }
    ]
}
```

- GET /decada (Response)

```
[
    {
        "decada": 1980,
        "quantidade": 1
    },
    {
        "decada": 1990,
        "quantidade": 1
    },
    {
        "decada": 2000,
        "quantidade": 3
    },
    {
        "decada": 2020,
        "quantidade": 4
    }
]
```

- GET /marca (Response)

```
[
    {
        "marca": "Chevrolet",
        "quantidade": 2
    },
    {
        "marca": "Fiat",
        "quantidade": 4
    },
    {
        "marca": "Volkswagen",
        "quantidade": 3
    }
]
```
