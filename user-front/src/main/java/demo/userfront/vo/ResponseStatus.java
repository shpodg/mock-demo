package demo.userfront.vo;

/**
 * Created by oneday on 2016/7/22 0022.
 */
public class ResponseStatus {
    public static ResponseStatus STATUS_OK = new ResponseStatus("ok");
    public static ResponseStatus STATUS_ERROR= new ResponseStatus("error");

    public ResponseStatus(String status){
        this.status = status;
    }
    public ResponseStatus(){}
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
