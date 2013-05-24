package com.chihuo.web.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.chihuo.bussiness.Owner;

public final class OwnerUserDetails extends Owner implements UserDetails {

	OwnerUserDetails(Owner user) {
		setId(user.getId());
		setName(user.getName());
		setPassword(user.getPassword());
		setStatus(user.getStatus());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("ADMIN");
	}

	@Override
	public String getUsername() {
		return getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private static final long serialVersionUID = 3384436451564509032L;
}
