package com.testboard2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testboard2.dto.MemberDTO;
import com.testboard2.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public void insertMember(MemberDTO memberDTO) {
		
		memberMapper.insertMember(memberDTO);
	}
	
}
