package com.duofei.interceptor;

import com.duofei.entity.Teacher;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author duofei
 * @date 2019/12/12
 */
@Component("keyGeneratorForTeacher")
public class KeyGeneratorForTeacher implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if(params.length != 1){
            throw new IllegalArgumentException("i just need one param!");
        }
        if(params[0] instanceof Teacher){
            Teacher teacher = (Teacher) params[0];
            return teacher.getName();
        }
        return method.getName() + Arrays.stream(params)
                .map(Object::toString).collect(Collectors.joining(","));
    }
}
