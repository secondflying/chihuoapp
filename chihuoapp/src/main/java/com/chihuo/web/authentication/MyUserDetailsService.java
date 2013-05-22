package com.chihuo.web.authentication;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Owner;
import com.chihuo.service.OwnerService;

@Component
public class MyUserDetailsService implements UserDetailsService,Serializable {
	@Autowired
	private OwnerService ownerService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner user = ownerService.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new OwnerUserDetails(user);
    }
    
    private final class OwnerUserDetails extends Owner implements UserDetails {
    	
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
}
