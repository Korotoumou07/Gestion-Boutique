package Fabrique;

import repository.*;
import repository.bd.*;
import repository.list.*;



public class Fabrique {
    public static ClientRepository createClientRepository(String type) {
        return "LIST".equalsIgnoreCase(type) ? new ClientRepositoryList() : new ClientRepositoryBD();
    }

    public static UserRepository createUserRepository(String type) {
        return "LIST".equalsIgnoreCase(type) ? new UserRepositoryList() : new UserRepositoryBD();
    }

    public static ArticleRepository createArticleRepository(String type) {
        return "LIST".equalsIgnoreCase(type) ? new ArticleRespositoryList() : new ArticleRepositoryBD();
    }

    // public static DetteRepository createDetteRepository(String type) {
    //     return "LIST".equalsIgnoreCase(type) ? new DetteRepositoryList() : new DetteRepositoryBD();
    // }
    public static DetteRepository createDetteRepository(String type) {
        if ("LIST".equalsIgnoreCase(type)) {
            // Ensure you have an instance of ClientRepositoryList
            ClientRepositoryList clientRepository = new ClientRepositoryList();
            return new DetteRepositoryList(clientRepository); // Pass clientRepository to DetteRepositoryList
        } else {
            return new DetteRepositoryBD(); // For database-backed implementation
        }
    }
    
    public static PaymentRepository createPaymentRepository(String type) {
        return "LIST".equalsIgnoreCase(type) ? new PaymentRepositoryList() : new PaymentRepositoryBD();
    }

    public static DetailRepository createDetailRepository(String type) {
        if ("LIST".equalsIgnoreCase(type)) {
            ClientRepositoryList clientRepository = new ClientRepositoryList();
            DetteRepositoryList detteRepository = new DetteRepositoryList(clientRepository);
            ArticleRespositoryList articleRespository =new ArticleRespositoryList();
            return new DetailRepositoryList(articleRespository,detteRepository); // Pass clientRepository to DetteRepositoryList
        } else {
            return new DetailRepositoryBD(); // For database-backed implementation
        }
    }

    public static DetailADRepository createDetailADRepository(String type) {
        if ("LIST".equalsIgnoreCase(type)) {
            ClientRepositoryList clientRepository = new ClientRepositoryList();
            DemandeRepositoryList demandeRepository =new DemandeRepositoryList(clientRepository);
            ArticleRespositoryList articleRepository=new ArticleRespositoryList();
            return new DetailADRepositoryList(demandeRepository, articleRepository);
        } else {
        return new DetailADRepositoryBD(); // Assurez-vous que cette classe existe
        }
}


    // public static DemandeRepository createDemandeRepository(String type) {
    //     return "LIST".equalsIgnoreCase(type) ? new DemandeRepositoryList(null) : new DemandeRepositoryBD();
    // }
    public static DemandeRepository createDemandeRepository(String type) {
        if ("LIST".equalsIgnoreCase(type)) {
            ClientRepositoryList clientRepository = new ClientRepositoryList(); // Initialize the client repository
            return new DemandeRepositoryList(clientRepository); // Pass the client repository to the constructor
        } else {
            return new DemandeRepositoryBD(); // For database-backed implementation
        }
    }
    
    public static RelanceRepository createRelanceRepository(String type) {
        return "LIST".equalsIgnoreCase(type) ? new RelanceRepositoryList() : new RelanceRepositoryBD();
    }
    
}
