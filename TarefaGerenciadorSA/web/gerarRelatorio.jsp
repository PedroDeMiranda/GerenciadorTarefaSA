<%@ page import="java.io.*, java.sql.*" %>
<%
    // Estabelece a conexão com o banco de dados
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    BufferedWriter writer = null;

    try {
        // Conectar ao banco de dados
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gerenciador_tarefas", "userGerenciador", "pass");
        
        // Consulta SQL para obter as tarefas sem considerar a tabela Pessoa_has_tarefa
        String sql = "SELECT t.id, t.descricao AS descricao_tarefa, tt.descricao AS tipo_tarefa, " +
                     "t.data_criacao, t.prazo, t.status " +
                     "FROM tarefa t " +
                     "JOIN tipo_tarefa tt ON t.tipo_tarefa_id = tt.id " +
                     "ORDER BY t.id";
        
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();

        // Configurar o tipo de resposta para download de arquivo CSV
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio_tarefas.csv");

        // Escrever o cabeçalho do CSV
        writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
        writer.write("ID, Descrição da Tarefa, Tipo de Tarefa, Data de Criação, Prazo, Status\n");

        // Escrever os dados das tarefas no CSV
        while (rs.next()) {
            int id = rs.getInt("id");
            String descricaoTarefa = rs.getString("descricao_tarefa");
            String tipoTarefa = rs.getString("tipo_tarefa");
            String dataCriacao = rs.getString("data_criacao");
            String prazo = rs.getString("prazo");
            String status = rs.getString("status");

            writer.write(id + "," + descricaoTarefa + "," + tipoTarefa + "," + dataCriacao + "," + prazo + "," + status + "\n");
        }

        writer.flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
            if (writer != null) writer.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
%>


