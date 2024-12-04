package com.example.Testify.DTO;


import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_seq")
//	@GenericGenerator(name = "admin_seq", strategy = "com.ecommerceShoppingsite.util.StringPrefixedSequenceIdGenerator", parameters = {
//			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
//			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ADMIN_"),
//			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%02d") })
	private int id;
	@NotEmpty(message = "Name may not be empty")
	@Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long") 
	private String name;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotNull(message = "phone number required")
	@Min(value = 5678999999L)
	@Max(value = 9998880000L)
	private long phone_no;

}
