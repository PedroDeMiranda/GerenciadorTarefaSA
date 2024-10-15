package loginUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.Conexao;

public class Usuario {
    private int Id;
    private String permissao;
    private int PessoaId;

    
    // Inclusão de Usuario

    
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
