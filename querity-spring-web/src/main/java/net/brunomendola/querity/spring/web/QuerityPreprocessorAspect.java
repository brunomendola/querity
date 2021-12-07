package net.brunomendola.querity.spring.web;

import net.brunomendola.querity.api.Condition;
import net.brunomendola.querity.api.Querity;
import net.brunomendola.querity.api.Query;
import net.brunomendola.querity.api.QueryPreprocessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
public class QuerityPreprocessorAspect {

  private final ApplicationContext applicationContext;

  public QuerityPreprocessorAspect(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void anyRestControllerMethod() {
  }

  @Pointcut("execution(public * *(..))")
  public static void anyPublicMethod() {
  }

  @Around("anyRestControllerMethod() && anyPublicMethod() && @annotation(withQueryPreprocessor) && args(query)")
  public Object applyPreprocessorsToQuery(ProceedingJoinPoint pjp, WithQueryPreprocessor withQueryPreprocessor, Query query) throws Throwable {
    Query preprocessedQuery = null;
    if (query != null) {
      List<QueryPreprocessor> preprocessors = getQueryPreprocessors(withQueryPreprocessor);
      Query queryWithPreprocessors = query.toBuilder()
          .preprocessors(preprocessors)
          .build();
      preprocessedQuery = queryWithPreprocessors.preprocess();
    }
    return pjp.proceed(new Object[]{preprocessedQuery});
  }

  @Around("anyRestControllerMethod() && anyPublicMethod() && @annotation(withQueryPreprocessor) && args(condition)")
  public Object applyPreprocessorsToCondition(ProceedingJoinPoint pjp, WithQueryPreprocessor withQueryPreprocessor, Condition condition) throws Throwable {
    Condition preprocessedCondition = null;
    if (condition != null) {
      List<QueryPreprocessor> preprocessors = getQueryPreprocessors(withQueryPreprocessor);
      Query queryWithPreprocessors = Querity.query()
          .filter(condition)
          .preprocessors(preprocessors)
          .build();
      Query preprocessedQuery = queryWithPreprocessors.preprocess();
      preprocessedCondition = preprocessedQuery.getFilter();
    }
    return pjp.proceed(new Object[]{preprocessedCondition});
  }

  private List<QueryPreprocessor> getQueryPreprocessors(WithQueryPreprocessor withQueryPreprocessor) {
    return Arrays.stream(withQueryPreprocessor.beanName())
        .map(beanName -> applicationContext.getBean(beanName, QueryPreprocessor.class))
        .collect(Collectors.toList());
  }
}
