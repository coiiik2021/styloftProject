package org.sale.project.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sale.project.entity.Account;
import org.sale.project.entity.User;
import org.sale.project.service.AccountService;
import org.sale.project.service.OrderService;
import org.sale.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;

    @Value("${name.host}")
    private String host;


    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {

            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request, authentication);

    }

    protected void clearAuthenticationAttributes(@org.jetbrains.annotations.NotNull HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Account account = accountService.findByEmail(email);

        session.setAttribute("totalAnnounce", orderService.totalAnnounce());
        session.setAttribute("host", host);
        System.out.println("ss host: " + host);

        if(account != null){
            session.setAttribute("email", email);
            System.out.println("email " + email );
            session.setAttribute("isAdmin", account.getRole().getName().equals("ADMIN"));
        }


        if (user != null) {


            // session.setAttribute("fullName", user.getFullName());
            // session.setAttribute("avatar", user.getAvatar());
            // System.out.println(">>avatar: " + session.getAttribute("avatar"));
            session.setAttribute("id", user.getId());
            session.setAttribute("sum", user.getCart() == null || user.getCart().getCartItems() == null ? 0
                    : user.getCart().getCartItems().size());
        }

    }

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        final java.util.Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

}
