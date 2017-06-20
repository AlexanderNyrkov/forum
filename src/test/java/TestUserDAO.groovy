import io.shuritter.spring.MyApplication
import io.shuritter.spring.dao.UserDAO
import io.shuritter.spring.dao.implementation.UserDAOImpl
import io.shuritter.spring.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import spock.lang.Specification

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

/**
 * Tests for {@link UserDAOImpl}.
 *
 * @author Alexander Nyrkov
 */
@DataJpaTest
@SpringBootTest(classes = [MyApplication.class, UserDAOImpl.class])
class TestUserDAO extends Specification {

    @Autowired
    UserDAO userDAO

    private EmbeddedDatabase db

    def setup() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/user.sql")
                .build()
    }

    def 'get all users'() {
        expect:
        assertNotNull(userDAO.getAll(false))
    }

    def 'create user'() {
        setup:
        userDAO.add(new User('testName', 'testLog', 'testPas', 'test@test.com'))

        when:
        def users = userDAO.getAll(false)

        then:
        assertEquals(1, users.size())
    }

    def 'delete user'() {
        setup:
        userDAO.add(new User('testName', 'testLog', 'testPas', 'test@test.com'))

        when:
        assertEquals(1, userDAO.getAll(false).size())
        userDAO.delete(userDAO.getAll(false).get(0).getId())

        then:
        assertEquals(0, userDAO.getAll(false).size())
    }

    def 'update user'() {
        setup:
        User user = new User('testName', 'testLog', 'testPas', 'test@test.com')
        userDAO.add(user)

        when:
        def id = user.getId()
        user.setName('updatedTestName')
        user.setLogin('updatedTestLog')
        user.setPassword('updatedTestPas')
        user.setEmail('updated@test.com')
        userDAO.update(user)

        then:
        assertEquals(id, user.getId())
        assertEquals('updatedTestName', user.getName())
        assertEquals('updatedTestLog', user.getLogin())
        assertEquals('updatedTestPas', user.getPassword())
        assertEquals('updated@test.com', user.getEmail())
    }

    def 'find user by id'() {
        setup:
        User user = new User('testName', 'testLog', 'testPas', 'test@test.com')
        userDAO.add(user)

        when:
        def findUser = userDAO.getById(user.getId())

        then:
        assertEquals('testName', findUser.getName())
        assertEquals('testLog', findUser.getLogin())
        assertEquals('testPas', findUser.getPassword())
        assertEquals('test@test.com', findUser.getEmail())
    }

    def cleanup() {
        db.shutdown()
    }
}