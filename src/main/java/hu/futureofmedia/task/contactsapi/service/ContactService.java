package hu.futureofmedia.task.contactsapi.service;

import hu.futureofmedia.task.contactsapi.dao.ContactDao;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    private final ContactDao contactDao;

    @Autowired
    public ContactService(@Qualifier("postgres") ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public int addContact(Contact contact)
    {
        return contactDao.insertContact(contact);
    }

    public List<Contact> getAllContact()
    {
        return contactDao.selectAllContact();
    }

    public Optional<Contact> getContactById(int id)
    {
        return contactDao.selectContactById(id);
    }

    public boolean deleteContact(int id)
    {
        return contactDao.deleteContactById(id);
    }

    public int updateContact(int id, Contact newContact)
    {
        return contactDao.updateContactById(id, newContact);
    }
}
