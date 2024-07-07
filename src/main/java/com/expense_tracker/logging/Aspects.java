package com.expense_tracker.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {

	Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Before("execution(* com.expense_tracker..*Controller.*(..))")
	public void beforeAnyControllerMethod(JoinPoint joinPoint) {
		logger.info("... {} {} start...", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
	}

	@After("execution(* com.expense_tracker..*Controller.*(..))")
	public void afterAnyControllerMethod(JoinPoint joinPoint) {
		logger.info("... {} {} end...", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
	}

	@Before("execution(* com.expense_tracker..*Service.*(..))")
	public void beforeAnyServiceMethod(JoinPoint joinPoint) {
		logger.info("... {} {} start...", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
	}

	@After("execution(* com.expense_tracker..*Service.*(..))")
	public void afterAnyServiceMethod(JoinPoint joinPoint) {
		logger.info("... {} {} end...", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
	}

}
