package pk.smartq.journalApp.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pk.smartq.journalApp.entities.JournalEntry;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.repositories.JournalEntryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    // @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String username) {

        try {
            Optional<User> user = userService.findUserByUsername(username);

            if (!user.isPresent()) {
                throw new RuntimeException("User not found");
            }

            JournalEntry je = journalEntryRepository.save(journalEntry);
            user.get().getJournalEntries().add(je);
            userService.saveUser(user.get());
            return je;
        } catch (Exception e) {
            throw new RuntimeException("An error occured while saving the entry");
        }
    }

    public JournalEntry saveJournalEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public boolean deleteJournalEntryById(ObjectId id, String username) {

        try {
            Optional<User> user = userService.findUserByUsername(username);
            boolean removed = user.get().getJournalEntries().removeIf(e -> e.getId().equals(id));
            if (removed) {
                journalEntryRepository.deleteById(id);
                userService.saveUser(user.get());
                return removed;
            }
            else {
               return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occured while deleting the entry: " + e.getMessage());
        }

    }

    // public JournalEntry updateJournalEntry(ObjectId id, JournalEntry
    // journalEntry) {
    // journalEntry.setId(id);
    // journalEntryRepository.save(journalEntry);
    // return journalEntry;
    // }
}
