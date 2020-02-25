package com.duofei.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户服务实现类
 * @author duofei
 * @date 2020/1/16
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Override
    public List<String> getDefaultUserNames() {
        return Stream.of("num1", "num2").collect(Collectors.toList());
    }
}
