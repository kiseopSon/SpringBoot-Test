package com.jpa.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

//@Repository
public class testRepoImpl implements testRepo{
	
	private static Map<Long, testVO> store = new HashMap<>();
	private static long seq = 0L;

	@Override
	public Optional<String> realText(String text) {
		System.out.println("여기는 repo");
		return Optional.ofNullable(text);
	}

	@Override
	public testVO save(testVO vo) {
		System.out.println("여기는 testVO");
		vo.setId(++seq);
		store.put(vo.getId(), vo);
		return vo;
	}

	@Override
	public Optional<testVO> findById(Long id) {
		System.out.println("여기는 findById");
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Optional<testVO> findByName(String name) {
		System.out.println("여기는 findByName");
		return store.values().stream().filter(vo -> vo.getUsername().equals(name)).findAny();
	}

	@Override
	public List<testVO> findByAll() {
		System.out.println("여기는 findByAll");
		return new ArrayList<>(store.values());
	}
	
	
	public void clear() {
		store.clear();
	}
}
