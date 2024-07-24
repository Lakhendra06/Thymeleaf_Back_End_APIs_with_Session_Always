package com.tms.repository;

import com.tms.model.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long>{
    
    List<Employee> findByEmail(String email);
    
    @Query(value = "SELECT count(*) FROM employees",nativeQuery = true)
	public int getEmployeesCount();
}
