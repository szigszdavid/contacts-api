package hu.futureofmedia.task.contactsapi.dao;

import hu.futureofmedia.task.contactsapi.entities.Contact;
import hu.futureofmedia.task.contactsapi.entities.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("postgres")
public class ContactDataAccessService implements ContactDao{

    private final JdbcTemplate jdbcTemplate;

    public ContactDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertContact(int id, Contact contact) {

        String sqlQuery = "insert into contact " +
                "values (?, ?, ?, ?, ?, ?)";
        int value = jdbcTemplate.update(sqlQuery,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmailAddress(),
                contact.getPhoneNumber(),
                contact.getComment()
                );
        return 0;
    }

    @Override
    public List<Contact> selectAllContact() {
        final String sql = "SELECT * FROM contact";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Contact(Integer.parseInt(resultSet.getString("id")), resultSet.getString("firstName"),
                    resultSet.getString("lastName"), resultSet.getString("emailAddress"),
                    resultSet.getString("phoneNumber"), resultSet.getString("comment"),Status.ACTIVE);
        });
        //return List.of(new Contact((int)((Math.random() * 100 ) + 1), "","", "", "", "", Status.ACTIVE));
    }

    @Override
    public Optional<Contact> selectContactById(int id) {
        final String sql = "SELECT id, firstName, lastName, emailAddress, phoneNumber, comment FROM contact where id = ?";

        Contact contact = jdbcTemplate.queryForObject(sql, (resultSet, i) -> {
            return new Contact(Integer.parseInt(resultSet.getString("id")), resultSet.getString("firstName"),
                    resultSet.getString("lastName"), resultSet.getString("emailAddress"),
                    resultSet.getString("phoneNumber"), resultSet.getString("comment"),Status.ACTIVE);
        }, new Object[]{id});
        return Optional.ofNullable(contact);
    }

    @Override
    public boolean deleteContactById(int id) {

        String sql = "delete from contact where id = ?";

        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public int updateContactById(int id, Contact contact) {

        String sql = "update contact set " +
                "firstName = ?, lastName = ?, emailAddress = ?, phoneNumber = ?, comment = ? " +
                "where id = ?";
        jdbcTemplate.update(sql,
                 contact.getFirstName(),
                 contact.getLastName(),
                 contact.getEmailAddress(),
                 contact.getPhoneNumber(),
                 contact.getComment(),
                 contact.getId());

        return 0;
    }
}
