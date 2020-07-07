package com.duofei.service.export;

import com.duofei.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 导出服务
 * @author duofei
 * @date 2020/1/17
 */
@Service
public class ExportService {

    @Resource
    private UserService userService;

    public List<String> getDefaultUserNames(){
        return userService.getDefaultUserNames();
    }

}
