package pk.smartq.journalApp.cotrollers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.smartq.journalApp.entities.JournalEntry;
import pk.smartq.journalApp.payloads.ErrorResponse;
import pk.smartq.journalApp.services.JournalEntryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public ResponseEntity<?> getAll(){

        Optional<List<JournalEntry>> all = Optional.ofNullable(journalEntryService.getAllJournalEntries());
        if (all.isPresent()){
            Map<String, Object> body = new HashMap<>();
            body.put("data", all);
            body.put("statusCode", HttpStatus.OK.value());
            body.put("timestamp", LocalDateTime.now());
            return new ResponseEntity<>(body,HttpStatus.OK);
        }

        return new ResponseEntity<>(new ErrorResponse("Not found", HttpStatus.NOT_FOUND.value(), LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public  ResponseEntity<?> add(@RequestBody  JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            Optional<JournalEntry> myEntry = Optional.ofNullable(journalEntryService.saveJournalEntry(journalEntry));
            return  new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e ){
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ObjectId id){
        Optional<JournalEntry>  entry  = Optional.ofNullable(journalEntryService.getJournalEntryById(id).orElse(null));
        if (entry.isPresent()){
            return new  ResponseEntity<>(entry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry){
        try {
            JournalEntry old = journalEntryService.getJournalEntryById(id).orElse(null);
        if (old != null){
            old.setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : old.getTitle());
            old.setContent(journalEntry.getContent() != null && ! journalEntry.getContent().isEmpty() ? journalEntry.getContent() : old.getContent());
        }
        journalEntryService.saveJournalEntry(old);
        return  new ResponseEntity<>( old, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        try{
            journalEntryService.deleteJournalEntryById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
