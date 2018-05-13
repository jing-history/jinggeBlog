package gq.jingge.blog.web.admin;

import gq.jingge.blog.dao.LoveRepository;
import gq.jingge.blog.dao.PostRepository;
import gq.jingge.blog.domain.Love;
import gq.jingge.blog.domain.Post;
import gq.jingge.blog.domain.support.PostFormat;
import gq.jingge.blog.domain.support.PostStatus;
import gq.jingge.blog.domain.vo.LoveForm;
import gq.jingge.blog.domain.vo.PostForm;
import gq.jingge.blog.service.LoveService;
import gq.jingge.blog.service.PostService;
import gq.jingge.blog.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

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
        model.addAttribute("loves", loves);

        return "admin/love/love";
    }

    @RequestMapping(value = "new")
    public String newLove(Model model){
        LoveForm loveForm = DTOUtil.map(new Love(), LoveForm.class);

        model.addAttribute("loveForm", loveForm);

        return "admin/love/new";
    }

    @RequestMapping(value = "", method = POST)
    public String createLove(Principal principal, @Valid LoveForm loveForm, Errors errors, Model model){
        Love love = DTOUtil.map(loveForm, Love.class);

        loveRepository.save(love);
        return "redirect:/admin/loves";
    }

    @RequestMapping(value = "{loveId:[0-9]+}/edit")
    public String editLove(@PathVariable Long loveId, Model model){
            Love love = loveRepository.findOne(loveId);
        LoveForm loveForm = DTOUtil.map(love, LoveForm.class);

        model.addAttribute("love", love);
        model.addAttribute("loveForm", loveForm);

        return "admin/love/edit";
    }

    @RequestMapping(value = "{loveId:[0-9]+}", method = {PUT, POST})
    public String updateLove(@PathVariable Long loveId, @Valid LoveForm loveForm, Errors errors, Model model){
        Love love = loveRepository.findOne(loveId);
        DTOUtil.mapTo(loveForm, love);

        loveRepository.save(love);

        return "redirect:/admin/loves";
    }
}
