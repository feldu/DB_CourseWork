package db.coursework.components;

import db.coursework.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        String roleName = "NO ROLES";
        String redirectURL = request.getContextPath();
        if (user.hasRole("ROLE_ADMIN")) {
            roleName = "ADMIN";
            redirectURL = "/admin";
        } else if (user.hasRole("ROLE_PREDETERMINER")) {
            roleName = "PREDETERMINER";
            redirectURL = "/user";
        } else if (user.hasRole("ROLE_REVIEWER")) {
            roleName = "REVIEWER";
            redirectURL = "/reviewer";
        } else if (user.hasRole("ROLE_VOLUNTEER")) {
            roleName = "VOLUNTEER";
            redirectURL = "/volunteer";
        }
        log.debug("user {} has role {}", user, roleName);
        response.sendRedirect(redirectURL);
    }

}