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
   
    
// Cadastra Pessoa
    public void Cadastrar(Usuario usuario) {
        String sqlPessoa = "INSERT INTO pessoa (funcao, user, senha) VALUES (?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuario (permissao, pessoa_id) VALUES (?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Insere a pessoa
            stmtPessoa.setString(1, usuario.getFuncao());
            stmtPessoa.setString(2, usuario.getUser());
            stmtPessoa.setString(3, usuario.getSenha());
            stmtPessoa.executeUpdate();

            // Obtém o ID gerado da pessoa
            ResultSet generatedKeys = stmtPessoa.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pessoaId = generatedKeys.getInt(1);

                // Insere o usuario com o ID da pessoa
                try (PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario)) {
                    stmtUsuario.setString(1, usuario.getPermissao());
                    stmtUsuario.setInt(2, pessoaId);
                    stmtUsuario.executeUpdate();

                    System.out.println("Usuario adicionado com sucesso!");
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao adicionar usuario: " + ex.getMessage());
        }
    }

    // Alterar
    public void Alterar(int id, Pessoa novaPessoa) {
        String sql = "UPDATE pessoa SET funcao = ?, user = ?, senha = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, novaPessoa.getFuncao());
            stmt.setString(2, novaPessoa.getUser());
            stmt.setString(3, novaPessoa.getSenha());
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Pessoa atualizada com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao atualizar pessoa: " + ex.getMessage());
        }
    }

    // Excluir 
    public void Excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Pessoa deletada com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Erro ao deletar pessoa: " + ex.getMessage());
        }
    }
    
   public Pessoa Consultar(int pessoaId) {
    String sqlPessoa = "SELECT * FROM pessoa WHERE id = ?";
    String sqlUsuario = "SELECT permissao FROM usuario WHERE pessoa_id = ?";
    String sqlDesenvolvedor = "SELECT nome FROM desenvolvedor WHERE pessoa_id = ?";
    Pessoa pessoa = null;

    try (Connection con = Conexao.conectar();
         PreparedStatement stmtPessoa = con.prepareStatement(sqlPessoa);
         PreparedStatement stmtUsuario = con.prepareStatement(sqlUsuario);
         PreparedStatement stmtDesenvolvedor = con.prepareStatement(sqlDesenvolvedor)) {

        // Consulta na tabela pessoa
        stmtPessoa.setInt(1, pessoaId);
        ResultSet rsPessoa = stmtPessoa.executeQuery();
        
        if (rsPessoa.next()) {
            pessoa = new Pessoa();
            pessoa.setId(rsPessoa.getInt("id"));
            pessoa.setFuncao(rsPessoa.getString("funcao"));
            pessoa.setUser(rsPessoa.getString("email"));
            pessoa.setSenha(rsPessoa.getString("senha"));

            // Verifica se a pessoa é um Usuario
            stmtUsuario.setInt(1, pessoaId);
            ResultSet rsUsuario = stmtUsuario.executeQuery();
            if (rsUsuario.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(pessoa.getId());
                usuario.setFuncao(pessoa.getFuncao());
                usuario.setUser(pessoa.getUser());
                usuario.setSenha(pessoa.getSenha());
                usuario.setPermissao(rsUsuario.getString("permissao"));
                System.out.println("Pessoa Usuario: " + usuario.getUser() + " - " + usuario.getPermissao());
                return usuario;
            }

            // Verifica se a pessoa é um Desenvolvedor
            stmtDesenvolvedor.setInt(1, pessoaId);
            ResultSet rsDesenvolvedor = stmtDesenvolvedor.executeQuery();
            if (rsDesenvolvedor.next()) {
                Desenvolvedor desenvolvedor = new Desenvolvedor();
                desenvolvedor.setId(pessoa.getId());
                desenvolvedor.setFuncao(pessoa.getFuncao());
                desenvolvedor.setUser(pessoa.getUser());
                desenvolvedor.setSenha(pessoa.getSenha());
                desenvolvedor.setNome(rsDesenvolvedor.getString("nome"));
                System.out.println("Pessoa Desenvolvedor: " + desenvolvedor.getUser() + " - " + desenvolvedor.getNome());
                return desenvolvedor;
            }

            System.out.println("Pessoa não é um Usuario nem um Desenvolvedor.");
        } else {
            System.out.println("Pessoa com ID " + pessoaId + " não encontrada.");
        }

    } catch (SQLException | ClassNotFoundException ex) {
        System.out.println("Erro ao buscar pessoa: " + ex.getMessage());
    }

    return pessoa;
}
   
   public Pessoa Autenticar() throws ClassNotFoundException {
        Connection con = Conexao.conectar();
        Pessoa pes = null;
        String sql = "SELECT email FROM Pessoa WHERE email = ? AND senha = ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, this.getUser());
            stm.setString(2, this.getSenha());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                pes = new Pessoa();
                pes.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
                System.out.println("Erro na consulta do usuario");
                return null;
        }
        return pes;
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
