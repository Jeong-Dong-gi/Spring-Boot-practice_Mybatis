package com.testboard2.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.testboard2.dto.MemberDTO;

@Mapper
public interface MemberMapper {

	public void insertMember( MemberDTO memberDTO );
	
	public MemberDTO selectMemberOne(int num);
	
	public void updateMember(MemberDTO memberDTO);
}
