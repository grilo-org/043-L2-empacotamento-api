-- Criação do modelo de banco de dados para o exercício 2

-- Tabela de Professores
CREATE TABLE professores (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)
);

-- Tabela de Disciplinas
CREATE TABLE disciplinas (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL
);

-- Tabela de Salas
CREATE TABLE salas (
    id INT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    capacidade INT NOT NULL,
    bloco VARCHAR(10)
);

-- Tabela de Horários
CREATE TABLE horarios (
    id INT PRIMARY KEY,
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL
);

-- Tabela de Aulas (relacionamento entre professor, disciplina, sala e horário)
CREATE TABLE aulas (
    id INT PRIMARY KEY,
    professor_id INT NOT NULL,
    disciplina_id INT NOT NULL,
    sala_id INT NOT NULL,
    horario_id INT NOT NULL,
    FOREIGN KEY (professor_id) REFERENCES professores(id),
    FOREIGN KEY (disciplina_id) REFERENCES disciplinas(id),
    FOREIGN KEY (sala_id) REFERENCES salas(id),
    FOREIGN KEY (horario_id) REFERENCES horarios(id)
);

-- Inserção de dados de exemplo
-- Professores
INSERT INTO professores (id, nome, email, telefone) VALUES
(1, 'Girafales', 'girafales@escola.com', '1234-5678'),
(2, 'Madruga', 'madruga@escola.com', '2345-6789'),
(3, 'Florinda', 'florinda@escola.com', '3456-7890');

-- Disciplinas
INSERT INTO disciplinas (id, nome, carga_horaria) VALUES
(1, 'Matemática', 60),
(2, 'Português', 80),
(3, 'Ciências', 40),
(4, 'História', 40),
(5, 'Geografia', 40);

-- Salas
INSERT INTO salas (id, nome, capacidade, bloco) VALUES
(1, 'Sala 101', 30, 'A'),
(2, 'Sala 102', 25, 'A'),
(3, 'Sala 201', 35, 'B'),
(4, 'Sala 202', 40, 'B');

-- Horários
INSERT INTO horarios (id, dia_semana, hora_inicio, hora_fim) VALUES
(1, 'Segunda', '08:00', '10:00'),
(2, 'Segunda', '10:00', '12:00'),
(3, 'Segunda', '14:00', '16:00'),
(4, 'Segunda', '16:00', '18:00'),
(5, 'Terça', '08:00', '10:00'),
(6, 'Terça', '10:00', '12:00'),
(7, 'Terça', '14:00', '16:00'),
(8, 'Terça', '16:00', '18:00'),
(9, 'Quarta', '08:00', '10:00'),
(10, 'Quarta', '10:00', '12:00'),
(11, 'Quarta', '14:00', '16:00'),
(12, 'Quarta', '16:00', '18:00');

-- Aulas
INSERT INTO aulas (id, professor_id, disciplina_id, sala_id, horario_id) VALUES
(1, 1, 1, 1, 1),  -- Girafales, Matemática, Sala 101, Segunda 08:00-10:00
(2, 1, 1, 2, 5),  -- Girafales, Matemática, Sala 102, Terça 08:00-10:00
(3, 1, 3, 3, 9),  -- Girafales, Ciências, Sala 201, Quarta 08:00-10:00
(4, 2, 2, 1, 2),  -- Madruga, Português, Sala 101, Segunda 10:00-12:00
(5, 2, 4, 2, 6),  -- Madruga, História, Sala 102, Terça 10:00-12:00
(6, 3, 5, 3, 3),  -- Florinda, Geografia, Sala 201, Segunda 14:00-16:00
(7, 3, 2, 4, 7);  -- Florinda, Português, Sala 202, Terça 14:00-16:00
