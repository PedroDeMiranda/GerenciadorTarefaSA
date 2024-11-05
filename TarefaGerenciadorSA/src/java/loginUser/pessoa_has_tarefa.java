package loginUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Conexao;

public class pessoa_has_tarefa {
    private int PessoaId;
    private int TarefaId;

    
    // Método para associar uma tarefa a uma pessoa
    public void Cadastrar() {
        String sql = "INSERT INTO Pessoa_has_tarefa (Pessoa_id, tarefa_id) VALUES (?, ?)";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setInt(1, PessoaId);
            stmt.setInt(2, TarefaId);
            stmt.executeUpdate();
            System.out.println("Tarefa associada à pessoa com sucesso.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao associar tarefa: " + e.getMessage());
        }
    }

    // Método para desassociar uma tarefa de uma pessoa
    public void Excluir() {
        String sql = "DELETE FROM Pessoa_has_tarefa WHERE Pessoa_id = ? AND tarefa_id = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setInt(1, PessoaId);
            stmt.setInt(2, TarefaId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Associação removida com sucesso.");
            } else {
                System.out.println("Nenhuma associação encontrada com os IDs especificados.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao remover associação: " + e.getMessage());
        }
    }

    // Método para consultar todas as tarefas de uma pessoa
    public static void Consultar_Por_Pessoa(int pessoaId) {
        String sql = "SELECT tarefa_id FROM Pessoa_has_tarefa WHERE Pessoa_id = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setInt(1, pessoaId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Tarefas associadas ao Pessoa ID " + pessoaId + ":");
            while (rs.next()) {
                int tarefaId = rs.getInt("tarefa_id");
                System.out.println(" - Tarefa ID: " + tarefaId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao consultar tarefas: " + e.getMessage());
        }
    }

    // Método para consultar todas as pessoas associadas a uma tarefa
    public static void Consultar_Por_Tarefa(int tarefaId) {
        String sql = "SELECT Pessoa_id FROM Pessoa_has_tarefa WHERE tarefa_id = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
             
            stmt.setInt(1, tarefaId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Pessoas associadas à Tarefa ID " + tarefaId + ":");
            while (rs.next()) {
                int pessoaId = rs.getInt("Pessoa_id");
                System.out.println(" - Pessoa ID: " + pessoaId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao consultar pessoas: " + e.getMessage());
        }
    }
    
    
    
    
    public int getPessoaId() {
        return PessoaId;
    }

    public void setPessoaId(int pessoaId) {
        PessoaId = pessoaId;
    }

    public int getTarefaId() {
        return TarefaId;
    }

    public void setTarefaId(int tarefaId) {
        TarefaId = tarefaId;
    }
}
