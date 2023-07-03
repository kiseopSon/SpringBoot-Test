package com.jpa.test;

import java.util.List;
import java.util.Optional;

public interface testRepo {
	public Optional<String> realText(String text) throws Exception;
	
	testVO save(testVO vo);
	Optional<testVO> findById(Long id);
	Optional<testVO> findByName(String name);
	List<testVO> findByAll();
}
