package epam.com.task_rest.repository;


import epam.com.task_rest.entity.Trainee;
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
public class TraineeRepository {
    private final EntityManager entityManager;

    public TraineeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Trainee save(Trainee trainee) {
        entityManager.persist(trainee);
        return trainee;
    }

    public boolean checkUsernameAndPasswordMatch(String username, String password) {
        Query query = entityManager.createQuery("select count(t) > 0 from Trainee t left join User u " +
                " on t.user.id = u.id where u.userName = :username and u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        boolean isMatch = (boolean) query.getSingleResult();
        return isMatch;
    }

    public Optional<Trainee> findTraineeByUsername(String username) {
        try {
            TypedQuery<Trainee> query = entityManager.createQuery("select t from Trainee t left join User u " +
                    " on t.user.id = u.id where u.userName = :username", Trainee.class);
            query.setParameter("username", username);
            Trainee trainee = query.getSingleResult();
            return Optional.of(trainee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Transactional
    public void changePassword(String username, String newPassword) {
        Query query = entityManager.createQuery("""
                update User u set u.password = :password
                where u.userName = :username
                """);
        query.setParameter("password", newPassword);
        query.setParameter("username", username);
        query.executeUpdate();
    }

    @Transactional
    public Trainee update(Trainee trainee) {
        entityManager.merge(trainee);
        return trainee;
    }
    @Transactional
    public void activateOrDeactivateTrainee(String username, boolean isActive) {
        Query query = entityManager.createQuery("""
                select u from User u left join Trainee t 
                on u.id =t.user.id where u.userName = :username
                """);
        query.setParameter("username", username);
        User user = (User) query.getSingleResult();
        user.setActive(isActive);
        entityManager.merge(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        Query query = entityManager.createQuery("select t from Trainee t " +
                "where t.user.userName = :username");
        query.setParameter("username", username);
        Trainee trainee = (Trainee) query.getSingleResult();
        entityManager.remove(trainee);
    }

    public List<Trainer> findTrainersListThatNotAssignedOnTraineeByTraineeUsername(String traineeUsername){
        String sql = """
                select tr.* from trainee_trainer tt inner join trainers tr 
                    on tr.id = tt.trainer_id where tt.trainee_id not in 
                (select t.id from trainees t inner join users u on u.id = t.user_id where u.username = :username)   
                """;
        Query query = entityManager.createNativeQuery(sql, Trainer.class);
        query.setParameter("username", traineeUsername);
        List<Trainer> trainers = query.getResultList();
        return trainers;
    }
    @Transactional
    public List<Trainer> updateTraineeTrainerList(String traineeUsername, List<String> trainersUsernameList) {
        Query query = entityManager.createQuery("""
                select t from Trainee t left join User u on u.id = t.user.id
                where u.userName = :traineeUsername
                """);
        query.setParameter("traineeUsername", traineeUsername);
        Trainee trainee = (Trainee) query.getSingleResult();

        Query query1 = entityManager.createQuery("""
                select t from Trainer t left join User u on u.id = t.user.id
                where u.userName in :trainersUsernameList
                """);
        query1.setParameter("trainersUsernameList", trainersUsernameList);
        List<Trainer> trainers = (List<Trainer>) query1.getResultList();
        trainee.setTrainers(trainers);

        entityManager.merge(trainee);
        return trainee.getTrainers();
    }

    public Optional<Trainee> getTraineeById(Integer traineeId) {
        Trainee trainee = entityManager.find(Trainee.class, traineeId);
        return trainee != null ? Optional.of(trainee) : Optional.empty();
    }
}
