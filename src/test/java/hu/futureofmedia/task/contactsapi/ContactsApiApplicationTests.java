package hu.futureofmedia.task.contactsapi;

import hu.futureofmedia.task.contactsapi.dao.ContactDao;
import hu.futureofmedia.task.contactsapi.dao.ContactDataAccessService;
import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;


class ContactsApiApplicationTests {


    private JdbcTemplate jdbcTemplate;
    private ContactDataAccessService dao;

    @BeforeEach
    @Autowired
    public void setUp(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        dao = new ContactDataAccessService(jdbcTemplate);
    }

    @Test
    public void getContacts()
    {
        /*
        List<Contact> contacts = dao.selectAllContact();

        assertEquals(3, contacts.size());
        */

        ReflectionTestUtils.setField(dao, "jdbcTemplate", jdbcTemplate);
        Mockito.when(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CONTACT", Integer.class))
                .thenReturn(3);

        assertEquals(3, dao.selectAllContact());
    }

    @Test
    public void getContactByUnValidId()
    {
        Optional<Contact> contact = dao.selectContactById(99);

        assertFalse(contact.isPresent());
    }

    @Test
    public void getContactByValidId()
    {
        Optional<Contact> contact = dao.selectContactById(1);

        assertTrue(contact.isPresent());
    }

    @Test
    public void createValidContact()
    {
        Contact contact = new Contact(4,"Darth","Vader", "darthvader@gmail.com", "123456789","", Status.ACTIVE);
        dao.insertContact(contact);

        List<Contact> contacts = dao.selectAllContact();
        assertEquals(4, contacts.size());
        assertEquals("Darth", contacts.get(3).getFirstName());
        assertEquals("Vader", contacts.get(3).getLastName());
        assertEquals("darthvader@gmail.com",contacts.get(3).getEmailAddress());
        assertEquals("123456789", contacts.get(3).getPhoneNumber());
        assertEquals("", contacts.get(3).getComment());
    }

    @Test
    public void updateFirstName()
    {
        Contact contact = dao.selectContactById(1).get();
        contact.setFirstName("Luke");
        dao.updateContactById(1, contact);

        Contact updatedContact = dao.selectContactById(1).get();
        assertEquals("Luke", updatedContact.getFirstName());
    }

    @Test
    public void deleteContact()
    {
        dao.deleteContactById(2);
        List<Contact> contacts = dao.selectAllContact();
        assertEquals(2, contacts.size());
    }

}
