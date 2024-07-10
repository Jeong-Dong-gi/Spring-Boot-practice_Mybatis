package com.testboard2.dto;

public class MemberDTO {

	// idx
	// private Long idx;
	private String name;
	private String id;
	private String phone;

	public MemberDTO() {}
	
	public MemberDTO(String name, String id, String phone) {
		this.name = name;
		this.id = id;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "MemberDTO [name=" + name + ", id=" + id + ", phone=" + phone + "]";
	}
	
}
