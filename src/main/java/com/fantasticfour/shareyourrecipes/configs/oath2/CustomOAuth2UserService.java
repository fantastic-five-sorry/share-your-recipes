package com.fantasticfour.shareyourrecipes.configs.oath2;

import org.slf4j.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		logger.info("CustomOAuth2UserService invoked");
		OAuth2User user = super.loadUser(userRequest);
		return new CustomOAuth2User(user);
	}

}
