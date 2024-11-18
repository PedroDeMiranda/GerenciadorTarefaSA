<%@ page import="loginUser.Pessoa" %>
<%
    // Obter par�metros de entrada do formul�rio
    String vEmail = request.getParameter("username");
    String vSenha = request.getParameter("password");

    // Cria o objeto Pessoa e configura o usu�rio e senha
    Pessoa pes = new Pessoa();
    pes.setUser(vEmail);
    pes.setSenha(vSenha);

    String permissao = null;
    
    try {
        // Chama o m�todo de autentica��o que agora retorna a permiss�o
        permissao = pes.Autenticar();
    } catch (ClassNotFoundException e) {
        // Em caso de erro de conex�o ou outro erro espec�fico
        out.println("<script>");
        out.println("alert('Erro ao tentar autenticar. Tente novamente mais tarde.');");
        out.println("window.location.href = 'index.html';");
        out.println("</script>");
        return;
    }

    // Verifica se a autentica��o foi bem-sucedida
    if (permissao != null) {
        // Cria a sess�o para o usu�rio autenticado
        session.setAttribute("usuario", vEmail); // Armazena o e-mail
        session.setAttribute("permissaoUsuario", permissao); // Armazena a permiss�o do usu�rio

        // Redireciona para a p�gina inicial ou home
        response.sendRedirect("Home.html");
    } else {
        // Caso de erro, exibe um alerta informando que o usu�rio ou senha est�o incorretos
        out.println("<script>");
        out.println("alert('Usu�rio ou senha incorretos! Por favor, tente novamente.');");
        out.println("window.location.href = 'index.html';");
        out.println("</script>");
    }
%>
