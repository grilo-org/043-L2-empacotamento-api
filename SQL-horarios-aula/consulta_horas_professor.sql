-- Consulta 1: Quantidade de horas que cada professor tem comprometido em aulas

SELECT 
    p.id AS professor_id,
    p.nome AS professor_nome,
    SUM(TIMESTAMPDIFF(HOUR, h.hora_inicio, h.hora_fim)) AS horas_comprometidas
FROM 
    professores p
JOIN 
    aulas a ON p.id = a.professor_id
JOIN 
    horarios h ON a.horario_id = h.id
GROUP BY 
    p.id, p.nome
ORDER BY 
    p.nome;
