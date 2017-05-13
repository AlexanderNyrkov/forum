package io.shuritter.spring.dao;
import io.shuritter.spring.model.Post;

import java.util.List;

public interface PostDAO extends BaseDAO<Post> {
    List userPosts(String userId);
}
