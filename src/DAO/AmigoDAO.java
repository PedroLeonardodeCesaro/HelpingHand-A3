package DAO;

import Model.Amigo;
import Model.Emprestimo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class AmigoDAO {

    public static ArrayList<Amigo> visualizaAmigos = new ArrayList<Amigo>();

    public AmigoDAO() {
    }

    public int maiorID() throws SQLException {
        int maiorID = 0;
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT ID_amigo FROM tb_amigos ORDER BY ID_amigo DESC");
            res.next();
            maiorID = res.getInt("ID_amigo");

            stmt.close();

        } catch (SQLException ex) {
        }

        return maiorID;
    }

    public Connection getConexao() {

        Connection connection = null;  //instancia da conexão

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
                System.out.println("Status: NÃO CONECTADO!");
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

    // Retorna a Lista de Amigoss(objetos)
    public ArrayList getVisualizaAmigos() {

        visualizaAmigos.clear(); // Limpa ArrayList

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos");
            while (res.next()) {

                int ID_amigo = res.getInt("ID_amigo");
                String nome_amigo = res.getString("nome_amigo");
                String telefone = res.getString("telefone");
                String email = res.getString("email");
                int quantidade_emprestimo = res.getInt("quantidade_emprestimo");

                Amigo objeto = new Amigo(ID_amigo, nome_amigo, telefone, email, quantidade_emprestimo);

                visualizaAmigos.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        }

        return visualizaAmigos;
    }

    // Cadastra novo amigo
    public boolean InsertAmigoDB(Amigo objeto) {
        String sql = "INSERT INTO tb_amigos(ID_amigo, nome_amigo, telefone, email, quantidade_emprestimo) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getID_amigo());
            stmt.setString(2, objeto.getNome_amigo());
            stmt.setString(3, objeto.getTelefone());
            stmt.setString(4, objeto.getEmail());
            stmt.setInt(5, objeto.getQuantidade_emprestimo());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    // Deleta um amigo especifico pelo seu campo ID
    public boolean DeleteAmigoBD(int ID_amigo) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_amigos WHERE ID_amigo = " + ID_amigo);
            stmt.close();

        } catch (SQLException erro) {
        }

        return true;
    }

    // Edita um amigo especifico pelo seu campo ID
    public boolean UpdateAmigoBD(Amigo objeto) {

        String sql = "UPDATE tb_amigos set nome_amigo = ? ,telefone = ? ,email = ? , quantidade_emprestimo = ? WHERE ID_amigo = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome_amigo());
            stmt.setString(2, objeto.getTelefone());
            stmt.setString(3, objeto.getEmail());
            stmt.setInt(4, objeto.getQuantidade_emprestimo());
            stmt.setInt(5, objeto.getID_amigo());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        }

    }

    //carrega dados
    public Amigo carregaAmigo(int id) {

        Amigo objeto = new Amigo();
        objeto.setID_amigo(id);

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos WHERE ID_amigo = " + objeto.getID_amigo());
            res.next();

            objeto.setNome_amigo(res.getString("nome_amigo"));
            objeto.setTelefone(res.getString("telefone"));
            objeto.setEmail(res.getString("email"));
            objeto.setQuantidade_emprestimo(res.getInt("quantidade_emprestimo"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return objeto;
    }

    public Amigo visualizar_locatario_que_mais_fez_emprestimos() {
        Amigo objeto = new Amigo();

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos ORDER BY quantidade_emprestimo DESC");
            res.next();

            objeto.setNome_amigo(res.getString("nome_amigo"));
            objeto.setID_amigo(res.getInt("ID_amigo"));
            objeto.setTelefone(res.getString("telefone"));
            objeto.setEmail(res.getString("email"));
            objeto.setQuantidade_emprestimo(res.getInt("quantidade_emprestimo"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return objeto;
    }

    public ArrayList<Amigo> visualizar_quem_nunca_devolveu_ferramenta() throws ParseException {
        ArrayList<Amigo> amigos = new ArrayList<Amigo>();
        AmigoDAO amigoDAO = new AmigoDAO();

        String date = new Date().toString();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        String data_atual = LocalDate.now().toString();

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT DISTINCT ID_amigo FROM tb_emprestimos WHERE data_devolucao < '" + data_atual + "' AND status_emprestimo = true;");
            while (res.next()) {

                int ID_amigo = res.getInt("ID_amigo");

                amigos.add(amigoDAO.carregaAmigo(ID_amigo));
            }        

            stmt.close();

        } catch (SQLException erro) {
        }
        return amigos;
    }

    public int BuscaAmigoByNameRetornandoID(String nome) {
        Amigo objeto = new Amigo();

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT ID_amigo FROM tb_amigos WHERE nome_amigo = '" + nome + "'");
            res.next();

            objeto.setID_amigo(res.getInt("ID_amigo"));

            stmt.close();

        } catch (SQLException erro) {
        }
        return objeto.getID_amigo();
    }
}
