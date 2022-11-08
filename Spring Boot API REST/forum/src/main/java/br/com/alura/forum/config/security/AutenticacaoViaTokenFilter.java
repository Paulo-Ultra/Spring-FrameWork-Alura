package br.com.alura.forum.config.security;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * TokenService e UsuarioRepository estão sendo injetados pelo construtor devido à classe
 * AutenticacaoViaTokenFilter ser do tipo filtro e não do tipo de um Bean gerenciado pelo Spring
 *
 */

@AllArgsConstructor
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private static final String TIPO_AUTORIZACAO = "Bearer";
    private static final String HEADER_AUTORIZACAO = "Authorization";

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = recuperarToken(request);

        if (tokenService.isTokenValido(token)) {
            autenticarCliente(token);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) throws ServletException{
        Long idUsuario = tokenService.getIdUsuario(token);
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        Usuario usuario = null;
        if(!optional.isPresent()){
            throw new ServletException();
        }
        usuario = optional.get();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTORIZACAO);

        if (ObjectUtils.isEmpty(token) || !token.startsWith(TIPO_AUTORIZACAO)) {
            return null;
        }

        return token.substring(7, token.length());
    }

}
