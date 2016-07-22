package demo.userservice.vo;

import lombok.Data;

/**
 * Created by oneday on 2016/7/22 0022.
 */
public enum  ResposeStatus {
    STATUS_OK("ok"),
    STATUS_ERROR("error");

    ResposeStatus(String status){
        this.status = status;
    }
    private String status;
}
