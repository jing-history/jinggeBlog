package gq.jingge.blog.web;

import gq.jingge.blog.domain.Post;
import gq.jingge.blog.service.AppSetting;
import gq.jingge.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by wangyunjing on 2017/11/30.
 */
@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;

    @RequestMapping(value = "", method = GET)
    public String index(@RequestParam(defaultValue = "1") int page, Model model) {
        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.getAllPublishedPostsByPage(page, appSetting.getPageSize());

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);

        return "home/index";
    }
}
