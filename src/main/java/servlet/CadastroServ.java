package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ConexaoBD;
import dao.FuncionarioDAO;
import entity.FuncionarioEnty;

@WebServlet("/CadastroFuncionario")
public class CadastroServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    Connection conn = ConexaoBD.getConnection();
	    FuncionarioDAO funcDao = new FuncionarioDAO();
       
        try {
            FuncionarioEnty func = new FuncionarioEnty();
            int nextCode = getNextCode(conn);
            func.setCodigo(nextCode);
            func.setCodigo(funcDao.listarFuncionarios(conn).size() + 1);
            func.setNome(request.getParameter("nome"));
            func.setDescricaoCargo(request.getParameter("descCargo"));
            func.setCargo(request.getParameter("cargo")); 
            func.setSetor(request.getParameter("setor")); 
            String salarioStr = request.getParameter("salario");

         // Configurar o NumberFormat para tratar números com diferentes formatos
         NumberFormat format = NumberFormat.getInstance(java.util.Locale.getDefault());

         try {
             Number number = format.parse(salarioStr);
             double salario = number.doubleValue();
             func.setSalario(salario);
         } catch (ParseException e) {
             e.printStackTrace();
             // Tratamento de erro ao converter número
             func.setSalario(0.0);
         }
            funcDao.inserir(conn, func);

         // Chamada para listar funcionários usando funcDao
            List<FuncionarioEnty> listaFuncionarios = funcDao.listarFuncionarios(conn);

            // Adicionar o JSON como atributo do request
            String jsonFuncionarios = new Gson().toJson(listaFuncionarios);
            request.setAttribute("jsonFuncionarios", jsonFuncionarios);
            request.setAttribute("listaFuncionarios", listaFuncionarios);
            
            System.out.println("JSON Funcionarios: " + jsonFuncionarios);

        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de exceções
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Tratamento de exceções ao fechar a conexão
            }
        }

        RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/sucesso.jsp");
        rd.forward(request, response);
    }

	private int getNextCode(Connection conn) {
	    int nextCode = 0;

	    String sql = "SELECT MAX(codigo) + 1 FROM funcionario";

	    try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
	        if (rs.next()) {
	            nextCode = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Tratamento de exceções ao consultar o próximo código
	    }

	    return nextCode;
	}


    // Método listarFuncionarios
    public List<FuncionarioEnty> listarFuncionarios(Connection conn) throws SQLException {
        List<FuncionarioEnty> funcionarios = new ArrayList<>();

        String sql = "SELECT codigo, nome, cargo, descricaoCargo, setor, salario FROM funcionario";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FuncionarioEnty funcionario = new FuncionarioEnty();
                funcionario.setCodigo(rs.getInt("codigo"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setDescricaoCargo(rs.getString("descricaoCargo"));
                funcionario.setSetor(rs.getString("setor"));
                funcionario.setSalario(rs.getDouble("salario"));

                funcionarios.add(funcionario);
            }
        }

        return funcionarios;
    }
    
}
