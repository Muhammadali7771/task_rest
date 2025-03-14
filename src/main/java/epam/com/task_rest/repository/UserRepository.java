package epam.com.task_rest.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isUsernameTaken(String username) {
        Query query = entityManager.createQuery("select count(u.id) > 0 from User u where u.userName = :username");
        query.setParameter("username", username);
        boolean isTaken = (boolean) query.getSingleResult();
        return isTaken;
    }
}
