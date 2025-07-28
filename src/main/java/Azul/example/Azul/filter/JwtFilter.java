package Azul.example.Azul.filter;

import Azul.example.Azul.service.JwtService;
import Azul.example.Azul.service.UserService;
import Azul.example.Azul.model.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public JwtFilter(@Lazy UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println("[JWT FILTER] Processing request: " + path);

        // Skip JWT processing for public endpoints
        if (path.equals("/user/login") ||
                path.equals("/user/roles") ||
                path.startsWith("/user/register") ||
                path.equals("/upload")) {
            System.out.println("[JWT FILTER] Skipping JWT for public endpoint: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        System.out.println("[JWT FILTER] Authorization header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
                System.out.println("[JWT FILTER] Extracted username: " + username);
            } catch (Exception e) {
                System.err.println("[JWT FILTER] Error extracting username from token: " + e.getMessage());
            }
        } else {
            System.out.println("[JWT FILTER] No valid Authorization header found");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Get user from database
                var user = userService.findByEmail(username);
                if (user != null) {
                    System.out.println("[JWT FILTER] Found user: " + user.getFullName());

                    // Create UserPrincipal
                    UserPrincipal userPrincipal = new UserPrincipal(user);

                    if (jwtService.validateToken(token, userPrincipal)) {
                        // Extract role from JWT and add as Spring Security authority
                        String role = jwtService.extractRole(token);
                        System.out.println("[JWT FILTER] Extracted role: " + role);

                        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                        System.out.println("[JWT FILTER] Created authority: " + authority.getAuthority());

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of(authority));
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        System.out.println("[JWT FILTER] Authentication set successfully for user: " + username + " with role: " + role);
                    } else {
                        System.err.println("[JWT FILTER] Token validation failed for user: " + username);
                    }
                } else {
                    System.err.println("[JWT FILTER] User not found: " + username);
                }
            } catch (Exception e) {
                System.err.println("[JWT FILTER] Error during authentication: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (username == null) {
            System.out.println("[JWT FILTER] Username is null, cannot authenticate");
        } else {
            System.out.println("[JWT FILTER] User already authenticated: " + SecurityContextHolder.getContext().getAuthentication());
        }

        filterChain.doFilter(request, response);
    }
}