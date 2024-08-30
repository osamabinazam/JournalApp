package pk.smartq.journalApp.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pk.smartq.journalApp.entities.JournalEntry;


public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
