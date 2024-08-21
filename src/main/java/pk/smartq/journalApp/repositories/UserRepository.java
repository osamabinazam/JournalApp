package pk.smartq.journalApp.repositories;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pk.smartq.journalApp.entities.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    @Query("{ 'username': ?0 }")
    public User findByUsername(@NonNull String username);

}
