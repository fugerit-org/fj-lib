package org.fugerit.java.core.lang.ex;

/**
 * Wrapper interface for a status code in an Exception or RuntimeException
 */
public interface CodeEx {

    /**
     * Return the status code, must be implemented.
     *
     * @return  the code
     */
    int getCode();

    /**
     * If not implemented, default behaviour is to warn that the operation is not available with log.
     *
     * @return  the message of the status code
     */
    default String getMessage() {
        ExUtils.WARN_UNSUPPORTED.accept( "getMessage()" );
        return null;
    }

    /**
     * If not implemented, default behaviour is to warn that the operation is not available with log.
     *
     * @return  the cause of the status code
     */
    default Throwable getCause() {
        ExUtils.WARN_UNSUPPORTED.accept( "getCause()" );
        return null;
    }

}
