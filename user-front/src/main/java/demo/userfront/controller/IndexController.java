package demo.userfront.controller;

import demo.userfront.exception.BusinessException;
import demo.userfront.service.UserService;
import demo.userfront.vo.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(){
        userService.getUser("1");
        return "index";
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseStatus onBusinessException(BusinessException e){

        return ResponseStatus.STATUS_ERROR;
    }
}
