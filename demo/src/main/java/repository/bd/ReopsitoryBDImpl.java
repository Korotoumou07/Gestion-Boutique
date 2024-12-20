package repository.bd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.lang.reflect.Method;
import bd.DataBaseImpl;
import entites.User;
import entites.Article;
import entites.Client;
import entites.Demande;
import entites.Detail;
import entites.DetailAD;
import entites.Dette;
import entites.Payment;
import entites.Role;
import entites.Statut;
import repository.Repository;
import java.sql.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public class ReopsitoryBDImpl<T> extends DataBaseImpl implements Repository<T> {



private Class<T> entityClass;

    public ReopsitoryBDImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }









    

    @Override
    public void insert(T data) {
        openConnection();
        int generatedId = 0;
        try {
            // Générer dynamiquement la requête SQL d'insertion
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(entityClass.getSimpleName().toLowerCase()).append(" (");
    
            Field[] fields = entityClass.getDeclaredFields();
            String primaryKeyField = "id"; // Supposons que le champ auto-incrémenté est "id"
            boolean firstField = true;
    
            // Construire les noms des colonnes
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                
                // Ignorer le champ auto-incrémenté et les champs de type List
                if (fieldName.equalsIgnoreCase(primaryKeyField) || List.class.isAssignableFrom(fields[i].getType())) {
                    continue;
                }
                if (!firstField) {
                    sql.append(", ");
                }
    
                // Si c'est une clé étrangère, on utilise le nom de la colonne avec "_id"
                if (isForeignKeyField(fields[i])) {
                    sql.append(fieldName.toLowerCase()).append("_id");
                } else {
                    sql.append(fieldName);
                }
                firstField = false;
            }
            sql.append(") VALUES (");
    
            // Construire les valeurs
            firstField = true;
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                
                // Ignorer le champ auto-incrémenté et les champs de type List
                if (fieldName.equalsIgnoreCase(primaryKeyField) || List.class.isAssignableFrom(fields[i].getType())) {
                    continue;
                }
                if (!firstField) {
                    sql.append(", ");
                }
                sql.append("?");
                firstField = false;
            }
            sql.append(")");
    
            // Préparer la déclaration
            statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
    
            // Map pour récupérer les entités étrangères
            Map<Class<?>, Function<Integer, ?>> entityFetchers = new HashMap<>();
            entityFetchers.put(Article.class, id -> findEntityById(Article.class, id));
            entityFetchers.put(Client.class, id -> findEntityById(Client.class, id));
            entityFetchers.put(Demande.class, id -> findEntityById(Demande.class, id));
            entityFetchers.put(DetailAD.class, id -> findEntityById(DetailAD.class, id));
            entityFetchers.put(Detail.class, id -> findEntityById(Detail.class, id));
            entityFetchers.put(Dette.class, id -> findEntityById(Dette.class, id));
            entityFetchers.put(Payment.class, id -> findEntityById(Payment.class, id));
            entityFetchers.put(User.class, id -> findEntityById(User.class, id));
    
            // Assignation des valeurs
            int parameterIndex = 1;
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                String fieldName = fields[i].getName();
                
                // Ignorer le champ auto-incrémenté et les champs de type List
                if (fieldName.equalsIgnoreCase(primaryKeyField) || List.class.isAssignableFrom(fields[i].getType())) {
                    continue;
                }
                Object fieldValue = fields[i].get(data);
                Class<?> fieldType = fields[i].getType();
    
                Integer foreignKeyValue = null;
    
                if (fieldValue != null && entityFetchers.containsKey(fieldType)) {
                    // Si c'est une clé étrangère avec une valeur non nulle
                    foreignKeyValue = (Integer) fieldType.getMethod("getId").invoke(fieldValue);
                    statement.setObject(parameterIndex++, foreignKeyValue);
                } else if (entityFetchers.containsKey(fieldType)) {
                    // Si c'est une clé étrangère mais que la valeur est null
                    statement.setNull(parameterIndex++, Types.INTEGER);
                } else {
                    // Gestion des autres types de champs
                    if (fieldValue == null) {
                        statement.setNull(parameterIndex++, java.sql.Types.NULL);
                    } else {
                        if (fieldValue instanceof Role) {
                            fieldValue = fieldValue.toString(); // Convertir l'objet Role en chaîne
                        }else if (fieldValue instanceof Statut) {
                            fieldValue = fieldValue.toString();
                        }
                        
                        statement.setObject(parameterIndex++, fieldValue); // Insérer la valeur
                    }
                }
            }
    
            // Exécuter l'insertion
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
    
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1); // Récupérer l'ID généré
                
                // Supposons que toutes les classes ont une méthode setId(int id)
                try {
                    Method setIdMethod = data.getClass().getMethod("setId", int.class);
                    setIdMethod.invoke(data, generatedId);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
    
        } catch (SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    
    





    @Override
public void update(T data) {
    openConnection();
    try {
        // Générer dynamiquement la requête SQL de mise à jour
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(entityClass.getSimpleName().toLowerCase()).append(" SET ");

        Field[] fields = entityClass.getDeclaredFields();
        String primaryKeyField = "id"; // Supposons que le champ clé primaire est "id"
        boolean firstField = true;

        // Construire les noms des colonnes pour la mise à jour
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();

            // Ignorer le champ auto-incrémenté et les champs de type List
            if (fieldName.equalsIgnoreCase(primaryKeyField) || List.class.isAssignableFrom(fields[i].getType())) {
                continue;
            }
            if (!firstField) {
                sql.append(", ");
            }

            // Si c'est une clé étrangère, on utilise le nom de la colonne avec "_id"
            if (isForeignKeyField(fields[i])) {
                sql.append(fieldName.toLowerCase()).append("_id = ?");
            } else {
                sql.append(fieldName).append(" = ?");
            }
            firstField = false;
        }
        sql.append(" WHERE ").append(primaryKeyField).append(" = ?");

        // Préparer la déclaration
        statement = connection.prepareStatement(sql.toString());

        // Assignation des valeurs
        int parameterIndex = 1;
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String fieldName = fields[i].getName();

            // Ignorer le champ auto-incrémenté et les champs de type List
            if (fieldName.equalsIgnoreCase(primaryKeyField) || List.class.isAssignableFrom(fields[i].getType())) {
                continue;
            }
            Object fieldValue = fields[i].get(data);
            Class<?> fieldType = fields[i].getType();

            Map<Class<?>, Function<Integer, ?>> entityFetchers = new HashMap<>();
            entityFetchers.put(Article.class, id -> findEntityById(Article.class, id));
            entityFetchers.put(Client.class, id -> findEntityById(Client.class, id));
            entityFetchers.put(Demande.class, id -> findEntityById(Demande.class, id));
            entityFetchers.put(Detail.class, id -> findEntityById(Detail.class, id));
            entityFetchers.put(Dette.class, id -> findEntityById(Dette.class, id));
            entityFetchers.put(Payment.class, id -> findEntityById(Payment.class, id));
            entityFetchers.put(User.class, id -> findEntityById(User.class, id));
    

            Integer foreignKeyValue = null;

            if (fieldValue != null && entityFetchers.containsKey(fieldType)) {
                // Si c'est une clé étrangère avec une valeur non nulle
                foreignKeyValue = (Integer) fieldType.getMethod("getId").invoke(fieldValue);
                statement.setObject(parameterIndex++, foreignKeyValue);
            } else if (entityFetchers.containsKey(fieldType)) {
                // Si c'est une clé étrangère mais que la valeur est null
                statement.setNull(parameterIndex++, Types.INTEGER);
            } else {
                // Gestion des autres types de champs
                if (fieldValue == null) {
                    statement.setNull(parameterIndex++, java.sql.Types.NULL);
                } else {
                    if (fieldValue instanceof Role ) {
                        fieldValue = fieldValue.toString(); // Convertir l'objet Role en chaîne
                    }else if (fieldValue instanceof Statut) {
                        fieldValue = fieldValue.toString();
                    }
                    
                    statement.setObject(parameterIndex++, fieldValue); // Insérer la valeur
                }
            }
            
         
        }

        // Ajout de la clé primaire à la fin
        Field primaryKey = entityClass.getDeclaredField(primaryKeyField);
        primaryKey.setAccessible(true);
        statement.setObject(parameterIndex, primaryKey.get(data));

        // Exécuter la mise à jour
        statement.executeUpdate();
    } catch (SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | NoSuchFieldException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
}
















