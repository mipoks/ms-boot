package design.kfu.config.security;

import design.kfu.entity.music.Person;
import design.kfu.entity.dto.PersonForm;
import design.kfu.repository.music.PersonRepository;
import design.kfu.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String OAUTH2_USER_PASSWORD = "dankato_oauth2_pwd";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private PersonRepository userRepository;

    private String getProviderFromUri(String uri) {
        int last = uri.lastIndexOf("/");
        return uri.substring(last + 1);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        log.info(httpServletRequest.getQueryString());
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String provider = getProviderFromUri(httpServletRequest.getRequestURI());

        String email = oAuth2User.getName() + "_" + provider;

        log.info("Unique login: " + email);
        String username = "Friend";
        if (oAuth2User.getAttributes().containsKey("name")) {
            username = (String) oAuth2User.getAttributes().get("name");
        } else {
            if (oAuth2User.getAttributes().containsKey("email")) {
                username = (String) oAuth2User.getAttributes().get("email");
            }
        }

        Person user = userRepository.findByEmail(email);
        PersonForm userDto;
        if (user == null) {
            userDto = PersonForm.builder()
                    .email(email)
                    .name(username)
                    .password(OAUTH2_USER_PASSWORD)
                    .build();
            log.info("Sign up with: " + userDto);
            signUpService.signUp(userDto);
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, OAUTH2_USER_PASSWORD);

        // Authenticate the user
        authentication = authenticationManager.authenticate(authRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // Create a new session and add the security context.
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/");
    }
}
