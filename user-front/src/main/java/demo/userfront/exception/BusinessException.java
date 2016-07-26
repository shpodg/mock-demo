package demo.userfront.exception;

/**
 * Created by oneday on 2016/7/26 0026.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
