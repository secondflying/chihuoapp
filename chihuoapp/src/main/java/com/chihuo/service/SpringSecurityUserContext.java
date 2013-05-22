package com.chihuo.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Owner;

@Component
public class SpringSecurityUserContext implements UserContext {

	@Override
	public Owner getCurrentUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication == null) {
			return null;
		}

		return (Owner) authentication.getPrincipal();
	}


	@Override
	public void setCurrentUser(Owner user) {
		if (user == null) {
			throw new IllegalArgumentException("user cannot be null");
		}
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				user, user.getPassword(),
				AuthorityUtils.createAuthorityList("ADMIN"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
