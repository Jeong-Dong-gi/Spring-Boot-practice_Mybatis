package com.testboard2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.testboard2.dto.MemberDTO;
import com.testboard2.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;

	/*
	 * 회원 등록 Form 페이지 + 회원 수정 Form
	 */
	@GetMapping("/member/memberWriteForm")
	public String memberWriteForm(
			@RequestParam(value="num", required=false) Integer num,
			Model model) {
			// required 옵션은 디폴트가 true이고, 써놓지 않으면 디폴트 값으로 true가 적용.
			// 기본값 true로 사용을 하는 경우 --> 보통 줄여서 @RequestParam("num")
		
		// 넘어온 값이 null인지 체크
		// num이 null인지 비교를 할 때 주의사항!
		// Primitive Type(원시 타입)인 int는 null일 수 없음. null이 필요한 경우 Integer 사용. 또는 0을 사용.
		// if( num != 0) {...}
		if(num != null) {
			System.out.println(num);
			// null이 아니라는 것은 파라미터 값으로 num 값이 넘어왔다는 것이므로 "수정" 처리라고 볼 수 있음.
			// 따라서, 여기에다 수정에 대한 처리 코드를 작성.
			
			// 수정 처리
			// 먼저, 넘어온 num 값에 대한 회원 정보를 데이터베이스에서 가져오고 --> 해당 회원 정보를 Form 페이지로 전달.
			MemberDTO m1 = memberService.getMemberOne( num );
			
			// DB에서 가져온 회원 정보가 없을 경우 --> 즉, m1 객체가 null인 경우.
			if(m1 == null) {
				
				// 1번 방식 : 리다이렉트(가장 심플)
				// return "redirect:/member/memberWriteForm";				
				
				// 2번 방식 : PrintWriter 사용
				// import 필요하고, 추가 처리 필요(아래 코드와 같은)
				// HttpServletResponse response () throws Exception {...
				
				// 3번 방식 : 특정 페이지(Error Message Page)로 데이터 값들을(Model을 사용) 보내서 출력
				
				
			}
			
			// 콘솔 출력
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
			
			// Form 페이지로 m1 객체를 전달 --> 모델(model)
			model.addAttribute("memberDTO", m1);
			model.addAttribute("formTitle", "Modification");
			
		}
		else {
			
			System.out.println("null 입니다.");
			// null이라는 것은 파라미터 값으로 num 값이 넘어온게 없다는 것이므로 "입력" 처리라고 볼 수 있음
			// 따라서, 여기에다 등록에 대한 처리 코드를 작성
			
			// 등록 처리(신규 회원)
			model.addAttribute("memberDTO", new MemberDTO());
			model.addAttribute("formTitle", "Registration");
		}
		
		return "member/memberWriteForm";
		
	}
	
	@PostMapping("/member/memberWriteOk")
	public String insertMember(MemberDTO m1) {

		try {
			// 등록 처리
			System.out.println(m1.getName());
			System.out.println(m1.getId());
			System.out.println(m1.getPhone());
			
			memberService.insertMember( m1 );
			
		}
		catch (Exception e) {
			// err
			
		}
		
		return "redirect:/";
		/*
		 * 그냥 리턴처리 하는 것과 redirect 리턴의 차이!
		 * 		1. 별 차이는 없다.
		 * 		2. 다만 redirect 경우는 다시 한번 해당 URL로 HTTP 요청을 넣는 형태.
		 */
	}
}
