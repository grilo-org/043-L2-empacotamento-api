-- Consulta 2: Lista de salas com horários livres e ocupados

WITH todos_horarios AS (
    SELECT 
        s.id AS sala_id,
        s.nome AS sala_nome,
        h.id AS horario_id,
        h.dia_semana,
        h.hora_inicio,
        h.hora_fim,
        CASE 
            WHEN a.id IS NOT NULL THEN 'Ocupado'
            ELSE 'Livre'
        END AS status,
        CASE 
            WHEN a.id IS NOT NULL THEN p.nome
            ELSE NULL
        END AS professor,
        CASE 
            WHEN a.id IS NOT NULL THEN d.nome
            ELSE NULL
        END AS disciplina
    FROM 
        salas s
    CROSS JOIN 
        horarios h
    LEFT JOIN 
        aulas a ON s.id = a.sala_id AND h.id = a.horario_id
    LEFT JOIN 
        professores p ON a.professor_id = p.id
    LEFT JOIN 
        disciplinas d ON a.disciplina_id = d.id
)

SELECT 
    sala_id,
    sala_nome,
    dia_semana,
    hora_inicio,
    hora_fim,
    status,
    professor,
    disciplina
FROM 
    todos_horarios
ORDER BY 
    sala_nome, 
    dia_semana, 
    hora_inicio;
