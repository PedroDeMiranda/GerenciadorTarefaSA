package loginUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Conexao;

public class Pessoa {
    private int Id;
    private String Funcao;
    private String User;
    private String Senha;
   

   public String Autenticar() throws ClassNotFoundException {
    Connection con = Conexao.conectar();
    String permissao = null; // Inicialmente, permissao é null indicando falha de autenticação
    String sqlPessoa = "SELECT id, email, senha, Funcao FROM Pessoa WHERE email = ?";

    try {
        PreparedStatement stm = con.prepareStatement(sqlPessoa);
        stm.setString(1, this.getUser());
        ResultSet rs = stm.executeQuery();

        // Verifica se o usuário existe
        if (rs.next()) {
            // Obter dados do usuário no banco de dados
            int pessoaId = rs.getInt("id");
            String senhaBanco = rs.getString("senha");  // A senha armazenada no banco de dados
            String funcao = rs.getString("Funcao");

            // Verificar se a senha fornecida pelo usuário é igual à senha no banco de dados
            if (this.getSenha().equals(senhaBanco)) {
                // Senha correta, agora verificamos a função
                if ("Dev".equals(funcao)) {
                    permissao = "Dev";  // Se for Dev, a permissão é Dev
                } else if ("Usuario".equals(funcao)) {
                    // Caso contrário, verificar se é 'Admin' ou 'Analista'
                    String sqlUsuario = "SELECT permissao FROM usuario WHERE Pessoa_id = ?";
                    PreparedStatement stmUsuario = con.prepareStatement(sqlUsuario);
                    stmUsuario.setInt(1, pessoaId);
                    ResultSet rsUsuario = stmUsuario.executeQuery();

                    if (rsUsuario.next()) {
                        permissao = rsUsuario.getString("permissao");  // "Admin" ou "Analista"
                    }
                }
            } else {
                // Senha incorreta
                permissao = null;
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro na consulta do usuario: " + e.getMessage());
        return null;
    }

    return permissao;
}



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getFuncao() {
        return Funcao;
    }

    public void setFuncao(String funcao) {
        Funcao = funcao;
    }

    public String getEmail() {
        return User;
    }

    public void setEmail(String email) {
        User = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }
    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }
}
