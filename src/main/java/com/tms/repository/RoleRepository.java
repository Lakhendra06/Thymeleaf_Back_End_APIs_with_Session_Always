package com.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.model.Role;

@Repository
public interface RoleRepository  extends JpaRepository <Role, Long>{
    
}
