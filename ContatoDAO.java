package brincandocombanco1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO implements ContatoInterface {

    // Método para adicionar um novo contato
    @Override
    public void adicionarContato(Contato contato) {
        String sql = "INSERT INTO contato (nome, email, telefone) VALUES(?, ?, ?)";

        try (Connection connection = ConexaoBanco.getConnection();  // Usa ConexaoBanco para obter a conexão
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            statement.setString(1, contato.getNome());
            statement.setString(2, contato.getEmail());
            statement.setString(3, contato.getTelefone());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        contato.setId(rs.getInt(1)); // Obtém o ID gerado e configura no objeto Contato
                        System.out.println("Contato inserido com ID: " + contato.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para listar todos os contatos
    @Override
    public List<Contato> listarContatos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT id_contato, nome, email, telefone FROM contato";

        try (Connection connection = ConexaoBanco.getConnection(); // Usa ConexaoBanco para obter a conexão
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("id_contato"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setTelefone(rs.getString("telefone"));
                contatos.add(contato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contatos;
    }

    // Método para deletar um contato pelo ID
    @Override
    public void deletarContato(int id) {
        String sql = "DELETE FROM contato WHERE id_contato = ?";

        try (Connection connection = ConexaoBanco.getConnection(); // Usa ConexaoBanco para obter a conexão
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Contato deletado com ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método para buscar contatos pelo nome
public List<Contato> buscarContatosPorNome(String nomeParcial) {
    List<Contato> contatos = new ArrayList<>();
    String sql = "SELECT id_contato, nome, email, telefone FROM contato WHERE nome LIKE ?";

    try (Connection connection = ConexaoBanco.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, "%" + nomeParcial + "%"); // Para busca parcial

        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("id_contato"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setTelefone(rs.getString("telefone"));
                contatos.add(contato);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return contatos;
}

}
