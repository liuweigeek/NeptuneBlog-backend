package com.scott.neptune.authenticationserver.jwt;

import com.scott.neptune.authenticationserver.convertor.AuthUserConvertor;
import com.scott.neptune.authenticationserver.domain.AuthRole;
import com.scott.neptune.authenticationserver.domain.AuthUser;
import com.scott.neptune.authenticationserver.properties.JwtProperties;
import com.scott.neptune.authenticationserver.service.AuthUserService;
import com.scott.neptune.common.exception.RestException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final AuthUserService authUserService;
    private final AuthUserConvertor authUserConvertor;

    public String createToken(AuthUser user, List<AuthRole> roles) {

//        Claims claims = Jwts.claims().setSubject(user.getUsername());
//        claims.put(jwtProperties.getMd5Key(), roles);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtProperties.getExpiration() * 1000);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtProperties.getSecret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setId(Long.toString(user.getId()))
                .setSubject(user.getUsername())
//                .setClaims(claims)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(expirationDate)
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        AuthUser authUser = authUserService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(authUserConvertor.convertToDto(), "", authUser.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtProperties.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getHeaderPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new RestException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
