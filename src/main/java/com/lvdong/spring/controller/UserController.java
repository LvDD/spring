package com.lvdong.spring.controller;

import com.alibaba.fastjson.JSON;
import com.lvdong.spring.model.User;
import com.lvdong.spring.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户控制层
 * Created by lvdong on 2016/9/28.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @RequestMapping("/showUser")
    public ModelAndView showUser(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        logger.info("查询所有用户信息");
        StringBuffer requestURL = request.getRequestURL();
        logger.info("requestURL : " + requestURL.toString());

        List<User> allUser = userService.getAllUser();
        logger.info("allUser : " + JSON.toJSONString(allUser));
        modelAndView.addObject("userList", allUser);
        modelAndView.setViewName("showUser");
        return modelAndView;
    }
}
