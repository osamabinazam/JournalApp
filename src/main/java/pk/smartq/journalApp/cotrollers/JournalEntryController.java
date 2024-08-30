package pk.smartq.journalApp.cotrollers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pk.smartq.journalApp.entities.JournalEntry;
import pk.smartq.journalApp.entities.User;
import pk.smartq.journalApp.payloads.ErrorResponse;
import pk.smartq.journalApp.services.JournalEntryService;
import pk.smartq.journalApp.services.UserService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        try {

            String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByUsername(username).orElse(null);
            if (user != null) {
                List<JournalEntry> all = user.getJournalEntries();
                Map<String, Object> body = new HashMap<>();
                body.put("data", all);
                body.put("statusCode", HttpStatus.OK.value());
                body.put("timestamp", LocalDateTime.now());
                return new ResponseEntity<>(body, HttpStatus.OK);
            }
            return new ResponseEntity<>(
                    new ErrorResponse("Not found", HttpStatus.NOT_FOUND.value(), LocalDateTime.now()),
                    HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody JournalEntry journalEntry) {

        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
            journalEntry.setDate(LocalDateTime.now());
            Optional<JournalEntry> myEntry = Optional
                    .ofNullable(journalEntryService.saveJournalEntry(journalEntry, username));
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(
                    new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable ObjectId id) {

        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            String usernanme = (String) SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByUsername(usernanme).orElse(null);
            List<JournalEntry> entries = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id))
                    .toList();
            if (!entries.isEmpty()) {
                return new ResponseEntity<>(entries.get(0), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Optional<JournalEntry> entry =
        // Optional.ofNullable(journalEntryService.getJournalEntryById(id).orElse(null));
        // if (entry.isPresent()) {
        // return new ResponseEntity<>(entry, HttpStatus.OK);
        // }

        // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {

        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findUserByUsername(username).orElse(null);
            if (user == null) {

                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<JournalEntry> entries = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id))
                    .toList();
            if (!entries.isEmpty()) {
                JournalEntry old = journalEntryService.getJournalEntryById(id).orElse(null);
                if (old != null) {
                    old.setTitle(
                            journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty()
                                    ? journalEntry.getTitle()
                                    : old.getTitle());
                    old.setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty()
                            ? journalEntry.getContent()
                            : old.getContent());
                }
                journalEntryService.saveJournalEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id) {

        try {
            String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
            boolean removed = journalEntryService.deleteJournalEntryById(id, username);

            if (removed) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
