package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.FuncionarioEnty;

public class FuncionarioDAO {
	public List<FuncionarioEnty> listarFuncionarios(Connection conn) throws SQLException {
	    StringBuffer sql = new StringBuffer();
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    FuncionarioEnty func = null;
	    List<FuncionarioEnty> lista = new ArrayList<FuncionarioEnty>();

	    try {
	        sql.append("SELECT * FROM FUNCIONARIO ");
	        sql.append("ORDER BY CODIGO ");
	        ps = conn.prepareStatement(sql.toString());
	        rs = ps.executeQuery();

	        while (rs.next()) {
	            func = new FuncionarioEnty();
	            func.setCodigo(rs.getInt("CODIGO"));
	            func.setNome(rs.getString("NOME").trim());
	            func.setCargo(rs.getString("CARGO"));
	            func.setDescricaoCargo(rs.getString("DESCRICAOCARGO"));
	            func.setSetor(rs.getString("SETOR"));
	            func.setSalario(rs.getDouble("SALARIO"));
	            lista.add(func);
	        }
	    }catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("(listarFuncionarios) - Erro: " + e.getMessage());
	    }finally{
	    try{ps.close();}catch(SQLException e){}
	    if (rs != null){
	    try{rs.close();}catch(SQLException e){}
	    }
	    ps = null;
	    rs = null;
	    sql = null;
	    }
	    return lista;
	    }


	public void inserir(Connection conn, FuncionarioEnty funcionario) throws SQLException{
		StringBuffer sql = new StringBuffer();
		PreparedStatement ps = null;
		try{
		int qtdeGravados = listarFuncionarios(conn).size(); // 
		qtdeGravados++;
		sql.delete(0, sql.length());
		sql.append("INSERT INTO FUNCIONARIO (CODIGO, NOME, CARGO,DESCRICAOCARGO, SETOR, SALARIO) ");
		sql.append(" VALUES (?, ?, ?, ?, ?, ?) ");
		ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, qtdeGravados);
		ps.setString(2, funcionario.getNome());
		ps.setString(3, funcionario.getCargo());
		ps.setString(4, funcionario.getDescricaoCargo());
		ps.setString(5, funcionario.getSetor());
		ps.setDouble(6, funcionario.getSalario());
		ps.executeUpdate();
		}finally{
		try{ps.close();ps = null;}catch(SQLException e){}
		ps = null;
		sql = null;
		}
		}

    public void alterar(List<FuncionarioEnty> lista, FuncionarioEnty funcionario) {
        for (FuncionarioEnty f : lista) {
            if (f.getCodigo() == funcionario.getCodigo()) {
                f.setNome(funcionario.getNome());
                f.setCargo(funcionario.getCargo());
                f.setDescricaoCargo(funcionario.getDescricaoCargo());
                f.setSetor(funcionario.getSetor());
                f.setSalario(funcionario.getSalario());
            }
        }
    }

    public void excluir(List<FuncionarioEnty> lista, FuncionarioEnty funcionario) {
        lista.removeIf(f -> f.getCodigo() == funcionario.getCodigo());
    }
}