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
     private String article;

     @Column(nullable = false)
     private String figureImg;

     @Column(nullable = false)
     private String figureMsg;

     @Column(nullable = false)
     private String figcaption;

    public Love() {

    }

    public Love(String title, String article, String figureImg, String figureMsg, String figcaption) {
        this.title = title;
        this.article = article;
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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Love{" +
                "title='" + title + '\'' +
                ", article='" + article + '\'' +
                ", figureImg='" + figureImg + '\'' +
                ", figureMsg='" + figureMsg + '\'' +
                ", figcaption='" + figcaption + '\'' +
                '}';
    }
}
