package com.mrgenius.learningjava.ToDoList.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mrgenius.learningjava.ToDoList.users.IUsersReoository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired 
    private IUsersReoository usersReoository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/task/")){
            var autorization = request.getHeader("Autorization");
            var authEncode = autorization.substring("Basic".length()).trim();
            byte authDecode[]  = Base64.getDecoder().decode(authEncode);
            var authString = new String(authDecode);
            String credentials[] = authString.split(":");
            var username = credentials[0];
            var password = credentials[1];
            var user = this.usersReoository.findByUsername(username);
            if(user==null){
                response.sendError(401,"Usuario inexiste");
            }else{
                var passVerifyered = BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
                if(passVerifyered.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                }else{
                response.sendError(401); 
                }
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }
}
