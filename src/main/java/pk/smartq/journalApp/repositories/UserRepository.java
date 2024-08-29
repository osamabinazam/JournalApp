package pk.smartq.journalApp.repositories;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pk.smartq.journalApp.entities.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    public User findByUsername(@NonNull String username);

    public void deleteByUsername(@NonNull String username);

}
