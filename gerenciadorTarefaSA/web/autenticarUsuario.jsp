<%@page import="loginUser.Usuario"%>
<%
    // Obtém os parâmetros "email" e "senha" da requisição
    String vEmail = request.getParameter("email");
    String vSenha = request.getParameter("senha");
    
    // Cria uma instância de Usuario e configura o email e senha
    Usuario usu = new Usuario();
    usu.setEmail(vEmail);
    usu.setSenha(vSenha);
    
    // Chama o método para autenticar o usuário
    usu = usu.autenticarUsuario();
    
    // Verifica se o usuário foi autenticado com sucesso
    if (usu != null) {
        session.setAttribute("usuario", vEmail);
        // Redireciona para a página "menu.jsp"
        response.sendRedirect("menu.jsp");
    } else {
        // Se não autenticado com sucesso, redireciona para a página "usuarioNaoCadastrado.html"
        response.sendRedirect("usuarioNaoCadastrado.html");
    }
%>
