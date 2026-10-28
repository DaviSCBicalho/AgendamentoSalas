DROP TABLE salas;
DROP TABLE usuarios;
DROP TABLE reservas;

CREATE TABLE salas
(
	numSala INTEGER NOT NULL,
	andar INTEGER NOT NULL,
	numAssentos INTEGER NULL,
	tamanho DECIMAL(10,2) NOT NULL
);

CREATE TABLE usuarios
(
	matricula INTEGER NOT NULL,
	telefone INTEGER NOT NULL,
	nome varchar2(50) NOT NULL,
	dataNasc DATE NOT NULL
);

CREATE TABLE reservas
(
	idReserva INTEGER NOT NULL,
	numSala INTEGER NOT NULL,
	matricula INTEGER NOT NULL,
	dtInicio DATE NOT NULL,
	dtFim DATE NOT NULL,
	dtReserva DATE NOT NULL
);

ALTER TABLE salas
ADD PRIMARY KEY (numSala);

ALTER TABLE usuarios
ADD PRIMARY KEY (matricula);

ALTER TABLE reservas
ADD PRIMARY KEY (idReserva);

ALTER TABLE reservas
ADD CONSTRAINT f1 FOREIGN KEY (numSala)
REFERENCES salas(numSala);

ALTER TABLE reservas
ADD CONSTRAINT f2 FOREIGN KEY (matricula)
REFERENCES usuarios(matricula);

