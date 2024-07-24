package com.tms.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tms.model.Employee;
import com.tms.filter.AuthoritiesLoggingAfterFilter;
import com.tms.model.Authority;
import com.tms.repository.EmployeeRepository;

@Component
public class UserNamePasswordAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;
    
    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAfterFilter.class.getName());

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        List<Employee> emp = employeeRepository.findByEmail(username);

        if(emp.size() > 0){
            if(passwordEncoder.matches(pwd, emp.get(0).getPassword())){
                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(emp.get(0).getAuthorities()));
            }else{
                LOG.info("Invalid PPassword");
                throw new BadCredentialsException("Invalid password");
            }
        }else{
            throw new BadCredentialsException("No User registered with this details");
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for(Authority authority: authorities){
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
    
}
