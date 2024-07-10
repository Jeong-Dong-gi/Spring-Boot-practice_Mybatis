package com.testboard2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.testboard2.dto.MemberDTO;
import com.testboard2.mapper.MemberMapper;

@SpringBootTest
public class MapperTests {

	@Autowired
	private MemberMapper memberMapper;
	
	@Test
	public void testInsert() {
		
		MemberDTO m1 = new MemberDTO();
		
		m1.setName("장발순");
		m1.setId("mr.soon");
		m1.setPhone("010-111-7777");
		
		System.out.println(m1);
		memberMapper.insertMember(m1);
	}
}
