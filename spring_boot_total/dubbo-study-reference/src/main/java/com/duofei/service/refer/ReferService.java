package com.duofei.service.refer;

import com.duofei.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务引入
 * @author duofei
 * @date 2020/1/17
 */
@Service
public class ReferService {

    @Reference
    private UserService userService;

    public List<String> getDefaultUsernames(){
        return userService.getDefaultUserNames();
    }
}
