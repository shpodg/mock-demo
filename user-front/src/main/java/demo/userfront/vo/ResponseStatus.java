package demo.userfront.vo;

/**
 * Created by oneday on 2016/7/22 0022.
 */
public enum ResponseStatus {
    STATUS_OK("ok"),
    STATUS_ERROR("error");

    ResponseStatus(String status){
        this.status = status;
    }
    private String status;
}
