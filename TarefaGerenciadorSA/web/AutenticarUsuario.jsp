<%@page import="loginUser.Pessoa"%>
<%
    String vEmail =  request.getParameter("username");
    String vSenha =  request.getParameter("password");

    
    Pessoa pes = new Pessoa();
    pes.setUser(vEmail);
    pes.setSenha(vSenha);
    
    pes = pes.Autenticar();
    if (pes != null){
    
        // criar uma sess�o para o uus�rio que est� autenticado
        session.setAttribute("usuario", vEmail);
        response.sendRedirect("Home.html");
    } else {
    
        out.println("<script>");
        out.println("alert('Usu�rio ou senha incorretos! Por favor, tente novamente.');");
        out.println("window.location.href = 'index.html';");
        out.println("</script>");
    }


%>