package gq.jingge.blog.util;

import gq.jingge.blog.support.MarkdownService;
import gq.jingge.blog.support.PegDownMarkdownService;

/**
 * Created by wangyunjing on 2017/12/6.
 */
public class Markdown {

    private static final MarkdownService MARKDOWN_SERVICE = new PegDownMarkdownService();

    public static String markdownToHtml(String content){
        return MARKDOWN_SERVICE.renderToHtml(content);
    }
}
