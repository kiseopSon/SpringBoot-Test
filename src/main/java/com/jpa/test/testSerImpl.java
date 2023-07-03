package com.jpa.test;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
@Transactional
public class testSerImpl implements testSer{
	
	private final testRepo repo;
	
	@Override
	public Optional<String> testText(String text) throws Exception {
		System.out.println("여긴 Ser");
		Optional<String> result = repo.realText(text);
		return result;
	}
	
	//회원가입
	@Override
	public Long join(testVO vo){
		validateDuplicateTestVO(vo);//중복회원 검증
		
		repo.save(vo);
		return vo.getId();
	}
	
	private void validateDuplicateTestVO(testVO vo) {
		repo.findByName(vo.getUsername()).ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}
	
	@Override
	public List<testVO> findTestVOs(){
		return repo.findByAll();
	}
	
	@Override
	public Optional<testVO> findOne(Long id){
		return repo.findById(id);
	}
}
