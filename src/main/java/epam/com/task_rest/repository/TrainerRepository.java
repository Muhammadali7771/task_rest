package epam.com.task_rest.repository;

import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class TrainerRepository {
    private final EntityManager entityManager;

    public TrainerRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Trainer save(Trainer trainer){
        entityManager.persist(trainer);
        return trainer;
    }

    public boolean checkUsernameAndPasswordMatch(String username, String password){
        Query query = entityManager.createQuery("select count(t) > 0 from Trainer t left join User u " +
                " on t.user.id = u.id where u.userName = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        Boolean isMatch = (boolean) query.getSingleResult();
        return isMatch;
    }

    public Optional<Trainer> findTrainerByUsername(String username){
        try {
            TypedQuery<Trainer> query = entityManager.createQuery("select t from Trainer t left join User u " +
                    "on t.user.id = u.id where u.userName = :username", Trainer.class);
            query.setParameter("username", username);
            Trainer trainer = query.getSingleResult();
            return Optional.of(trainer);
        }catch (Exception e){
            return Optional.empty();
        }
    }
    @Transactional
    public void changePassword(String username, String password){
        Query query = entityManager.createQuery("""
                update User u set u.password = :password
                where u.userName = :username
                """);
        query.setParameter("password", password);
        query.setParameter("username", username);
        query.executeUpdate();
        //entityManager.clear();
    }

    @Transactional
    public Trainer update(Trainer trainer){
        entityManager.merge(trainer);
        return trainer;
    }
    @Transactional
    public void activateOrDeactivateTrainee(String username, boolean isActive) {
        Query query = entityManager.createQuery("""
                select u from User u left join Trainer t 
                on u.id = t.user.id where u.userName = :username
                """);
        query.setParameter("username", username);
        User user = (User) query.getSingleResult();
        user.setActive(isActive);
        entityManager.merge(user);
    }



    public Optional<Trainer> findTrainerById(Integer id){
        Trainer trainer = entityManager.find(Trainer.class, id);
        return trainer != null ? Optional.of(trainer) : Optional.empty();
    }
}
