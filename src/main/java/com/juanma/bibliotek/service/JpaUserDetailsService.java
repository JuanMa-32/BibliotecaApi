package com.juanma.bibliotek.service;

import com.juanma.bibliotek.entities.UserEntity;
import com.juanma.bibliotek.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> o =  repository.findByUsername(username);
        if(o.isPresent()){
            UserEntity us= o.get();

            List<GrantedAuthority> permisos = new ArrayList();
            permisos.add(new SimpleGrantedAuthority("ROLE_"+us.getRol().toString()));

            return new User(us.getUsername(),us.getPassword(),true,true,true,true,permisos);
        }
        throw new UsernameNotFoundException("NO EXISTIS!!!!");
    }
}
