package com.tms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tms.model.Employee;
import com.tms.repository.EmployeeRepository;

@Service
public class EmployeeUserDetailService implements UserDetailsService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
        String userName, password = null;
        List<GrantedAuthority> authorities=null;
        List<Employee> employee = employeeRepository.findByEmail(username);
        if(employee.size() == 0){
            throw new UsernameNotFoundException("User details not found for the user : "+username);
        }else{
            userName= employee.get(0).getEmail();
            password= employee.get(0).getPassword();
            authorities=new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(employee.get(0).getRole()));
        }
        return new User(username,password,authorities);
    }    
}
