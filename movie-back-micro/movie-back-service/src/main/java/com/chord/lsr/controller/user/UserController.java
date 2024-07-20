package com.chord.lsr.controller.user;

import com.chord.lsr.constant.JwtClaimsConstant;
import com.chord.lsr.pojo.dto.UserLoginDTO;
import com.chord.lsr.pojo.dto.UserRegisterDTO;
import com.chord.lsr.pojo.entity.User;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.pojo.vo.UserLoginVO;
import com.chord.lsr.properties.JwtProperties;
import com.chord.lsr.service.UserService;
import com.chord.lsr.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        UserLoginVO userLoginVO = new UserLoginVO();
        BeanUtils.copyProperties(user, userLoginVO);
        userLoginVO.setToken(token);

        return Result.success(userLoginVO);
    }

    /**
     * 新建用户
     * @param userAddDTO
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userAddDTO) {
        log.info("用户注册：{}", userAddDTO);

        userService.register(userAddDTO);

        return Result.success();
    }

    /**
     * 升级VIP
     * @return
     */
    @PutMapping("/privilege/upgrade")
    public Result upgrade() {
        userService.upgrade();
        return Result.success();
    }

    /**
     * 退出VIP
     * @return
     */
    @PutMapping("/privilege/downgrade")
    public Result downgrade() {
        userService.downgrade();
        return Result.success();
    }
}
