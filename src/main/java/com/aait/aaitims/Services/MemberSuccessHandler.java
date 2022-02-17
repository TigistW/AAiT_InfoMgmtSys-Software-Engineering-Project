package com.aait.aaitims.Services;

import java.io.IOException;
// import java.util.Collection;
// import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.hibernate.mapping.Set;
import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.AuthorityUtils;
// import org.springframework.security.web.DefaultRedirectStrategy;
// import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MemberSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // String redirectUrl = null;

        // Collection<? extends GrantedAuthority> authorities =
        // authentication.getAuthorities();

        // for (GrantedAuthority grantedAuthority : authorities) {
        // System.out.println("role " + grantedAuthority.getAuthority());
        // if (grantedAuthority.getAuthority().equals("ROLE_user")) {
        // redirectUrl = "/list";
        // break;
        // } else if (grantedAuthority.getAuthority().equals("ROLE_Admin")) {
        // redirectUrl = "/home";
        // break;
        // }
        // }
        // System.out.println("redirectUrl " + redirectUrl);
        // if (redirectUrl == null) {
        // throw new IllegalStateException();
        // }
        // new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
        // }

        // @Override
        // public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
        // HttpServletResponse httpServletResponse, Authentication authentication)
        // throws IOException, ServletException {

        // Set<String> role =
        // AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // if (role.contains("ROLE_Admin")) {
        // httpServletResponse.sendRedirect("/home");
        // } else {
        // httpServletResponse.sendRedirect("/list");
        // }
        // }
    }
}
