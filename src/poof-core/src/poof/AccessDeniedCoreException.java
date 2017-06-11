/** @version $Id: AccessDeniedCoreException.java,v 1.6 2014/12/01 23:09:46 ist178942 Exp $ */
package poof;

/**
 * Thrown when a restricted operation is attempted.
 */
public class AccessDeniedCoreException extends CoreException {
    /** Invalid user id. */
    private final String _username;

    /**
     * @param username
     */
    public AccessDeniedCoreException(String username) {
        _username = username;
    }

    /**
     * @return _username
     */
    public String getUsername() {
        return _username;
    }
}
