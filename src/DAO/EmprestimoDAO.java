package DAO;

import Model.Amigo;
import Model.Emprestimo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class EmprestimoDAO {

    public static ArrayList<Emprestimo> visualizaEmprestimos = new ArrayList<Emprestimo>();

    public EmprestimoDAO() {
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT ID_emprestimo FROM tb_emprestimos ORDER BY ID_emprestimo DESC");
            res.next();
            maiorID = res.getInt("ID_emprestimo");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID + 1;
    }

    public Connection getConexao() {

        Connection connection = null;  //instância da conexão

        try {

            // Carregamento do JDBC Driver
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            // Configurar a conexão
            String server = "localhost"; //caminho do MySQL
            String database = "db_gerenciador";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "mysql";

            connection = DriverManager.getConnection(url, user, password);

            // Testando..
            if (connection != null) {
                System.out.println("Status: Conectado!");
            } else {
                System.out.println("Status: N�O CONECTADO!");
            }

            return connection;

        } catch (ClassNotFoundException e) {  //Driver não encontrado
            System.out.println("O driver nao foi encontrado. " + e.getMessage());
            return null;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
    }

    // Retorna a Lista de Emprestimos(objetos)
    public ArrayList visualizaEmprestimos() {

        visualizaEmprestimos.clear(); // Limpa nosso ArrayList

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos");
            while (res.next()) {

                int ID_emprestimo = res.getInt("ID_emprestimo");
                int ID_amigo = res.getInt("ID_amigo");
                int ID_ferramenta = res.getInt("ID_ferramenta");
                String data_ocorreu = res.getString("data_ocorreu");
                String data_devolucao = res.getString("data_devolucao");

                Emprestimo objeto = new Emprestimo(ID_emprestimo, ID_amigo, ID_ferramenta, data_ocorreu, data_devolucao);

                visualizaEmprestimos.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return visualizaEmprestimos;
    }

    // Cadastra novo emprestimo
    public boolean InsertEmprestimoDB(Emprestimo objeto) {
        String sql = "INSERT INTO tb_emprestimos(ID_emprestimo, ID_amigo, ID_ferramenta, data_ocorreu, data_devolucao, status_emprestimo) VALUES(?,?,?,?,?,?)";
        AmigoDAO amigoM = new AmigoDAO();
        Amigo amigo = new Amigo();

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, this.maiorID());
            stmt.setInt(2, objeto.getID_amigo());
            stmt.setInt(3, objeto.getID_ferramenta());
            stmt.setString(4, objeto.getData_ocorreu());
            stmt.setString(5, objeto.getData_devolucao());
            stmt.setBoolean(6, objeto.getStatus_emprestimo());

            stmt.execute();
            stmt.close();

            amigo = amigoM.carregaAmigo(objeto.getID_amigo());
                          
            int qntdEmprestimos = amigo.getQuantidade_emprestimo() + 1;
            amigo.setQuantidade_emprestimo(qntdEmprestimos);
            
            amigoM.UpdateAmigoBD(amigo);

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // Deleta um emprestimo especifico pelo seu campo ID
    public boolean DeleteEmprestimoBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_emprestimos WHERE ID_emprestimo = " + id);
            stmt.close();

        } catch (SQLException erro) {
        }

        return true;
    }

    // Edita um emprestimo especifico pelo seu campo ID
    public boolean UpdateEmprestimoBD(Emprestimo objeto) {

        String sql = "UPDATE tb_emprestimos set ID_emprestimo = ? ,ID_amigo = ? ,ID_ferramenta = ? ,data_ocorreu = ? ,data_devolucao = ?, status_emprestimo = ? WHERE ID_emprestimo = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getID_emprestimo());
            stmt.setInt(2, objeto.getID_amigo());
            stmt.setInt(3, objeto.getID_ferramenta());
            stmt.setString(4, objeto.getData_ocorreu());
            stmt.setString(5, objeto.getData_devolucao());
            stmt.setBoolean(6, objeto.getStatus_emprestimo());
            stmt.setInt(7, objeto.getID_emprestimo());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    //carrega dados
    public Emprestimo carregaEmprestimo(int id) {

        Emprestimo objeto = new Emprestimo();
        objeto.setID_emprestimo(id);

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos WHERE ID_emprestimo = " + id);
            res.next();

            objeto.setID_emprestimo(res.getInt("ID_emprestimo"));
            objeto.setID_amigo(res.getInt("ID_amigo"));
            objeto.setID_ferramenta(res.getInt("ID_ferramenta"));
            objeto.setData_ocorreu(res.getString("data_ocorreu"));
            objeto.setData_devolucao(res.getString("data_devolucao"));
            objeto.setStatus_emprestimo(res.getBoolean("status_emprestimo"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return objeto;
    }
}
