package com.scott.neptune.authenticationclient.jwt;

import com.scott.neptune.authenticationclient.property.JwtProperties;
import com.scott.neptune.userclient.dto.AuthRoleDto;
import com.scott.neptune.userclient.dto.AuthUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    private static final String CLAIM_KEY_USER_ID = "id";
    private static final String CLAIM_KEY_USERNAME = "username";
    private static final String CLAIM_KEY_EMAIL = "email";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    private final JwtProperties jwtProperties;

    public String createToken(AuthUserDto user, List<AuthRoleDto> roles) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtProperties.getExpiration() * 1000);

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(CLAIM_KEY_USER_ID, user.getId());
        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
        claims.put(CLAIM_KEY_EMAIL, user.getEmail());

        Key signingKey = generateSigningKey(jwtProperties.getSecret());
        return jwtProperties.getHeaderPrefix() + " " + Jwts.builder()
                .setId(Long.toString(user.getId()))
                .setSubject(user.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(expirationDate)
                .signWith(JwtTokenProvider.SIGNATURE_ALGORITHM, signingKey)
                .compact();
    }

    public String getUsername(String claimsJws) {
        Key signingKey = generateSigningKey(jwtProperties.getSecret());
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(claimsJws).getBody().getSubject();
    }

    public String getClaimsJws(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtProperties.getHeader());
        return getClaimsJws(bearerToken);
    }

    public String getClaimsJws(String bearerToken) {
        if (StringUtils.isNotBlank(bearerToken) && StringUtils.startsWith(bearerToken, jwtProperties.getHeaderPrefix() + " ")) {
            return bearerToken.substring(jwtProperties.getHeaderPrefix().length() + 1);
        }
        return null;
    }

    public AuthUserDto getAutoUser(String claimsJws) {
        Key signingKey = generateSigningKey(jwtProperties.getSecret());
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(claimsJws).getBody();
        Long userId = claims.get(CLAIM_KEY_USER_ID, Long.class);
        String username = claims.get(CLAIM_KEY_USERNAME, String.class);
        String email = claims.get(CLAIM_KEY_EMAIL, String.class);
        return AuthUserDto.builder().id(userId).username(username).email(email).authorities(new String[]{}).build();
    }

    public boolean validateToken(String token) throws JwtException, IllegalArgumentException {
        Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token);
        return true;
    }

    private Key generateSigningKey(String secret) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        return new SecretKeySpec(apiKeySecretBytes, JwtTokenProvider.SIGNATURE_ALGORITHM.getJcaName());
    }

}
