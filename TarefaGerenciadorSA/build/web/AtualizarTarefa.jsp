<%-- 
    Document   : AtualizarTarefa.jsp
    Created on : 7 de nov. de 2024, 09:36:22
    Author     : pedro_miranda-neto
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="loginUser.Tarefa"%>
<%@page import="java.sql.SQLException"%>
<%
    // Captura os parâmetros do formulário
    int id = Integer.parseInt(request.getParameter("id"));
    String descricao = request.getParameter("descricao");
    String prazoStr = request.getParameter("prazo");
    String status = request.getParameter("status");
    int tipo = Integer.parseInt(request.getParameter("tipoTarefa"));

    // Converte a string de data para `java.sql.Date`
    java.sql.Date prazo = null;
    if (prazoStr != null && !prazoStr.isEmpty()) {
        prazo = java.sql.Date.valueOf(prazoStr); // Usa `java.sql.Date` diretamente
    }

    Tarefa tar = new Tarefa();
    tar.setDescricao(descricao);
    tar.setPrazo(prazo);
    tar.setIdTipoTarefa(tipo);
    tar.setStatus(status);
    tar.setId(id);

    // Cadastra a tarefa e retorna a resposta ao usuário
    if (tar.Alterar()) {
        out.println("<script>");
        out.println("alert('Tarefa alterada com sucesso');");
        out.println("window.location.href = 'Tarefas.jsp';");
        out.println("</script>");
    } else {
        out.println("<script>");
        out.println("alert('Erro: Tarefa não alterada');");
        out.println("window.location.href = 'Tarefas.jsp';");
        out.println("</script>");
    }
%>



