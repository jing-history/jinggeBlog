package gq.jingge.blog.domain.vo;

import gq.jingge.blog.domain.support.PostFormat;
import gq.jingge.blog.domain.support.PostStatus;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class LoveForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private PostFormat postFormat;

    @NotNull
    private String figureImg;

    @NotNull
    private String figureMsg;

    @NotNull
    private String figcaption;

    private String formatDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostFormat getPostFormat() {
        return postFormat;
    }

    public void setPostFormat(PostFormat postFormat) {
        this.postFormat = postFormat;
    }

    public String getFigureImg() {
        return figureImg;
    }

    public void setFigureImg(String figureImg) {
        this.figureImg = figureImg;
    }

    public String getFigureMsg() {
        return figureMsg;
    }

    public void setFigureMsg(String figureMsg) {
        this.figureMsg = figureMsg;
    }

    public String getFigcaption() {
        return figcaption;
    }

    public void setFigcaption(String figcaption) {
        this.figcaption = figcaption;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    @Override
    public String toString() {
        return "LoveForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postFormat=" + postFormat +
                ", figureImg='" + figureImg + '\'' +
                ", figureMsg='" + figureMsg + '\'' +
                ", figcaption='" + figcaption + '\'' +
                ", formatDate='" + formatDate + '\'' +
                '}';
    }
}
