package gq.jingge.blog.web.admin;

import gq.jingge.blog.dao.LoveRepository;
import gq.jingge.blog.dao.PostRepository;
import gq.jingge.blog.domain.Love;
import gq.jingge.blog.domain.Post;
import gq.jingge.blog.service.LoveService;
import gq.jingge.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangyj
 * @description
 * @create 2018-05-10 14:37
 **/
@Controller
@RequestMapping("admin/loves")
public class LoveController {

    @Autowired
    private LoveRepository loveRepository;

    private static final int PAGE_SIZE = 20;

    @RequestMapping(value = "")
    public String index(@RequestParam(defaultValue = "0") int page, Model model){
        Page<Love> loves = loveRepository.findAll(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

        model.addAttribute("totalPages", loves.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("posts", loves);

        return "admin/posts/index";
    }
}
