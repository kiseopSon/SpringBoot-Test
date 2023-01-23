package hello.hellospring.aop;

import hello.hellospring.mapper.CustomAnotationTest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {//각각 모든 메서드에 타임 걸기
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("start : " + joinPoint.toString());
        try {
            return joinPoint.proceed();//다음 메서드 실행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("end : " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }

/*   @Around("@anotation("CustomAnotationTest")")

  public Object executer(ProceedingJoinPoint joinPoint){
        System.out.println("test");
        return joinPoint.toString();
    }*/
}
