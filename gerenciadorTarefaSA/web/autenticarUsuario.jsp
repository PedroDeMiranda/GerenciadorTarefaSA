<%@page import="loginUser.Usuario"%>
<%
    // Obt�m os par�metros "email" e "senha" da requisi��o
    String vEmail = request.getParameter("email");
    String vSenha = request.getParameter("senha");
    
    // Cria uma inst�ncia de Usuario e configura o email e senha
    Usuario usu = new Usuario();
    usu.setEmail(vEmail);
    usu.setSenha(vSenha);
    
    // Chama o m�todo para autenticar o usu�rio
    usu = usu.autenticarUsuario();
    
    // Verifica se o usu�rio foi autenticado com sucesso
    if (usu != null) {
        session.setAttribute("usuario", vEmail);
        // Redireciona para a p�gina "menu.jsp"
        response.sendRedirect("menu.jsp");
    } else {
        // Se n�o autenticado com sucesso, redireciona para a p�gina "usuarioNaoCadastrado.html"
        response.sendRedirect("usuarioNaoCadastrado.html");
    }
%>
