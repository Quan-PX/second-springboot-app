package com.quanpham.secondApp.Configuration;

import com.quanpham.secondApp.Service.InterfaceService.JwtService;
import com.quanpham.secondApp.Service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PreFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        thuc hien check truoc khi dua toi request
        final String authorization = request.getHeader("Authorization");

        if(StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")){    // neu no rong || ko bat dau tu bearer
            filterChain.doFilter(request, response);
            return;
        }

        // neu no chua token thi
        final String token = authorization.substring("Bearer ".length());
        final String username = jwtService.extractUser(token);

        if(StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.userDetailService().loadUserByUsername(username);        // check database xem user co exist ko
            if(jwtService.validateToken(token) && userDetails.equals(jwtService.extractUser(userDetails.getUsername()))) {      // sau khi ktra roi thi ta kiem tra token hop le ko
                SecurityContext context = SecurityContextHolder.createEmptyContext();   // Tao context moi
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); // tao ra 1 username, password o trong co che AuthenticationProvider
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));     // build request de day het thong tin vao request nay
                context.setAuthentication(authenticationToken);     // context se gan authen
                SecurityContextHolder.setContext(context);          // gan laij securitycontextholder
            }
        }
        filterChain.doFilter(request, response);
    }
}
