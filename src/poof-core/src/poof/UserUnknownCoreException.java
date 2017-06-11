/** @version $Id: UserUnknownCoreException.java,v 1.5 2014/12/01 23:08:44 ist178942 Exp $ */
package poof;

/**
 * Thrown when an attempt is made to use an unknown username.
 */
public class UserUnknownCoreException extends CoreException {
    /** Invalid user name. */
    private final String _username;

    /**
     * @param username
     */
    public UserUnknownCoreException(String username) {
        _username = username;
    }

    /**
     * @return _username
     */
    public String getUsername() {
        return _username;
    }

}
