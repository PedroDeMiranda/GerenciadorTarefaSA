package loginUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Conexao;

public class Funcionario {
	private int        idFunc;
	private String     nomeFunc;
	private int        idDepto;
	private double     salHora;
	
	// Inclusão de Funcionario
		public boolean incluirFuncionario() throws ClassNotFoundException {
			String sql = "INSERT INTO funcionario (nomeFunc,idDepto,salHora) ";
				   sql+= "VALUES (?,?,?);";
			Connection con = Conexao.conectar();
			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setString(1, this.getNomeFunc());
				stm.setInt   (2, this.getIdDepto());
				stm.setDouble(3, this.getSalHora());
				stm.execute();
			} catch (SQLException e) {
				System.out.println("Erro na inclusão do departamento");
				return false;
			}
			return true;
		}

		// ALteração de Funcionario
		public boolean alterarFuncionario() throws ClassNotFoundException {
			String sql = "UPDATE funcionario ";
			       sql+= "SET nomeFunc = ?,idDepto = ?,salHora = ?) ";
				   sql+= "WHERE idfunc = ?";
			Connection con = Conexao.conectar();
			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setString(1, this.getNomeFunc());
				stm.setInt   (2, this.getIdDepto());
				stm.setDouble(3, this.getSalHora());
				stm.execute();
			} catch (SQLException e) {
				System.out.println("Erro na inclusão do departamento");
				return false;
			}
			return true;
		}		
		
		// listar todos os funcionarios
		public List<Funcionario> listarFuncionarios() throws ClassNotFoundException {
			List<Funcionario> listarFuncionarios = new ArrayList<>();
			Connection con = Conexao.conectar();
			String sql = "SELECT idfunc,nomeFunc,idDepto,salHora FROM funcionario order by idfunc;";
			try {
				PreparedStatement stm = con.prepareStatement(sql);
				ResultSet rs = stm.executeQuery();
				while (rs.next()) {
					Funcionario fun = new Funcionario();
					fun.setIdFunc(rs.getInt("idfunc"));
					fun.setNomeFunc(rs.getString("nomefunc"));
					fun.setIdDepto(rs.getInt("idDepto"));
					fun.setSalHora(rs.getDouble("salHora"));
					listarFuncionarios.add(fun);
				}
			} catch (SQLException e) {
				System.out.println("Erro na lista de funcionarios");
				return null;
			}
			return listarFuncionarios;
		}		
		// listar um funcionario
		public Funcionario consultaFuncionario() throws ClassNotFoundException {
			Connection con = Conexao.conectar();
			Funcionario fun = null;
			String sql = "SELECT nomeFunc,idDepto,salHora FROM funcionario where idfunc = ?";
			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setInt(1, this.getIdFunc());
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					fun = new Funcionario();
					fun.setNomeFunc(rs.getString("nomeFunc"));
					fun.setIdDepto(rs.getInt("idDepto"));
					fun.setSalHora(rs.getDouble("salHora"));
				}
			} catch (SQLException e) {
				System.out.println("Erro na consulta do funcionario");
				return null;
			}
			return fun;
		}
		
	// area de getters e setters
	public int getIdFunc() {
		return idFunc;
	}
	public void setIdFunc(int idFunc) {
		this.idFunc = idFunc;
	}
	public String getNomeFunc() {
		return nomeFunc;
	}
	public void setNomeFunc(String nomeFunc) {
		this.nomeFunc = nomeFunc;
	}
	public int getIdDepto() {
		return idDepto;
	}
	public void setIdDepto(int idDepto) {
		this.idDepto = idDepto;
	}
	public double getSalHora() {
		return salHora;
	}
	public void setSalHora(double salHora) {
		this.salHora = salHora;
	}
	
	
	
}
