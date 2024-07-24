package com.tms.service;

import java.util.List;

import com.tms.model.Role;

public interface RoleService {
    List<Role> getAllRole();
    void saveRole(Role role);
    Role getRoleById(Long id);
    void deleteRoleById(Long id);
}
