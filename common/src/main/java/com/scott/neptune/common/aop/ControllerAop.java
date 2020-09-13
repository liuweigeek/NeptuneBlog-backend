package com.scott.neptune.common.aop;

import com.google.common.collect.Lists;
import com.scott.neptune.common.annotation.RedisLock;
import com.scott.neptune.common.constant.Constant;
import com.scott.neptune.common.exception.RestException;
import com.scott.neptune.common.util.HeaderUtils;
import com.scott.neptune.common.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2019/11/10 17:27
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
@ConditionalOnBean(name = "redisTemplate")
public class ControllerAop {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 防止重复提交 在关键操作controller上添加注解@RedisLock
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.scott.neptune.common.annotation.RedisLock)")
    public Object aroundLock(ProceedingJoinPoint pjp) throws Throwable {
        if (redisTemplate != null) {
            String redisLockKey = buildKeyFromRequest(pjp);
            // 30秒自动释放锁
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            RedisLock redisLock = method.getAnnotation(RedisLock.class);
            boolean getLockSuccess = redisTemplate.opsForValue().setIfAbsent(redisLockKey, 1, redisLock.keepTimeInMills(), TimeUnit.MILLISECONDS);
            if (getLockSuccess) {
                try {
                    return pjp.proceed();
                } finally {
                    // 执行完毕手动释放锁
                    redisTemplate.delete(redisLockKey);
                }
            } else {
                throw new RestException("操作进行中，请勿重复提交", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new RestException("系统中间件[redis]失败，请联系管理员以修复", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 通过请求与当前登录信息获取redis lock key
     *
     * @param pjp
     * @return
     */
    private String buildKeyFromRequest(ProceedingJoinPoint pjp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        StringBuilder sb = new StringBuilder("lock");
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String uri = request.getRequestURI();

            sb.append("-").append(uri);
            String token = HeaderUtils.get(request, Constant.Login.CURRENT_USER);
            sb.append("-").append(token);
        }
        String methodName = pjp.getSignature().getName();
        sb.append("-").append(methodName);
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            List<Object> newArgs = Lists.newArrayList();
            for (Object arg : args) {
                if (arg == null) {
                    continue;
                }
                if (arg instanceof MultipartFile) {
                    MultipartFile mf = (MultipartFile) arg;
                    newArgs.add(mf.getOriginalFilename());
                } else if (arg instanceof MultipartFile[]) {
                    MultipartFile[] mfArr = (MultipartFile[]) arg;
                    for (MultipartFile mf : mfArr) {
                        newArgs.add(mf.getOriginalFilename());
                    }
                } else {
                    newArgs.add(arg);
                }
            }
            if (!newArgs.isEmpty()) {
                sb.append("-").append(Arrays.toString(DigestUtils.md5Digest(JsonUtils.toJsonString(newArgs).getBytes())));
            }
        }
        return sb.toString();
    }

}
