package br.dev.sant.sga.infrastructure.components;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenComponent {

	/*
	 * Método para receber um TOKEN JWT e extrair o valor do ID do usuário contido
	 * no corpo do TOKEN (PAYLOAD)
	 */
	public UUID getIdFromToken(String token) {
		return UUID.fromString(getClaimFromToken(token, Claims::getSubject));
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		var secretKey = "37eda45a-0c72-425c-a6c6-e750eefddc27";
		final Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return claimsResolver.apply(claims);
	}
}
