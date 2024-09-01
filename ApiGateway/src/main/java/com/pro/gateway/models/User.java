package com.pro.gateway.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="micro_users")
public class User{
	@Id
	@Column(value="ID")
	private String userId;
	private String username;
	@Column(value="NAME")
	private String name;
	@Column(value="EMAIL")
	private String email;
	@Column(value="ABOUT")
	private String about;
	private String password;
}
