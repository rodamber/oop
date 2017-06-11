/** @version $Id: IsNotDirectoryCoreException.java,v 1.5 2014/12/01 23:08:44 ist178942 Exp $ */
package poof;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotDirectoryCoreException extends CoreException {
    /** Invalid entry name. */
    private final String _entryName;

    /**
     * @param entryName
     */
    public IsNotDirectoryCoreException(String entryName) {
        _entryName = entryName;
    }

    /**
     * @return _entryName
     */
    public String getEntryName() {
        return _entryName;
    }

}