private boolean isForeignKeyField(Field field) {
    // Vérifier si le champ est une clé étrangère (ex: User, Client, etc.)
    return field.getType().equals(User.class) || field.getType().equals(Client.class)|| field.getType().equals(Dette.class)|| field.getType().equals(Article.class);
}









@Override
public List<T> selectAll() {
    List<T> resultList = new ArrayList<>();
    openConnection();
    try {
        // Générer la requête de sélection
        String sql = "SELECT * FROM " + entityClass.getSimpleName().toLowerCase();
        statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();

        // Initialiser les récupérateurs d'entités avec une carte
        Map<Class<?>, Function<Integer, ?>> entityFetchers = new HashMap<>();
        entityFetchers.put(Article.class, id -> findEntityById(Article.class, id));
        entityFetchers.put(Client.class, id -> findEntityById(Client.class, id));
        entityFetchers.put(Demande.class, id -> findEntityById(Demande.class, id));
        entityFetchers.put(Detail.class, id -> findEntityById(Detail.class, id));
        entityFetchers.put(Dette.class, id -> findEntityById(Dette.class, id));
        entityFetchers.put(Payment.class, id -> findEntityById(Payment.class, id));
        entityFetchers.put(User.class, id -> findEntityById(User.class, id));

        while (rs.next()) {
            T entity = entityClass.getDeclaredConstructor().newInstance();
            Field[] fields = entityClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();

                // Ignorer les champs de type liste (List)
                if (List.class.isAssignableFrom(fieldType)) {
                    continue;
                }

                if (fieldType.isEnum()) {
                    // Vérifiez si le champ est de type Role
                    if (fieldType.equals(Role.class)) {
                        // Récupérer la valeur de chaîne du rôle
                        String roleString = rs.getString(field.getName().toLowerCase());

                        // Convertir la chaîne en valeur de l'énumération Role
                        try {
                            Role role = Role.valueOf(roleString);
                            field.set(entity, role);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Unknown role value: " + roleString);
                            field.set(entity, null); // ou Role.Client, par exemple
                        }
                    }
                } else if (entityFetchers.containsKey(fieldType)) {
                    // Récupérer la clé étrangère à partir du nom de champ (par exemple user_id, client_id)
                    String foreignKeyColumn = field.getName().toLowerCase() + "_id";
                    int foreignKeyValue = rs.getInt(foreignKeyColumn);

                    // Récupérer l'entité via la fonction dans la carte
                    Object foreignEntity = entityFetchers.get(fieldType).apply(foreignKeyValue);
                    field.set(entity, foreignEntity);
                
                } else {
                    // Traiter les champs normaux
                    field.set(entity, rs.getObject(field.getName()));
                }
            }
            resultList.add(entity);
        }
    } catch (SQLException | ReflectiveOperationException e) {
        e.printStackTrace();
    } finally {
        closeConnection();
    }
    return resultList;
}






private <E> E findEntityById(Class<E> entityClass, int entityId) {
    E entity = null;
    try {
        entity = entityClass.getDeclaredConstructor().newInstance();
        Field idField = entityClass.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(entity, entityId);
        
        // Si vous devez charger plus de détails, vous pouvez faire une requête supplémentaire ici
        // Par exemple, utiliser une autre requête SQL pour charger l'entité complète à partir de la base de données
        
    } catch (ReflectiveOperationException e) {
        e.printStackTrace();
    }
    return entity;
}



}

