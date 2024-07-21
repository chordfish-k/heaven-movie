package com.chord.lsr.controller.user;

import cn.hutool.core.util.StrUtil;
import com.chord.lsr.client.UserClient;
import constant.JwtClaimsConstant;
import com.chord.lsr.context.UserContext;
import com.chord.lsr.handler.NonStaticResourceHttpRequestHandler;
import com.chord.lsr.pojo.dto.MovieQueryDTO;
import com.chord.lsr.pojo.entity.Movie;
import com.chord.lsr.pojo.entity.User;
import com.chord.lsr.pojo.result.PageResult;
import com.chord.lsr.pojo.result.Result;
import com.chord.lsr.pojo.vo.CategoryVO;
import com.chord.lsr.properties.JwtProperties;
import com.chord.lsr.properties.MediaProperties;
import com.chord.lsr.service.*;
import com.chord.lsr.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/user/movie")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieLikeService movieLikeService;
    @Autowired
    private MediaProperties mediaProperties;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserClient userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    /**
     * 根据id查询电影
     * @param id
     * @return
     */
    @GetMapping("/y/{id}")
    public Result<Movie> getById(@PathVariable Long id) {
        System.out.println(mediaProperties.imagePath);
        Movie movie = movieService.getById(id);
        return Result.success(movie);
    }

    /**
     * 分页查询电影
     * @param pageSize
     * @param currentPage
     * @return
     */
    @GetMapping("/y/page/{pageSize}/{currentPage}")
    public Result<PageResult> queryPage(@PathVariable Integer pageSize, @PathVariable Integer currentPage) {
        PageResult page = movieService.queryPage(currentPage, pageSize);
        return Result.success(page);
    }


    /**
     * 条件查询电影
     */
    @GetMapping("/y")
    public Result<PageResult> query(MovieQueryDTO movieQueryDTO) {
        log.info("查询电影：{}", movieQueryDTO);
        PageResult page = movieService.query(movieQueryDTO);
        return Result.success(page);
    }

    /**
     * 增加播放量并添加历史记录
     * @param id
     * @return
     */
    @PutMapping("/n/view/{id}")
    @Transactional
    public Result view(@PathVariable Long id) {
        // 该视频观看次数+1
        Movie movie = movieService.getById(id);
        Movie movieUpdated = Movie.builder()
                .id(id)
                .views(movie.getViews() + 1)
                .build();
        movieService.updateById(movieUpdated);
        // 添加用户的观影记录
        userService.insertHistory(UserContext.getCurrentId(), movie.getId());
        return Result.success();
    }


    /**
     * 查询用户是否对某电影点过赞
     * @param id
     * @return
     */
    @GetMapping("/n/like/{id}")
    public Result<Integer> isLike(@PathVariable Long id) {
        log.info("查询用户是否对某电影点过赞：{}, {}", id, UserContext.getCurrentId());
        return Result.success(UserContext.getCurrentId() != null ? movieLikeService.isLike(id) : 0);
    }

    /**
     * 反转某用户对某电影的点赞
     * @param id
     * @return
     */
    @PutMapping("/n/like/{id}")
    public Result like(@PathVariable Long id) {
        // 该视频观点赞数+1
        movieLikeService.likeReverse(id);
        return Result.success();
    }

    /**
     * 查询电影分类列表
     * @param type
     * @return
     */
    @GetMapping("/y/category")
    public Result<CategoryVO> list(Integer type) {
        CategoryVO categoryVO = categoryService.list(type);
        return Result.success(categoryVO);
    }


    /**
     * 获取视频
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping("/n/get/{id}")
    @Transactional
    public void videoPreview(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("获取视频流：{}", id);

        // 1.获取请求头中的用户信息
        String userInfo = request.getHeader("user-info");
        System.out.println("userinfo: " + userInfo);
        // 2.判断是否为空
        if (StrUtil.isNotBlank(userInfo)) {
            // 不为空，保存到ThreadLocal
            Long userId = Long.valueOf(userInfo);
            UserContext.setCurrentId(userId);

            // 查询员工和电影是否为vip
            Movie movie = movieService.getById(id);
            Integer vipMovie = movie.getVip();
            User user = userService.getById(userId).getData();
            Integer vipUser = user.getVip();
            log.info("当前用户id：{}", userId);
            log.info("电影vip:{}, 用户vip:{}", vipMovie, vipUser);
            //普通会员无法获取vip视频
            if (vipMovie==1 && vipUser==0) {
                response.sendError(403, "非法访问");
                return;
            }

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


        } else {
            //token不通过，响应401状态码
            response.setStatus(401);
        }

    }
}
