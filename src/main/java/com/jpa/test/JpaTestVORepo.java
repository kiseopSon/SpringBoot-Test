package com.jpa.test;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaTestVORepo implements testRepo{
	private final EntityManager em;
	
	@Override
	public Optional<String> realText(String text) throws Exception {
		System.out.println("여기는 jpa_realText");
		return Optional.ofNullable(text);
	}

	@Override
	public testVO save(testVO vo) {
		System.out.println("여기는 jpa_save : " + vo);
		em.persist(vo);
		return vo;
	}

	@Override
	public Optional<testVO> findById(Long id) {
		System.out.println("여기는 jpa_findById : " + id);
		return Optional.ofNullable(em.find(testVO.class, id));
	}

	@Override
	public Optional<testVO> findByName(String name) {
		//jpql = sql언어
		System.out.println("여기는 jpa_findByName : " + name);
		List<testVO> result = em.createQuery("select m from testVO m where m.name = :name", testVO.class)
		.setParameter("name", name)
		.getResultList();
		return result.stream().findAny();
	}

	@Override
	public List<testVO> findByAll() {
		System.out.println("여기는 jpa_findByAll");
		return em.createQuery("select m from testVO m", testVO.class).getResultList();
	}
	
}
