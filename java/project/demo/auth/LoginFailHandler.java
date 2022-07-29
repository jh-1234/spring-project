package project.demo.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String message;

        if (exception instanceof BadCredentialsException) {
            message = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            message = "존재하지 않는 아이디입니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "내부 시스템 오류로 인해 로그인을 실패 하였습니다.";
        } else {
            message = "서버오류로 인해 로그인을 실패 하였습니다.";
        }

        message = URLEncoder.encode(message, "UTF-8");
        setDefaultFailureUrl("/loginForm?error=true&exception=" + message);

        super.onAuthenticationFailure(request, response, exception);
    }
}
