package com.example.Testify.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import com.example.Testify.DTO.Admin;
import com.example.Testify.Repository.AdminRepository;


@Repository
public class AdminDAO {

	@Autowired
	AdminRepository adminRepository;
	
	public List<Admin> saveMultipleAdmin(List<Admin> admin) {
		return adminRepository.saveAll(admin);
	}
	public Optional<Admin> getAdminById(int id) {
		return adminRepository.findById(id);
	}
	public List<Admin> getAllAdmins(){
		return adminRepository.findAll();
	}
	public void deleteAdmin(int id) {
		adminRepository.deleteById(id);
	}
//	@SuppressWarnings("unchecked")
//	public Optional<Admin> getAdminById(Admin admin) {
//		return adminRepository.findAll(id);
//	}
	public Admin saveAdmin(Admin admin) {
		return adminRepository.save(admin);
	}
	
	public Admin findAdminByName(String name) {
		return adminRepository.findAdminByName(name);
		
	}
}