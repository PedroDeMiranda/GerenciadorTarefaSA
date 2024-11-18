<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<%
    String permissaoUsuario = (String) session.getAttribute("permissaoUsuario");
%>
<!DOCTYPE html> 
<html>  
<head>
    <link rel="shortcut icon" href="imagens/logo.png">
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet"/>
</head>
<body class="bg-white">
    <nav class="bg-blue-600 p-4 flex justify-between items-center">
        <div class="flex items-center">
            <img alt="Logo" class="h-10" height="50" src="imagens/logo.png" width="40"/>
        </div>
        <div class="flex items-center space-x-8">
            <a class="text-white" href="Tarefas.jsp">Tarefas</a>
            <a class="text-white" href="Home.html">Home</a>
        </div>
        <div class="flex items-center">
            <span class="text-white mr-2"><%= permissaoUsuario != null ? permissaoUsuario : "Usuário não autenticado" %></span>
            <i class="fas fa-user-circle text-white text-2xl"></i>
        </div>
    </nav>

    <div class="container mx-auto mt-10 text-center">
        <h1 class="text-3xl font-bold mb-8">Colaboradores</h1>

        <!-- Usando grid para organizar os cartões -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <%
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gerenciador_tarefas", "userGerenciador", "pass");

                    String query = "SELECT p.email, p.Funcao, COALESCE(u.permissao, 'Desenvolvedor') AS permissao " +
                                   "FROM Pessoa p " +
                                   "LEFT JOIN usuario u ON p.id = u.Pessoa_id";

                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        String email = rs.getString("email");
                        String funcao = rs.getString("Funcao");
                        String permissao = rs.getString("permissao");

                        String permissaoExibida = permissao.equals("Admin") ? "Adm" : permissao;
            %>
                        <div class="border-2 border-black rounded-lg p-4 flex flex-col items-center">
                            <img alt="Usuário <%= permissaoExibida %>" class="mx-auto mb-4" height="100" src="imagens/user.png" width="100"/>
                            <p class="font-semibold"><%= email %></p>
                            <p class="text-gray-500"><%= permissaoExibida %></p>
                        </div>
            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
            %>
                    <p>Erro ao carregar colaboradores.</p>
            <%
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (ps != null) ps.close();
                        if (con != null) con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            %>
        </div>
    </div>
</body>
</html>