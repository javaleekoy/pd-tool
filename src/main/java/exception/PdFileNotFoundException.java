package exception;

/**
 * @author peramdy on 2018/9/17.
 */
public class PdFileNotFoundException extends PdException {

    public PdFileNotFoundException() {
        super();
    }

    public PdFileNotFoundException(String message) {
        super(message);
    }

    public PdFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdFileNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PdFileNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
