package exception;

/**
 * @author peramdy on 2018/9/17.
 */
public class PdNullPointerException extends PdException {

    public PdNullPointerException() {
        super();
    }

    public PdNullPointerException(String message) {
        super(message);
    }

    public PdNullPointerException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdNullPointerException(Throwable cause) {
        super(cause);
    }

    protected PdNullPointerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
