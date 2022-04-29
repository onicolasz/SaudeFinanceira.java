CREATE DATABASE bd_saudefinanceira;

DROP DATABASE bd_saudefinanceira;

USE bd_saudefinanceira;

CREATE TABLE usuario (id_usuario BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
nome VARCHAR(50),
cpf VARCHAR(14) UNIQUE,
email VARCHAR(50) UNIQUE,
senha VARCHAR(25),
data_nascimento VARCHAR(10),
profissao VARCHAR(50),
PRIMARY KEY (id_usuario));

CREATE TABLE conta_pessoal (id_conta_pessoal BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
saldo BIGINT,
meta BIGINT,
tipo_conta VARCHAR(10),
id_usuario BIGINT,
FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario ) ON DELETE CASCADE,
PRIMARY KEY (id_conta_pessoal));

CREATE TABLE conta_empresa (id_conta_empresa BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
saldo BIGINT,
meta BIGINT,
nome_empresa VARCHAR(50),
cnpj VARCHAR(18),
tipo_conta VARCHAR(10),
id_usuario BIGINT,
FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario ) ON DELETE CASCADE,
PRIMARY KEY (id_conta_empresa));

CREATE TABLE rendas (id_renda BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
valor BIGINT NOT NULL,
classifica VARCHAR(50) NOT NULL,
id_conta_pessoal BIGINT,
id_conta_empresa BIGINT,
FOREIGN KEY (id_conta_pessoal) REFERENCES conta_pessoal(id_conta_pessoal ) ON DELETE CASCADE,
FOREIGN KEY (id_conta_empresa) REFERENCES conta_empresa(id_conta_empresa ) ON DELETE CASCADE,
PRIMARY KEY (id_renda));

CREATE TABLE despesas (id_despesa BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
valor BIGINT NOT NULL,
classifica VARCHAR(50),
id_conta_pessoal BIGINT,
id_conta_empresa BIGINT,
FOREIGN KEY (id_conta_pessoal) REFERENCES conta_pessoal(id_conta_pessoal ) ON DELETE CASCADE,
FOREIGN KEY (id_conta_empresa) REFERENCES conta_empresa(id_conta_empresa ) ON DELETE CASCADE,
PRIMARY KEY (id_despesa));

CREATE TABLE funcionarios (id_funcionario BIGINT UNIQUE AUTO_INCREMENT NOT NULL,
salario BIGINT NOT NULL,
nome VARCHAR(50),
funcao VARCHAR(30),
id_conta_empresa BIGINT NOT NULL,
FOREIGN KEY (id_conta_empresa) REFERENCES conta_empresa(id_conta_empresa) ON DELETE CASCADE,
PRIMARY KEY (id_funcionario));

