package com.jpa.test;

import java.util.List;
import java.util.Optional;

public interface testSer {
	public Optional<String> testText(String text) throws Exception;
	public Long join(testVO vo);
	public List<testVO> findTestVOs();
	public Optional<testVO> findOne(Long id);
}
