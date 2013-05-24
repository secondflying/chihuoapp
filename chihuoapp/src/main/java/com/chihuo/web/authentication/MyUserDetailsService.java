package com.chihuo.web.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chihuo.bussiness.Owner;
import com.chihuo.service.OwnerService;

@Component
public class MyUserDetailsService implements UserDetailsService{
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
    
}
