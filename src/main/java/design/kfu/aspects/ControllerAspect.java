package design.kfu.aspects;

import design.kfu.entity.music.Person;
import design.kfu.service.PersonGiver;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Aspect
@Slf4j
public class ControllerAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void logController() { }

    @Around("logController()")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        try {
            Person person = PersonGiver.get();
            log.info(person.getName() + " (id:" + person.getId() + ") is going to start \"" + methodName + "\" method in \"" +
                    className + "\" class");
        } catch (NullPointerException ex) {
            log.info("Unauthorized user is going to start service \"" + methodName + "\" method in \"" +
                    className + "\" class");
        }
        Object forReturn = joinPoint.proceed();
        log.info("Ending execution");
        return forReturn;
    }

}
