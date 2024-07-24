package com.tms.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.model.Employee;
import com.tms.repository.EmployeeRepository;
import com.tms.service.EmployeeService;

@RestController
public class LoginController {
    
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/getUserName")
    public Employee currentUserName(Authentication authentication) {
        List<Employee> emp = employeeRepository.findByEmail(authentication.getName());
        if(emp.size() > 0){
            return emp.get(0);
        }else{
            return null;
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Employee employee){
        Employee savedEmployee = null;
        ResponseEntity<?> response = null;
        try{
            String hashPwd = passwordEncoder.encode(employee.getPassword());
            employee.setPassword(hashPwd);
            savedEmployee = employeeRepository.save(employee);
            if (savedEmployee.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.OK).body(1);
            }
        }catch(Exception e){
            response = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An exception occured due to " + e.getMessage());
        }
        return response;
    }
    
    @PutMapping("/update_register")
    public ResponseEntity<?> updateRegisteredUser(@RequestBody Employee employee){
        Employee savedEmployee = null;
        ResponseEntity<?> response = null;
        Employee saveEmployee = employeeService.getEmployeeById(employee.getId());

        saveEmployee.setId(employee.getId());
        if(employee.getDepartment() != null){
            saveEmployee.setDepartment(employee.getDepartment());
        }
        if(employee.getEmail() != null){
            saveEmployee.setEmail(employee.getEmail());
        }
        if(employee.getPhone() != null){
            saveEmployee.setPhone(employee.getPhone());
        }
        if(employee.getRole() != null){
            saveEmployee.setRole(employee.getRole());
        }
        if(employee.getUserName() != null){
            saveEmployee.setUserName(employee.getUserName());
        }

        try{
            if( employee.getPassword() != null ){
                String hashPwd = passwordEncoder.encode(employee.getPassword());
                saveEmployee.setPassword(hashPwd);
            }
            savedEmployee = employeeRepository.save(saveEmployee);
            if (savedEmployee.getId() > 0) {
                response = ResponseEntity.status(HttpStatus.OK).body(1);
            }
        }catch(Exception e){
            response = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An exception occured due to " + e.getMessage());
        }
        return response;
    }
}
