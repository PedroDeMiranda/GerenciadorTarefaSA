package loginUser;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Conexao;

public class Desenvolvedor extends Pessoa{
    private int Id;
    private String Nome;
    private int PessoaId;

    // Cadastrar ok
    public boolean Cadastrar() throws ClassNotFoundException {
    String sqlPessoa = "INSERT INTO pessoa (funcao, email, senha) VALUES (?, ?, ?)";
    String sqlDesenvolvedor = "INSERT INTO desenvolvedor (nome, pessoa_id) VALUES (?, ?)";

    Connection con = Conexao.conectar();
    
    try {
        // Preparação e execução da inserção na tabela "pessoa"
        PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
        stmtPessoa.setString(1, this.getFuncao());
        stmtPessoa.setString(2, this.getUser());
        stmtPessoa.setString(3, this.getSenha());
        stmtPessoa.executeUpdate();

        // Obtém o ID gerado automaticamente para "pessoa"
        ResultSet generatedKeys = stmtPessoa.getGeneratedKeys();
        if (generatedKeys.next()) {
            int pessoaId = generatedKeys.getInt(1); // Obtenção do ID

            // Preparação e execução da inserção na tabela "desenvolvedor"
            PreparedStatement stmtDesenvolvedor = con.prepareStatement(sqlDesenvolvedor);
            stmtDesenvolvedor.setString(1, this.getNome());
            stmtDesenvolvedor.setInt(2, pessoaId); // Usando o ID gerado
            stmtDesenvolvedor.executeUpdate();

            System.out.println("Desenvolvedor adicionado com sucesso!");
        } else {
            System.out.println("Erro ao obter o ID da pessoa.");
            return false;
        }

    } catch (SQLException e) {
        System.out.println("Erro na inclusão do desenvolvedor: " + e.getMessage());
        return false;
    }
    return true;
}


    // Alterar
    public void Alterar(int id, Desenvolvedor novoDesenvolvedor) {
        String sqlPessoa = "UPDATE pessoa SET funcao = ?, email = ?, senha = ? WHERE id = ?";
        String sqlDesenvolvedor = "UPDATE desenvolvedor SET nome = ? WHERE pessoa_id = ?";
        try (Connection con = Conexao.conectar()) {
            // Atualiza a pessoa
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setString(1, novoDesenvolvedor.getFuncao());
            stmtPessoa.setString(2, novoDesenvolvedor.getUser());
            stmtPessoa.setString(3, novoDesenvolvedor.getSenha());
            stmtPessoa.setInt(4, id);
            stmtPessoa.executeUpdate();

            // Atualiza o desenvolvedor
            PreparedStatement stmtDesenvolvedor = con.prepareStatement(sqlDesenvolvedor);
            stmtDesenvolvedor.setString(1, novoDesenvolvedor.getNome());
            stmtDesenvolvedor.setInt(2, novoDesenvolvedor.getId()); // ID da pessoa
            stmtDesenvolvedor.executeUpdate();

            System.out.println("Desenvolvedor atualizado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao atualizar desenvolvedor: " + ex.getMessage());
        }
    }

    // Excluir
    public void Excluir(int pessoaId) {
        String sqlDesenvolvedor = "DELETE FROM desenvolvedor WHERE pessoa_id = ?";
        String sqlPessoa = "DELETE FROM pessoa WHERE id = ?";
        try (Connection con = Conexao.conectar()) {
            // Primeiro, deleta o desenvolvedor
            PreparedStatement stmtDesenvolvedor = con.prepareStatement(sqlDesenvolvedor);
            stmtDesenvolvedor.setInt(1, pessoaId);
            stmtDesenvolvedor.executeUpdate();

            // Depois, deleta a pessoa
            PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, pessoaId);
            stmtPessoa.executeUpdate();

            System.out.println("Desenvolvedor deletado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao deletar desenvolvedor: " + ex.getMessage());
        }
    }

    //getters e setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getPessoaId() {
        return PessoaId;
    }

    public void setPessoaId(int pessoaId) {
        PessoaId = pessoaId;
    }
}
