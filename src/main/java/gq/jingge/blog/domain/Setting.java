package gq.jingge.blog.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A generic setting model
 *
 * @author Raysmond<i@raysmond.com>
 */
@Entity
@Table(name = "settings")
public class Setting extends BaseModel{
    @Column(name = "_key", unique = true, nullable = false)
    private String key;

    @Lob
    @Column(name = "_value")
    private Serializable value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
}
