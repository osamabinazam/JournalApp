package pk.smartq.journalApp.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pk.smartq.journalApp.entities.JournalEntry;
import pk.smartq.journalApp.repositories.JournalEntryRepository;

import java.util.List;
import java.util.Optional;

@Component
public class    JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveJournalEntry(JournalEntry journalEntry  ){


        return  journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

//    public JournalEntry updateJournalEntry(ObjectId id, JournalEntry journalEntry) {
//        journalEntry.setId(id);
//        journalEntryRepository.save(journalEntry);
//        return journalEntry;
//    }
}
