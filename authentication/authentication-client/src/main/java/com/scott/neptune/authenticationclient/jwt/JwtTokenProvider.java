package com.scott.neptune.authenticationclient.jwt;

import com.scott.neptune.authenticationclient.property.JwtProperties;
import com.scott.neptune.userclient.dto.AuthRoleDto;
import com.scott.neptune.userclient.dto.AuthUserDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
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

    public String createToken(AuthUserDto user, List<AuthRoleDto> roles) {

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

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtProperties.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getHeaderPrefix() + " ")) {
            return bearerToken.substring(jwtProperties.getHeaderPrefix().length() + 1);
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
        Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
        return true;
    }

}
