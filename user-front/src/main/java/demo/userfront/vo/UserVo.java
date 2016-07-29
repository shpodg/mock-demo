package demo.userfront.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * Created by oneday on 2016/7/22 0022.
 */
@Data
public class UserVo {
    private String id;
    @NotEmpty(message = "name 不能为空！")
    private String name;
    @Range(min = 0,message = "年龄不能为负数")
    private int age;
    @NotEmpty(message = "性别不能为空！")
    @Pattern(regexp = "(男|女)",message = "性别只能为男或女！")
    private String sex;
}
