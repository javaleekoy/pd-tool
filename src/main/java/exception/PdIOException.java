package exception;

/**
 * @author peramdy on 2018/9/17.
 */
public class PdIOException extends PdException {

    public PdIOException() {
        super();
    }

    public PdIOException(String message) {
        super(message);
    }

    public PdIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdIOException(Throwable cause) {
        super(cause);
    }

    protected PdIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
