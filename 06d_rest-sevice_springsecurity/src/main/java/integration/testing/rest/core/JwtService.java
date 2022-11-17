package integration.testing.rest.core;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${jwt.expirationTime}")
	private long expirationTime;

	public String createJWT(Claims claims, String secret) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		Key signingKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(secret), signatureAlgorithm.getJcaName());
		JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(now).setClaims(claims).signWith(signatureAlgorithm, signingKey);

		if (expirationTime >= 0) {
			long expMillis = nowMillis + expirationTime;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

	public Boolean validateToken(String token, String validationSecret) {
		String jwt = token.replace("Bearer", "").trim();

		Claims claims = Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(validationSecret))
				.parseClaimsJws(jwt).getBody();

		return (!isTokenExpired(claims.getExpiration()));
	}

	private Boolean isTokenExpired(Date expiration) {
		return expiration.before(new Date());
	}

	public String getUserIdFromToken(String token, String validationSecret) {
		String jwt = token.replace("Bearer", "").trim();
		return Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(validationSecret))
				.parseClaimsJws(jwt).getBody().get("uid").toString();

	}

	public String getUsernameFromToken(String token, String validationSecret) {
		String jwt = token.replace("Bearer", "").trim();
		return Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(validationSecret))
				.parseClaimsJws(jwt).getBody().get("username").toString();

	}

}
