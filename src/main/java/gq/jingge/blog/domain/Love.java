package gq.jingge.blog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangyj
 * @description
 * @create 2018-05-09 9:04
 **/
@Entity
@Table(name = "loves")
public class Love extends BaseModel {

     @Column(nullable = false)
     private String title;

     @Column(nullable = false)
     private String content;

     @Column(nullable = false)
     private String figureImg;

     @Column(nullable = false)
     private String figureMsg;

     @Column(nullable = false)
     private String figcaption;

    public Love() {

    }

    public Love(String title, String content, String figureImg, String figureMsg, String figcaption) {
        this.title = title;
        this.content = content;
        this.figureImg = figureImg;
        this.figureMsg = figureMsg;
        this.figcaption = figcaption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Love{" +
                "title='" + title + '\'' +
                ", article='" + content + '\'' +
                ", figureImg='" + figureImg + '\'' +
                ", figureMsg='" + figureMsg + '\'' +
                ", figcaption='" + figcaption + '\'' +
                '}';
    }
}
