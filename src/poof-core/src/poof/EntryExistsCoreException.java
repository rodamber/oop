/** @version $Id: EntryExistsCoreException.java,v 1.5 2014/12/01 23:08:44 ist178942 Exp $ */
package poof;

/**
 * Thrown when an attempt is made to create a duplicate entry.
 */
public class EntryExistsCoreException extends CoreException {
    /** Invalid entry name. */
    private final String _entryName;

    /**
     * @param entryName
     */
    public EntryExistsCoreException(String entryName) {
        _entryName = entryName;
    }

    /**
     * @return _entryName
     */
    public String getEntryName() {
        return _entryName;
    }

}
