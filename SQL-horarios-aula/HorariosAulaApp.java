package com.loja.horarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorariosAulaApp {

    // Configurações de conexão com o banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try {
            // Demonstração das consultas
            System.out.println("=== Horas comprometidas por professor ===");
            List<Map<String, Object>> horasProfessores = consultarHorasProfessores();
            for (Map<String, Object> professor : horasProfessores) {
                System.out.println(professor.get("professor_nome") + ": " + 
                                  professor.get("horas_comprometidas") + " horas");
            }

            System.out.println("\n=== Salas com horários livres e ocupados ===");
            List<Map<String, Object>> salasHorarios = consultarSalasHorarios();
            String salaAtual = "";
            String diaAtual = "";
            
            for (Map<String, Object> horario : salasHorarios) {
                String sala = (String) horario.get("sala_nome");
                String dia = (String) horario.get("dia_semana");
                
                if (!sala.equals(salaAtual)) {
                    System.out.println("\nSala: " + sala);
                    salaAtual = sala;
                    diaAtual = "";
                }
                
                if (!dia.equals(diaAtual)) {
                    System.out.println("  " + dia + ":");
                    diaAtual = dia;
                }
                
                System.out.println("    " + horario.get("hora_inicio") + " - " + 
                                 horario.get("hora_fim") + ": " + 
                                 horario.get("status") + 
                                 (horario.get("professor") != null ? 
                                  " (" + horario.get("professor") + " - " + horario.get("disciplina") + ")" : ""));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Consulta 1: Quantidade de horas que cada professor tem comprometido em aulas
     */
    public static List<Map<String, Object>> consultarHorasProfessores() throws Exception {
        List<Map<String, Object>> resultado = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            String sql = "SELECT " +
                         "    p.id AS professor_id, " +
                         "    p.nome AS professor_nome, " +
                         "    SUM(TIMESTAMPDIFF(HOUR, h.hora_inicio, h.hora_fim)) AS horas_comprometidas " +
                         "FROM " +
                         "    professores p " +
                         "JOIN " +
                         "    aulas a ON p.id = a.professor_id " +
                         "JOIN " +
                         "    horarios h ON a.horario_id = h.id " +
                         "GROUP BY " +
                         "    p.id, p.nome " +
                         "ORDER BY " +
                         "    p.nome";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("professor_id", rs.getInt("professor_id"));
                row.put("professor_nome", rs.getString("professor_nome"));
                row.put("horas_comprometidas", rs.getInt("horas_comprometidas"));
                resultado.add(row);
            }
        }
        
        return resultado;
    }

    /**
     * Consulta 2: Lista de salas com horários livres e ocupados
     */
    public static List<Map<String, Object>> consultarSalasHorarios() throws Exception {
        List<Map<String, Object>> resultado = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            String sql = "WITH todos_horarios AS ( " +
                         "    SELECT " +
                         "        s.id AS sala_id, " +
                         "        s.nome AS sala_nome, " +
                         "        h.id AS horario_id, " +
                         "        h.dia_semana, " +
                         "        h.hora_inicio, " +
                         "        h.hora_fim, " +
                         "        CASE " +
                         "            WHEN a.id IS NOT NULL THEN 'Ocupado' " +
                         "            ELSE 'Livre' " +
                         "        END AS status, " +
                         "        CASE " +
                         "            WHEN a.id IS NOT NULL THEN p.nome " +
                         "            ELSE NULL " +
                         "        END AS professor, " +
                         "        CASE " +
                         "            WHEN a.id IS NOT NULL THEN d.nome " +
                         "            ELSE NULL " +
                         "        END AS disciplina " +
                         "    FROM " +
                         "        salas s " +
                         "    CROSS JOIN " +
                         "        horarios h " +
                         "    LEFT JOIN " +
                         "        aulas a ON s.id = a.sala_id AND h.id = a.horario_id " +
                         "    LEFT JOIN " +
                         "        professores p ON a.professor_id = p.id " +
                         "    LEFT JOIN " +
                         "        disciplinas d ON a.disciplina_id = d.id " +
                         ") " +
                         "SELECT " +
                         "    sala_id, " +
                         "    sala_nome, " +
                         "    dia_semana, " +
                         "    hora_inicio, " +
                         "    hora_fim, " +
                         "    status, " +
                         "    professor, " +
                         "    disciplina " +
                         "FROM " +
                         "    todos_horarios " +
                         "ORDER BY " +
                         "    sala_nome, " +
                         "    dia_semana, " +
                         "    hora_inicio";
            
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("sala_id", rs.getInt("sala_id"));
                row.put("sala_nome", rs.getString("sala_nome"));
                row.put("dia_semana", rs.getString("dia_semana"));
                row.put("hora_inicio", rs.getTime("hora_inicio"));
                row.put("hora_fim", rs.getTime("hora_fim"));
                row.put("status", rs.getString("status"));
                row.put("professor", rs.getString("professor"));
                row.put("disciplina", rs.getString("disciplina"));
                resultado.add(row);
            }
        }
        
        return resultado;
    }
}
