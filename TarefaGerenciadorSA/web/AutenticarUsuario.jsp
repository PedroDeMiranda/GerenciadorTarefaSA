<%@ page import="loginUser.Pessoa" %>
<%
    // Obter parâmetros de entrada do formulário
    String vEmail = request.getParameter("username");
    String vSenha = request.getParameter("password");

    // Cria o objeto Pessoa e configura o usuário e senha
    Pessoa pes = new Pessoa();
    pes.setUser(vEmail);
    pes.setSenha(vSenha);

    String permissao = null;
    
    try {
        // Chama o método de autenticação que agora retorna a permissão
        permissao = pes.Autenticar();
    } catch (ClassNotFoundException e) {
        // Em caso de erro de conexão ou outro erro específico
        out.println("<script>");
        out.println("alert('Erro ao tentar autenticar. Tente novamente mais tarde.');");
        out.println("window.location.href = 'index.html';");
        out.println("</script>");
        return;
    }

    // Verifica se a autenticação foi bem-sucedida
    if (permissao != null) {
        // Cria a sessão para o usuário autenticado
        session.setAttribute("usuario", vEmail); // Armazena o e-mail
        session.setAttribute("permissaoUsuario", permissao); // Armazena a permissão do usuário

        // Redireciona para a página inicial ou home
        response.sendRedirect("Home.html");
    } else {
        // Caso de erro, exibe um alerta informando que o usuário ou senha estão incorretos
        out.println("<script>");
        out.println("alert('Usuário ou senha incorretos! Por favor, tente novamente.');");
        out.println("window.location.href = 'index.html';");
        out.println("</script>");
    }
%>
