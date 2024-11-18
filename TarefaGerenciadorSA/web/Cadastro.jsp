<%-- 
    Document   : Cadastro
    Created on : 29 de out. de 2024, 08:37:54
    Author     : pedro_miranda-neto
--%>
<%@page import="loginUser.Usuario"%>
<%@page import="loginUser.Desenvolvedor"%>
<%@page import="loginUser.Pessoa"%>
<%
    String vEmail  =  request.getParameter("email");
    String vSenha  =  request.getParameter("senha");
    String vFuncao =  request.getParameter("role");
    
    if ("Dev".equals(vFuncao)) {
        
        Desenvolvedor dev = new Desenvolvedor();
        dev.setFuncao(vFuncao);
        dev.setUser(vEmail);
        dev.setSenha(vSenha);
        
        String nome = request.getParameter("devName");
        dev.setNome(nome);
        
        if (dev.Cadastrar()) {
            out.println("<script>");
            out.println("alert('Desenvolvedor cadastrado');");
            out.println("window.location.href = 'index.html';");
            out.println("</script>");  
            
        } else {
            out.println("<script>");
            out.println("alert('Erro: Desenvolvedor não cadastrado');");
            out.println("window.location.href = 'CadastrarTela.html';");
            out.println("</script>");
        }
        
    } else if ("Usuario".equals(vFuncao)) {
        
        Usuario usu = new Usuario();
        
        usu.setFuncao(vFuncao);
        usu.setUser(vEmail);
        usu.setSenha(vSenha);
        
        String permissao = request.getParameter("analistaAdmin");
        usu.setPermissao(permissao);
        
        if (usu.Cadastrar()) {
            out.println("<script>");
            out.println("alert('Usuario cadastrado');");
            out.println("window.location.href = 'index.html';");
            out.println("</script>");  
            
        } else {
            out.println("<script>");
            out.println("alert('Erro: Usuario não cadastrado, esse nome ja está sendo ultilizado');");
            out.println("window.location.href = 'CadastrarTela.html';");
            out.println("</script>");
        }
        
    } else {
        out.println("<script>");
        out.println("alert('Algo deu Errado');");
        out.println("window.location.href = 'CadastrarTela.html';");
        out.println("</script>");
    }
%>
