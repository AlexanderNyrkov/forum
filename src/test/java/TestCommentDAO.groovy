import io.shuritter.spring.MyApplication
import io.shuritter.spring.dao.CommentDAO
import io.shuritter.spring.dao.implementation.CommentDAOImpl
import io.shuritter.spring.model.Comment
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
 * Tests for {@link CommentDAOImpl}.
 *
 * @author Alexander Nyrkov
 */
@DataJpaTest
@SpringBootTest(classes = [MyApplication.class, CommentDAOImpl.class])
class TestCommentDAO extends Specification {
    @Autowired
    CommentDAO commentDAO

    private EmbeddedDatabase db

    def setup() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/user.sql")
                .addScript("/post.sql")
                .addScript("/comment.sql")
                .build()
    }

    def 'get all comments'() {
        expect:
        assertNotNull(commentDAO.getAll(false))
    }

    def 'create comment'() {
        setup:
        commentDAO.add(new Comment("testText"))

        when:
        def comments = commentDAO.getAll(false)

        then:
        assertEquals(1, comments.size())
    }

    def 'delete comment'() {
        setup:
        commentDAO.add(new Comment("testText"))

        when:
        assertEquals(1, commentDAO.getAll(false).size())
        commentDAO.delete(commentDAO.getAll(false).get(0).getId())

        then:
        assertEquals(0, commentDAO.getAll(false).size())
    }

    def 'update comment'() {
        setup:
        Comment comment = new Comment("testText")
        commentDAO.add(comment)

        when:
        def id = comment.getId()
        comment.setText('updatedTestText')
        commentDAO.update(comment)

        then:
        assertEquals(id, comment.getId())
        assertEquals('updatedTestText', comment.getText())
    }

    def 'find comment by id'() {
        setup:
        Comment comment = new Comment("testText")
        commentDAO.add(comment)

        when:
        def findComment = commentDAO.getById(comment.getId())

        then:
        assertEquals('testText', findComment.getText())

    }

    def cleanup() {
        db.shutdown()
    }
}
