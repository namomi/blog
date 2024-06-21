package com.namomi.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
	private String email;
	private String password;
}
