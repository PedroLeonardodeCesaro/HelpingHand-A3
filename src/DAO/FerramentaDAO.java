package DAO;

import Model.Ferramenta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class FerramentaDAO {

    public FerramentaDAO() {
    }

    public int maiorID() throws SQLException {

        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT ID_ferramenta FROM tb_ferramentas ORDER BY ID_ferramenta DESC");
            res.next();
            maiorID = res.getInt("ID_ferramenta");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public Connection getConexao() {

        Connection connection = null;  //instância da conex�o

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

            // Testando...
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

    // Retorna a Lista de Ferramentas(objetos)
    public ArrayList visualizaFerramentas() {
        ArrayList<Ferramenta> visualizaFerramentas = new ArrayList<Ferramenta>();
        visualizaFerramentas.clear(); // Limpa nosso ArrayList

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas");
            while (res.next()) {

                int ID_ferramenta = res.getInt("ID_ferramenta");
                String nome_ferramenta = res.getString("nome_ferramenta");
                String marca = res.getString("marca");
                double custo_aquisicao = res.getDouble("custo_aquisicao");

                Ferramenta objeto = new Ferramenta(ID_ferramenta, nome_ferramenta, marca, custo_aquisicao);

                visualizaFerramentas.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return visualizaFerramentas;
    }

    public String VisualizaValorFerramentas() {
        ArrayList<Double> valoresFerramentas = new ArrayList<Double>();
        Double valorTotalGasto = 0.0;

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT custo_aquisicao FROM tb_ferramentas");

            while (res.next()) {
                double valor = res.getDouble("custo_aquisicao");
                valoresFerramentas.add(valor);
            }

            int i;
            for (i = 0; i < valoresFerramentas.size(); i++) {
                valorTotalGasto += valoresFerramentas.get(i);
            }

            stmt.close();

        } catch (SQLException erro) {
        }
        return valorTotalGasto.toString();
    }

    // Cadastra nova ferramenta
    public boolean InsertFerramentaDB(Ferramenta objeto) {
        String sql = "INSERT INTO tb_ferramentas(ID_ferramenta, nome_ferramenta, marca, custo_aquisicao) VALUES(?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getID_ferramenta());
            stmt.setString(2, objeto.getNome_ferramenta());
            stmt.setString(3, objeto.getMarca());
            stmt.setDouble(4, objeto.getCusto_aquisicao());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // Deleta uma ferramenta específica pelo seu campo ID
    public boolean DeleteFerramentaBD(int ID_ferramenta) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_ferramentas WHERE ID_ferramenta = " + ID_ferramenta);
            stmt.close();

        } catch (SQLException erro) {
        }

        return true;
    }

    // Edita uma ferramenta específica pelo seu campo ID
    public boolean UpdateFerramentaBD(Ferramenta objeto) {

        String sql = "UPDATE tb_ferramentas set nome_ferramenta = ? ,marca = ? ,custo_aquisicao = ? WHERE ID_ferramenta = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome_ferramenta());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getCusto_aquisicao());
            stmt.setInt(4, objeto.getID_ferramenta());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    //carrega dados
    public Ferramenta carregaFerramenta(int id) {

        Ferramenta objeto = new Ferramenta();
        objeto.setID_ferramenta(id);

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas WHERE ID_ferramenta = " + id);
            res.next();

            objeto.setID_ferramenta(res.getInt("ID_ferramenta"));
            objeto.setNome_ferramenta(res.getString("nome_ferramenta"));
            objeto.setMarca(res.getString("marca"));
            objeto.setCusto_aquisicao(res.getDouble("custo_aquisicao"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return objeto;
    }

    //Carrega ferramenta por nome e marca
    public Ferramenta buscaFerramentaByNomeMarca(String nome_ferramenta, String marca_ferramenta) {
        Ferramenta ferramenta = new Ferramenta();

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas WHERE nome_ferramenta = '" + nome_ferramenta + "' AND marca = '" + marca_ferramenta + "';");
            res.next();

            ferramenta.setID_ferramenta(res.getInt("ID_ferramenta"));
            ferramenta.setNome_ferramenta(nome_ferramenta);
            ferramenta.setMarca(marca_ferramenta);
            ferramenta.setCusto_aquisicao(res.getDouble("custo_aquisicao"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return ferramenta;
    }

}
