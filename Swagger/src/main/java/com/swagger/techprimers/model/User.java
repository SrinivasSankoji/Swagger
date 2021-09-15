package com.swagger.techprimers.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(notes  = "Name of the User")
	private String userName;
	
	@ApiModelProperty(notes = "Salary of the User")
	private Long salary;
}
