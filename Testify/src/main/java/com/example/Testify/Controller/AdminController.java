package com.example.Testify.Controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Testify.DTO.Admin;
import com.example.Testify.DTO.TokenReqRes;
import com.example.Testify.service.AdminService;
import com.example.Testify.util.ResponseStructure;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/admin")
@Api(value = "Admin CRUD", description = "it is about admin details")
public class AdminController {

	@Autowired
	AdminService adminService;

	@ApiOperation(value = "Save Admin", notes = "saves the admin details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Admin>> saveAdmin(@RequestBody Admin admin) {
		return adminService.saveAdmin(admin);
	}

	@ApiOperation(value = "Save Admin", notes = "saves the admin details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@PostMapping("/saveMultiple")
	public ResponseEntity<ResponseStructure<List<Admin>>> saveMultipleAdmin(@RequestBody List<Admin> admin) {
		return adminService.saveMultipleAdmin(admin);
	}

	@ApiOperation(value = "Get Admin By Id", notes = "get the admin details by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Admin>> getAdminById(@PathVariable int id) {
		return adminService.getAdminById(id);
	}

	@ApiOperation(value = "All Admin", notes = "Getting all the admin details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@GetMapping()
	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmins(@RequestHeader(value="Authorization",required=false) String token ) {
		return adminService.getAllAdmins();
	}

	@ApiOperation(value = "Update Admin", notes = "Updates the admin details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@PutMapping("{id}")
	public ResponseEntity<ResponseStructure<Admin>> updateAdminDetails(@PathVariable int id, @RequestBody Admin admin) {
		return adminService.updateAdmin(id, admin);
	}

	@ApiOperation(value = "Delete Admin", notes = "delete the admin details")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseStructure<Admin>> deleteAdminById(@PathVariable int id) {
		return adminService.deleteById(id);
	}

	@ApiOperation(value = "Get Admin By Id", notes = "get the admin details by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "successful"),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 403, message = "Access Forbidden"),
			@ApiResponse(code = 404, message = "Given id not found"),
			@ApiResponse(code = 405, message = "Method Not Allowed"), })
	@PostMapping("/get")
	public ResponseEntity<ResponseStructure<Admin>> getAdminByIdBYPOST(@RequestBody Admin admin) {
		int i = admin.getId();
		ResponseEntity<ResponseStructure<Admin>> record = adminService.getAdminById(i);
		return adminService.getAdminById(i);
	}

	@PostMapping("/generate-token")
	public ResponseEntity<Object> generateToken(@RequestBody TokenReqRes tokenReqRes) {
		return adminService.generateToken(tokenReqRes);
	}

	@PostMapping("/validate-token")
	public ResponseEntity<Object> validateToken(@RequestBody TokenReqRes tokenReqRes) {
		return adminService.validateToken(tokenReqRes);
	}

	@GetMapping("/get-all-admins")
	public ResponseEntity<ResponseStructure<List<Admin>>> getAllAdmin(@RequestHeader(value="Authorization",required=false) String token ) {
		return adminService.getAllAdmin(token);
		
	}

}