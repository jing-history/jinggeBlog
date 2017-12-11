package gq.jingge.blog.domain.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by wangyunjing on 2017/12/10.
 */
public class UserForm {

    @NotNull
    private String password;

    @NotNull
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
