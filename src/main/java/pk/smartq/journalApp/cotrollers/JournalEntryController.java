package pk.smartq.journalApp.cotrollers;

import org.springframework.web.bind.annotation.*;
import pk.smartq.journalApp.entities.JournalEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    Map<Long, JournalEntry> journalEntries = new HashMap<>();
    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public  boolean add(@RequestBody  JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/{id}")
    public JournalEntry get(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id, @RequestBody JournalEntry journalEntry){
        journalEntries.put(id, journalEntry);
        return true;
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id){
        journalEntries.remove(id);
        return true;
    }

}
