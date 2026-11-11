package springboot.learning;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springboot.learning.utility.jwtutil;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class jwtfilter extends OncePerRequestFilter {


    private final jwtutil jwtutil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("1. JWT FILTER ENTERED");

        String headers = request.getHeader("Authorization");

        System.out.println("2. HEADER FOUND: " + (headers != null));

        if (headers != null && headers.startsWith("Bearer ")) {

            System.out.println("3. BEARER TOKEN FOUND");

            String token = headers.substring(7);

            System.out.println("4. TOKEN VALID: " + jwtutil.validate(token));

            if (jwtutil.validate(token)) {

                String email = jwtutil.getemail(token);

                System.out.println("5. EMAIL: " + email);

                var auth = new UsernamePasswordAuthenticationToken(
                        email, null, List.of()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("6. AUTHENTICATION SET");
            }
        }

        filterChain.doFilter(request, response);
    }
}
