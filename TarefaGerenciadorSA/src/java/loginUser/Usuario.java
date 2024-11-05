package loginUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Conexao;

public class Usuario extends Pessoa{
    private int Id;
    private String permissao;
    private int PessoaId;

    public void Cadastrar(Usuario usuario) {
    String sqlPessoa = "INSERT INTO pessoa (funcao, email, senha) VALUES (?, ?, ?)";
    String sqlUsuario = "INSERT INTO usuario (permissao, pessoa_id) VALUES (?, ?)";
    
    try (Connection con = Conexao.conectar();
         PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS)) {
         
        // Insere a pessoa
        stmtPessoa.setString(1, usuario.getFuncao());
        stmtPessoa.setString(2, usuario.getUser()); // Corrigido para getEmail()
        stmtPessoa.setString(3, usuario.getSenha());
        stmtPessoa.executeUpdate();

        // Obtém o ID gerado da pessoa
        ResultSet generatedKeys = stmtPessoa.getGeneratedKeys();
        if (generatedKeys.next()) {
            int pessoaId = generatedKeys.getInt(1); // O ID gerado
            
            // Insere o usuario com o ID da pessoa
            try (PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, usuario.getPermissao()); // Verifique se `permissao` é correto aqui
                stmtUsuario.setInt(2, pessoaId);
                stmtUsuario.executeUpdate();
                
                System.out.println("Usuario adicionado com sucesso!");
            }
        }
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println("Erro ao adicionar usuario: " + ex.getMessage());
            }
}

    public void Alterar(Usuario usuario, int idAlterar) {
        String sqlPessoa = "UPDATE pessoa SET funcao = ?, email = ?, senha = ? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET permissao = ? WHERE pessoa_id = ?";
        try (Connection con = Conexao.conectar()) {
            // Atualiza a pessoa
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, usuario.getFuncao());
            stmtPessoa.setString(2, usuario.getUser());
            stmtPessoa.setString(3, usuario.getSenha());
            stmtPessoa.setInt(4, idAlterar);
            stmtPessoa.executeUpdate();

            // Atualiza o usuario
            PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, usuario.getPermissao());
            stmtUsuario.setInt(2, idAlterar); // ID da pessoa
            stmtUsuario.executeUpdate();

            System.out.println("Usuario atualizado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao atualizar usuario: " + ex.getMessage());
        }
    }

    // Excluir
    public void Excluir(int pessoaId) {
        String sqlUsuario = "DELETE FROM usuario WHERE pessoa_id = ?";
        String sqlPessoa = "DELETE FROM pessoa WHERE id = ?";
        try (Connection con = Conexao.conectar()) {
            // Primeiro, deleta o usuario
            PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario);
            stmtUsuario.setInt(1, pessoaId);
            stmtUsuario.executeUpdate();

            // Depois, deleta a pessoa
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, pessoaId);
            stmtPessoa.executeUpdate();

            System.out.println("Usuario deletado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao deletar usuario: " + ex.getMessage());
        }
    }
   
    
    //getters e setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public int getPessoaId() {
        return PessoaId;
    }

    public void setPessoaId(int pessoaId) {
        PessoaId = pessoaId;
    }
}
