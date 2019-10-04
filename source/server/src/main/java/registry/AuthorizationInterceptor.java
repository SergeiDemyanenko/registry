package registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Value("${application.authorization.login-uri}")
    private String authorizationLoginUri;

    @Value("${application.authorization.enabled}")
    private Boolean authorizationEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (Boolean.TRUE.equals(authorizationEnabled)
                && request.getSession().getAttribute(Controller.USER_PARAM) == null
                && !request.getRequestURI().equals(authorizationLoginUri))
        {
            response.sendRedirect(authorizationLoginUri);
            return false;
        }

        return true;
    }
}
