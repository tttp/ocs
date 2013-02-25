package eu.europa.ec.eci.oct.webcommons.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SuppressWarnings("serial")
public class OctAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private String originalPhrase;
	private String challengeResponse;

	public OctAuthenticationToken(Object principal, Object credentials, String originalPhrase, String challengeResponse) {
		super(principal, credentials);

		this.originalPhrase = originalPhrase;
		this.challengeResponse = challengeResponse;
	}

	public String getOriginalPhrase() {
		return originalPhrase;
	}

	public String getChallengeResponse() {
		return challengeResponse;
	}

}
