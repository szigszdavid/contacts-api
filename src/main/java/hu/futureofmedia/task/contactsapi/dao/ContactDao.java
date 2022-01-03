package hu.futureofmedia.task.contactsapi.dao;

import hu.futureofmedia.task.contactsapi.entities.Contact;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactDao {

    int insertContact(int id, Contact contact);

    default int insertContact(Contact contact)
    {
        int id = (int)((Math.random() * 100) + 1);
        return insertContact(id, contact);
    }

    List<Contact> selectAllContact();

    Optional<Contact> selectContactById(int id);

    boolean deleteContactById(int id);

    int updateContactById(int id, Contact contact);

}
