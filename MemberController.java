package com.testboard2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.testboard2.dto.MemberDTO;

@Controller
public class MemberController {

	@GetMapping("/member/memberWriteForm")
	public String memberWriteForm() {
		
		return "member/memberWriteForm";
		
	}
	
	@PostMapping("/member/memberWriteOk")
	public String registerMember(MemberDTO m1) {
		
		try {
			// 등록 처리
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
		}
		catch (Exception e) {
			// err
			
		}
		
		return "redirect:/member/memberWriteForm";
		/*
		 * 그냥 리턴처리 하는 것과 redirect 리턴의 차이!
		 * 		1. 별 차이는 없다.
		 * 		2. 다만 redirect 경우는 다시 한번 해당 URL로 HTTP 요청을 넣는 형태.
		 */
	}
}
