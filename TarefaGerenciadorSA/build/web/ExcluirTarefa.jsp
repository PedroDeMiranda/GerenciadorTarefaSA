<%-- 
    Document   : ExcluirTarefa
    Created on : 12 de nov. de 2024, 10:17:37
    Author     : pedro_miranda-neto
--%>

<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="loginUser.Tarefa"%>
<%@page import="utils.Conexao"%>
<%
    // Captura o ID da tarefa a ser exclu�da  
    String idStr = request.getParameter("idTarefa");
               
    if (idStr != null && !idStr.isEmpty()) {  
       int id = Integer.parseInt(idStr);  

       // Chama o m�todo de exclus�o da tarefa  
       Tarefa tar = new Tarefa();  
       tar.setId(id);  

       if (tar.Excluir()) {  
          out.println("<script>");  
          out.println("alert('Tarefa exclu�da com sucesso');");  
          out.println("window.location.href = 'Tarefas.jsp';");  
          out.println("</script>");  
       } else {  
          out.println("<script>");  
          out.println("alert('Erro ao excluir tarefa');");  
          out.println("window.location.href = 'Tarefas.jsp';");  
          out.println("</script>");  
       }  
    } else {  
       out.println("<script>");  
       out.println("alert('Error: id � nulo ou vazio');");  
       out.println("window.location.href = 'Tarefas.jsp';");  
       out.println("</script>");  
    }
    %>
