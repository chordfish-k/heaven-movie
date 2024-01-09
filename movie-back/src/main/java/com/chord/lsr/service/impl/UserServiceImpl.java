package com.chord.lsr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chord.lsr.constant.MessageConstant;
import com.chord.lsr.context.UserContext;
import com.chord.lsr.exception.AccountNotFoundException;
import com.chord.lsr.exception.PasswordErrorException;
import com.chord.lsr.mapper.UserMapper;
import com.chord.lsr.pojo.dto.UserLoginDTO;
import com.chord.lsr.pojo.dto.UserRegisterDTO;
import com.chord.lsr.pojo.entity.User;
import com.chord.lsr.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO userLoginDTO) {
        String mail = userLoginDTO.getMail();
        String pwd = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("mail", mail);
        User user = userMapper.selectOne(qw);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //需要进行md5加密，然后再进行比对
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        if (!pwd.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //3、返回实体对象
        return user;
    }

    public void register(UserRegisterDTO userAddDTO) {
        String pwd = userAddDTO.getPassword();
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());

        User user = User.builder()
                .mail(userAddDTO.getMail())
                .name(userAddDTO.getName())
                .password(pwd)
                .build();

        userMapper.insert(user);
    }

    public void upgrade() {
        User user = userMapper.selectById(UserContext.getCurrentId());
        if (user == null) throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        user.setVip(1);
        userMapper.updateById(user);
    }


    public void downgrade() {
        User user = userMapper.selectById(UserContext.getCurrentId());
        if (user == null) throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        user.setVip(0);
        userMapper.updateById(user);
    }


    public User getById(Integer id) {
        User user = userMapper.selectById(UserContext.getCurrentId());
        if (user == null) throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        return user;
    }
}
