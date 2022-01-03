package hu.futureofmedia.task.contactsapi.api;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/contact")
@RestController
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/add")
    public void addContact(@RequestBody Contact contact)
    {
        contactService.addContact(contact);
    }

    @GetMapping
    public List<Contact> getAllContact()
    {
        return contactService.getAllContact();
    }

    @GetMapping(path = "{id}")
    public Contact getContactById(@PathVariable("id") int id)
    {
        return contactService.getContactById(id)
                .orElse(null);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContactById(@PathVariable("id") int id)
    {
        contactService.deleteContact(id);
    }

    @PutMapping("/update/{id}")
    public void updateContact(@PathVariable("id") int id, @RequestBody Contact contactToUpdate)
    {
        contactService.updateContact(id, contactToUpdate);
    }
}
