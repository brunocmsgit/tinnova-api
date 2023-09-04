CREATE TABLE veiculo (
      id bigint NOT NULL AUTO_INCREMENT,
      veiculo varchar(255) NOT NULL,
      marca varchar(255) NOT NULL,
      ano int NOT NULL,
      descricao varchar(255) NOT NULL,
      vendido bit(1) NOT NULL,
      created datetime(6) DEFAULT NULL,
      updated datetime(6) DEFAULT NULL,
      PRIMARY KEY (id)
);