package com.example.Testify.service;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Testify.DAO.AdminDAO;
import com.example.Testify.DTO.Admin;
import com.example.Testify.DTO.TokenReqRes;
import com.example.Testify.Exception.IdNotFoundException;
import com.example.Testify.Exception.PhoneNumberNotValidException;
import com.example.Testify.util.JWTToken;
import com.example.Testify.util.ResponseStructure;

@Service
public class AdminService {
	@Autowired
	AdminDAO adminDAO;
	@Autowired
	private JWTToken jwtToken;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(Admin admin) {
		ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
		
		String hashedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
		admin.setPassword(hashedPassword);
		long phone = admin.getPhone_no();
		if(phone>5678999999L && phone<9998880000L) {
		if ((adminDAO.saveAdmin(admin).getId()>0)) {
			responseStructure.setStatus(HttpStatus.CREATED.value());
			responseStructure.setMessage("Admin saved successfully");
			responseStructure.setData(adminDAO.saveAdmin(admin));

			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.CREATED);
		}
		else {
			responseStructure.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseStructure.setMessage("Admin not saved due to internal server error");
			//responseStructure.setData(adminDAO.saveAdmin(admin));

			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.CREATED);
		}}
		else {
			throw new PhoneNumberNotValidException("entered phone number "+ phone + " is not valid");
		}
		
	}

	 public ResponseEntity<ResponseStructure<List<Admin>>> saveMultipleAdmin(List<Admin> adminList) {
	        ResponseStructure<List<Admin>> responseStructure = new ResponseStructure<List<Admin>>();
	        List<Admin> encryptedAdmins = new ArrayList<Admin>();

	        for (Admin admin : adminList) {
	            long phone = admin.getPhone_no();
	            if (phone >= 5678999999L && phone <= 9998880000L) {
	                // Encrypt password for each Admin
	                String hashedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
	                admin.setPassword(hashedPassword);
	                encryptedAdmins.add(admin);
	            } else {
	                throw new PhoneNumberNotValidException("Entered phone number " + phone + " is not valid");
	            }
	        }

	        List<Admin> savedAdmins = adminDAO.saveMultipleAdmin(encryptedAdmins);

	        responseStructure.setStatus(HttpStatus.CREATED.value());
	        responseStructure.setMessage("Admin saved successfully");
	        responseStructure.setData(savedAdmins);

	        return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Admin>> getAdminById(int id) {
		Optional<Admin> opt = adminDAO.getAdminById(id);

		if (opt.isPresent()) {
			ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();

			responseStructure.setStatus(HttpStatus.OK.value());
			responseStructure.setMessage("Admin details got successfully");
			responseStructure.setData(opt.get());

			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Given ID " + id + " is not found");
		}

	}

	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmins() {

		ResponseStructure<List<Admin>> responseStructure = new ResponseStructure<List<Admin>>();

		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMessage("All Admin details found succesfully");
		responseStructure.setData(adminDAO.getAllAdmins());

		return new ResponseEntity<ResponseStructure<List<Admin>>>(responseStructure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Admin>> updateAdmin(int id, Admin admin) {
		Optional<Admin> opt = adminDAO.getAdminById(id);

		if (opt.isPresent()) {
			Admin admin1 = opt.get();

			admin1.setName(admin.getName());
			admin1.setEmail(admin.getEmail());
			admin1.setPassword(admin.getPassword());
			admin1.setPhone_no(admin.getPhone_no());

			ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();
			responseStructure.setStatus(HttpStatus.ACCEPTED.value());
			responseStructure.setMessage("Updated successfully");
			responseStructure.setData(adminDAO.saveAdmin(admin1));

			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.ACCEPTED);
		} else {
			throw new IdNotFoundException("given id " + id + " doesnt exist");
		}
	}

	public ResponseEntity<ResponseStructure<Admin>> deleteById(int id) {
		Optional<Admin> opt = adminDAO.getAdminById(id);

		if (opt.isPresent()) {
			adminDAO.deleteAdmin(id);

			ResponseStructure<Admin> responseStructure = new ResponseStructure<Admin>();

			responseStructure.setStatus(HttpStatus.NO_CONTENT.value());
			// responseStructure.setMessage("deleted successfully");

			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException();
		}
	}

	public ResponseEntity<Object> generateToken(TokenReqRes tokenReqRes){
		Admin admin=adminDAO.findAdminByName(tokenReqRes.getName());
		if(admin==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("sorry, user doesn't exist");
		}
		if(new BCryptPasswordEncoder().matches(tokenReqRes.getPassword(), admin.getPassword())) {
			String token=jwtToken.generateToken(tokenReqRes.getName());
			tokenReqRes.setToken(token);
			tokenReqRes.setExporationTime("60 sec");
			return ResponseEntity.ok(tokenReqRes);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("password doesn't match.  verify");
		}
		
	}
	
	public ResponseEntity<Object> validateToken(TokenReqRes tokenReqRes){
		return ResponseEntity.ok(jwtToken.validateToken(tokenReqRes.getToken()));
	}
	

	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmin(String token) {

		ResponseStructure<List<Admin>> responseStructure = new ResponseStructure<List<Admin>>();
		if(token==null) {
		responseStructure.setStatus(HttpStatus.UNAUTHORIZED.value());
		responseStructure.setMessage("Token is required to proceed");
		return new ResponseEntity<ResponseStructure<List<Admin>>>(responseStructure, HttpStatus.UNAUTHORIZED);

		}else {
			String realToken=token.substring(7);
			String tokenCheckResult=jwtToken.validateToken(realToken);
			if (tokenCheckResult.equalsIgnoreCase("valid")) {

				responseStructure.setStatus(HttpStatus.OK.value());
				responseStructure.setMessage("All Admin details found succesfully");
				responseStructure.setData(adminDAO.getAllAdmins());

				return new ResponseEntity<ResponseStructure<List<Admin>>>(responseStructure, HttpStatus.OK);
			}else {
				responseStructure.setStatus(HttpStatus.UNAUTHORIZED.value());
				responseStructure.setMessage("unauthorized due to: "+tokenCheckResult);
				return new ResponseEntity<ResponseStructure<List<Admin>>>(responseStructure, HttpStatus.UNAUTHORIZED);
 
			}
		}
		
	}
	
}
