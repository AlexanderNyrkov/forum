package io.shuritter.spring.controller.implementation;

import io.shuritter.spring.controller.BaseController;
import io.shuritter.spring.model.BaseEntity;
import io.shuritter.spring.model.User;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Abstract base class for Controller classes
 * @param <T> Describes any entity class
 * Implementation of {@link BaseController}
 * @author Alexander Nyrkov
 */
public abstract class BaseControllerImpl<T extends BaseEntity> implements BaseController<T> {

    public Boolean authorization(HttpServletRequest request) {
        final String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Basic");
    }

    public Boolean loginPassword(HttpServletRequest request, User user) {
        final String authorization = request.getHeader("Authorization");
        String base64Credentials = authorization.substring("Basic".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                Charset.forName("UTF-8"));
        final String[] values = credentials.split(":");
        return !values[0].equals(user.getLogin()) || !BCrypt.checkpw(values[1], user.getPassword());
    }
}
