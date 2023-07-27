package com.juanma.bibliotek.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanma.bibliotek.auth.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

import static com.juanma.bibliotek.auth.TokenJwtConfig.SECRET_KEY;

//se ejecuta siempre en cada post/put/get
public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        /*
        El filtro verifica si la cabecera "Authorization" existe en la solicitud y si comienza con "Bearer ".
         Si estas condiciones no se cumplen, el filtro llama a chain.doFilter() para permitir que la solicitud continúe su flujo normal y
         llegue al destino final (por ejemplo, un controlador). Esto puede ser útil para permitir el acceso público a ciertas
         rutas o recursos de la aplicación que no requieren autenticación con un token JWT, mientras que otras rutas protegidas
         requerirán autenticación con un token válido.
         */
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        String token = header.replace("Bearer ","");

        try{
          Claims claims=  Jwts.parserBuilder().setSigningKey(SECRET_KEY)  //se valida que sea la misma firma que con la que fue creado
                    .build()
                    .parseClaimsJws(token)//se verifica su firma usando la clave secreta configurada anteriormente.
                    .getBody();// obtiene el cuerpo del token JWT como un objeto Claims

            Object authoritiesClaims = claims.get("authorities");
            String username = claims.getSubject();

            Collection<? extends GrantedAuthority> authorities = Arrays
                    .asList(new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims.toString().getBytes(),SimpleGrantedAuthority[].class));


            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);// se crea un objeto Authentication que representa la información de autenticación del usuario. Este objeto contiene detalles sobre el usuario autenticado, como su nombre de usuario, roles,
            SecurityContextHolder.getContext().setAuthentication(authentication);//actúa como un contenedor para almacenar la información de autenticación del usuario durante el tiempo de vida de una solicitud.
            chain.doFilter(request,response);

        }catch (JwtException e){
            Map<String,String> body = new HashMap<>();

            body.put("error",e.getMessage());
            body.put("message","TOKEN INVALIDO");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType("application/json");
        }


    }
}
