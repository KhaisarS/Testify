package com.example.Testify.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Testify.DTO.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

	public Optional<Admin> findById(int id);

	public void deleteById(int id);
	
	public Admin findAdminByName(String name);

	//public Admin findAdmin(String name);
}

