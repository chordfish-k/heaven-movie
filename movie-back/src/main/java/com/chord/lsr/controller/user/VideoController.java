package com.chord.lsr.controller.user;

import com.chord.lsr.constant.JwtClaimsConstant;
import com.chord.lsr.context.UserContext;
import com.chord.lsr.handler.NonStaticResourceHttpRequestHandler;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.entity.User;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.pojo.vo.VideoVO;
import com.chord.lsr.properties.JwtProperties;
import com.chord.lsr.properties.MediaProperties;
import com.chord.lsr.service.MovieService;
import com.chord.lsr.service.UserHistoryService;
import com.chord.lsr.service.UserService;
import com.chord.lsr.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user/video")
@Slf4j
public class VideoController {
    @Autowired
    private MediaProperties mediaProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private MovieService movieService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserHistoryService userHistoryService;
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    @GetMapping("/getUrl/{id}")
    @Transactional
    public Result<VideoVO> videoPreview(@PathVariable Long id) throws Exception {
        // 查询员工和电影是否为vip
        Long userId = UserContext.getCurrentId();
        Movie movie = movieService.getById(id);
        Integer vipMovie = movie.getVip();
        User user = userService.getById(userId);
        Integer vipUser = user.getVip();
        log.info("当前用户id：{}", userId);
        log.info("电影vip:{}, 用户vip:{}", vipMovie, vipUser);
        //普通会员无法获取vip视频
        if (vipMovie==1 && vipUser==0) {
            return Result.error("非法访问");
        }

        // 该视频观看次数+1
        Movie movieUpdated = Movie.builder()
                .id(id)
                .views(movie.getViews() + 1)
                .build();
        movieService.updateById(movieUpdated);

        // 添加用户的观影记录
        userHistoryService.insertHistory(movie.getId());

        // 获取视频路径
        String realPath = mediaProperties.videoPath + id + ".mp4";
        String rickPath = mediaProperties.videoPath + "rick.mp4";

        // 获取视频
        Path filePath = Paths.get(realPath );
        if (!Files.exists(filePath)) {
            log.info("不存在的文件: {}", realPath);
            // 改成rick
            filePath = Paths.get(rickPath);
        }
        String mimeType = Files.probeContentType(filePath);

        return Result.success(new VideoVO(filePath.getFileName().toString(), mimeType));
    }

    @GetMapping("/d/{id}")
    @Transactional
    public void videoPreview(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String token = request.getHeader(jwtProperties.getTokenName());
        log.info("获取视频流：{}, {}", id, token);
        //校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            // 保存id数据
            UserContext.setCurrentId(userId);

            // 查询员工和电影是否为vip
            Movie movie = movieService.getById(id);
            Integer vipMovie = movie.getVip();
            User user = userService.getById(userId);
            Integer vipUser = user.getVip();
            log.info("当前用户id：{}", userId);
            log.info("电影vip:{}, 用户vip:{}", vipMovie, vipUser);
            //普通会员无法获取vip视频
            if (vipMovie==1 && vipUser==0) {
                response.sendError(403, "非法访问");
                return;
            }

            // 该视频观看次数+1
            Movie movieUpdated = Movie.builder()
                    .id(id)
                    .views(movie.getViews() + 1)
                    .build();
            movieService.updateById(movieUpdated);

            // 添加用户的观影记录
            userHistoryService.insertHistory(movie.getId());

            // 获取视频路径
            String realPath = mediaProperties.videoPath + id + ".mp4";
            String rickPath = mediaProperties.videoPath + "rick.mp4";

            // 获取视频
            Path filePath = Paths.get(realPath );
            if (!Files.exists(filePath)) {
                log.info("不存在的文件: {}", realPath);
                // 改成rick
                filePath = Paths.get(rickPath);
            }
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);


        } catch (Exception ex) {
            //token不通过，响应401状态码
            response.setStatus(401);
        }

    }
}
