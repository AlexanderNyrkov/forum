import io.shuritter.spring.MyApplication
import io.shuritter.spring.dao.PostDAO
import io.shuritter.spring.dao.implementation.PostDAOImpl
import io.shuritter.spring.model.Post
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
 * Tests for {@link PostDAOImpl}.
 *
 * @author Alexander Nyrkov
 */
@DataJpaTest
@SpringBootTest(classes = [MyApplication.class, PostDAOImpl.class])
class TestPostDAO extends Specification{

    @Autowired
    PostDAO postDAO

    private EmbeddedDatabase db

    def setup() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/user.sql")
                .addScript("/post.sql")
                .build()
    }

    def 'get all posts'() {
        expect:
        assertNotNull(postDAO.getAll(false))
    }

    def 'create post'() {
        setup:
        postDAO.add(new Post("testText"))

        when:
        def posts = postDAO.getAll(false)

        then:
        assertEquals(1, posts.size())
    }

    def 'delete post'() {
        setup:
        postDAO.add(new Post("testText"))

        when:
        assertEquals(1, postDAO.getAll(false).size())
        postDAO.delete(postDAO.getAll(false).get(0).getId())

        then:
        assertEquals(0, postDAO.getAll(false).size())
    }

    def 'update post'() {
        setup:
        Post post = new Post("testText")
        postDAO.add(post)

        when:
        def id = post.getId()
        post.setText('updatedTestText')
        postDAO.update(post)

        then:
        assertEquals(id, post.getId())
        assertEquals('updatedTestText', post.getText())
    }

    def 'find post by id'() {
        setup:
        Post post = new Post("testText")
        postDAO.add(post)

        when:
        def findPost = postDAO.getById(post.getId())

        then:
        assertEquals('testText', findPost.getText())
    }

    def cleanup() {
        db.shutdown()
    }
}
