package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {

//    private DataSource dataSource;

    private final MemberRepository memberRepository;

//    @PersistenceContext ///jpa 와 jpQL의 db연결 택 1 현 db환경 구축안해서 서비스 안됨
//    private EntityManager entityManager;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    @Autowired
//    public SpringConfig(DataSource dataSource){//jdbcTemplate 방식의 db연결
//        this.dataSource = dataSource;
//    }
//    @Autowired
//    public SpringConfig(EntityManager entityManager){//jpa 와 jpQL의 db연결 택 2
//        this.entityManager = entityManager;
//    }

    @Bean
    public MemberService memberService(){
        // return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }
//    @Bean
//    public MemberRepository memberRepository(){
        //return new MemoryMemberRepository();
        //return new JdbcTemplateMemberRepository(dataSource);//jdbcTemplate 방식의 db연결
       // return new JpaMemberRepository(entityManager);//jpa 와 jpQL의 db연결
//    }
}
