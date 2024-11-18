<%-- 
    Document   : adicionarTarefa
    Created on : 5 de nov. de 2024, 10:35:14
    Author     : pedro_miranda-neto
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="loginUser.Tarefa"%>
<%@page import="java.sql.SQLException"%>
<%
    String descricao = request.getParameter("descricao");
    String prazoStr = request.getParameter("prazo");
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

    // Cadastra a tarefa e retorna a resposta ao usuário
    if (tar.Cadastrar()) {
        out.println("<script>");
        out.println("alert('Tarefa cadastrada com sucesso');");
        out.println("window.location.href = 'Tarefas.jsp';");
        out.println("</script>");
    } else {
        out.println("<script>");
        out.println("alert('Erro: Tarefa não cadastrada');");
        out.println("window.location.href = 'Tarefas.jsp';");
        out.println("</script>");
    }
%>





