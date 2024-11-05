package loginUser;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.Conexao;

public class Tarefa {

    private int Id;
    private String Descricao;
    private Date DataCriacao;
    private Date Prazo;
    private String Status;
    private int IdTipoTarefa;
    
    //Cadastrar
    public void Cadastrar(Tarefa tarefa) {
        String sql = "INSERT INTO tarefa (descricao, data_criacao, prazo, status, tipo_tarefa_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getDescricao());
            stmt.setDate(2, new java.sql.Date(tarefa.getDataCriacao().getTime()));
            stmt.setDate(3, new java.sql.Date(tarefa.getPrazo().getTime()));
            stmt.setString(4, tarefa.getStatus());
            stmt.setInt(5, tarefa.getIdTipoTarefa());
            stmt.executeUpdate();
            System.out.println("Tarefa cadastrada com sucesso.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao cadastrar tarefa: " + e.getMessage());
        }
    }
    
    //Conslutar
    public Tarefa Consultar(int id) {
        Tarefa tarefa = null;
        String sql = "SELECT * FROM tarefa WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setDescricao(rs.getString("descricao"));
                tarefa.setDataCriacao(rs.getDate("data_criacao"));
                tarefa.setPrazo(rs.getDate("prazo"));
                tarefa.setStatus(rs.getString("status"));
                tarefa.setIdTipoTarefa(rs.getInt("tipo_tarefa_id"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao consultar tarefa: " + e.getMessage());
        }
        return tarefa;
    }

    //Alterar
    public void Alterar(Tarefa tarefa) {
        String sql = "UPDATE tarefa SET descricao = ?, data_criacao = ?, prazo = ?, status = ?, tipo_tarefa_id = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tarefa.getDescricao());
            stmt.setDate(2, new java.sql.Date(tarefa.getDataCriacao().getTime()));
            stmt.setDate(3, new java.sql.Date(tarefa.getPrazo().getTime()));
            stmt.setString(4, tarefa.getStatus());
            stmt.setInt(5, tarefa.getIdTipoTarefa());
            stmt.setInt(6, tarefa.getId());
            stmt.executeUpdate();
            System.out.println("Tarefa atualizada com sucesso.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    //Excluir
    public void Excluir(int id) {
        String sql = "DELETE FROM tarefa WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Tarefa excluída com sucesso.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao excluir tarefa: " + e.getMessage());
        }
    }
    
    
    
    

    //getters e setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDataCriacao() {
        return DataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        DataCriacao = dataCriacao;
    }

    public Date getPrazo() {
        return Prazo;
    }

    public void setPrazo(Date prazo) {
        Prazo = prazo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getIdTipoTarefa() {
        return IdTipoTarefa;
    }

    public void setIdTipoTarefa(int idTipoTarefa) {
        IdTipoTarefa = idTipoTarefa;
    }
}
