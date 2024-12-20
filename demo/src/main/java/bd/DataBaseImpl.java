package bd;


import java.sql.*;

public class DataBaseImpl implements DataBase{
    private static final String url = "jdbc:mysql://localhost:3306/cours_java";
    private static final String username = "root";
    private static final String password = "";
    protected Connection connection;
    protected PreparedStatement statement;

    // Méthode pour ouvrir la connexion
    @Override
    public void openConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Méthode pour fermer la connexion
    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
@Override
public ResultSet executeSelect() throws SQLException {
    // Exécuter la requête SQL et retourner le ResultSet
    return statement.executeQuery();
}

@Override
public int executeUpdate() throws SQLException {
    // Exécuter la mise à jour SQL et retourner le nombre de lignes affectées
    return statement.executeUpdate();
}

@Override
public void initPreparedStatement(String sql) throws SQLException {
    // Ouvrir la connexion à la base de données
    this.openConnection();

    // Vérifier si la requête est une insertion pour utiliser RETURN_GENERATED_KEYS
    if (sql.trim().toLowerCase().startsWith("insert")) {
        // Préparer la requête avec retour des clés générées
        statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    } else {
        // Préparer la requête normalement pour les autres types de requêtes
        statement = connection.prepareStatement(sql);
    }
}
    
}
