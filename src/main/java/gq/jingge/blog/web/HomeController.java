package gq.jingge.blog.web;

import com.google.gson.Gson;
import gq.jingge.blog.config.Constants;
import gq.jingge.blog.dao.LoveRepository;
import gq.jingge.blog.domain.Love;
import gq.jingge.blog.domain.Post;
import gq.jingge.blog.domain.vo.LoveForm;
import gq.jingge.blog.service.AppSetting;
import gq.jingge.blog.service.PostService;
import gq.jingge.blog.util.DTOUtil;
import org.json.JSONArray;
import org.python.icu.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by wangyunjing on 2017/11/30.
 */
@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private LoveRepository loveRepository;

    @Autowired
    private AppSetting appSetting;

    private static SimpleDateFormat FORMATDATE = new SimpleDateFormat("yyyy,MM,dd");

    @RequestMapping(value = "", method = GET)
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {
        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.getAllPublishedPostsByPage(page, appSetting.getPageSize());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);

        return "home/index";
    }

    @RequestMapping(value = "about", method = GET)
    public String about(Model model) {
        Post post = postService.getPublishedPostByPermalink(Constants.ABOUT_PAGE_PERMALINK);

        if (post == null) {
            post = postService.createAboutPage();
        }

        model.addAttribute("about", post);
        return "home/about";
    }

    @RequestMapping(value = "love", method = GET)
    public String love(Model model) throws IOException {

        List<LoveForm> loveForms = new ArrayList<>();
        List<Love> loves = loveRepository.findAll();
        for (Love love : loves) {
            LoveForm loveForm = DTOUtil.map(love, LoveForm.class);
            Date nowDate = love.getCreatedAt();
            String dateStr = FORMATDATE.format(nowDate);
            loveForm.setFormatDate(dateStr);

            loveForms.add(loveForm);
        }

        model.addAttribute("loves", loveForms);
        return "home/love";
    }

    @RequestMapping(value = "love2", method = GET)
    @ResponseBody
    public ResponseEntity<String> love2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //数据
        List<Post> loves = new ArrayList<>();
        Post post = postService.getPublishedPostByPermalink(Constants.ABOUT_PAGE_PERMALINK);
        loves.add(post);

    //    Gson gson = new Gson();
    //    String result = gson.toJson(post);
        String result = "{\"username\":\"张三\",\"password\":\"123\",\"money\":500,\"book\":{\"id\":0,\"name\":\"三国演义\",\"price\":0,\"author\":\"罗贯中\"}}";

        //前端传过来的回调函数名称
        String callback = request.getParameter("callback");
        //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
        result = callback + "(" + result + ")";
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
