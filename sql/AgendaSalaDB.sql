DROP TABLE salas;
DROP TABLE usuarios;
DROP TABLE reservas;

CREATE TABLE salas
(
	idSala INTEGER NOT NULL,
	numSala INTEGER NOT NULL,
	andar INTEGER NOT NULL,
	numAssentos INTEGER NULL,
	tamanho DECIMAL(10,2) NOT NULL
);

CREATE TABLE usuarios
(
	matricula INTEGER NOT NULL,
	telefone varchar(13) NOT NULL,
	nome varchar(50) NOT NULL,
	dataNasc DATE NOT NULL
);

CREATE TABLE reservas
(
	idReserva INTEGER NOT NULL,
	idSala INTEGER NOT NULL,
	matricula INTEGER NOT NULL,
	dtInicio DATETIME NOT NULL,
	dtFim DATETIME NOT NULL,
	dtReserva DATE NOT NULL
);

ALTER TABLE salas
ADD PRIMARY KEY (idSala);

ALTER TABLE usuarios
ADD PRIMARY KEY (matricula);

ALTER TABLE reservas
ADD PRIMARY KEY (idReserva);

ALTER TABLE reservas
ADD CONSTRAINT f1 FOREIGN KEY (idSala)
REFERENCES salas(idSala);

ALTER TABLE reservas
ADD CONSTRAINT f2 FOREIGN KEY (matricula)
REFERENCES usuarios(matricula);

