/** @version $Id: EntryUnknownCoreException.java,v 1.5 2014/12/01 23:08:44 ist178942 Exp $ */
package poof;

/**
 * Thrown when an attempt is made to use an unknown entry.
 */
public class EntryUnknownCoreException extends CoreException {
    /** Invalid entry name. */
    private final String _entryName;

    /**
     * @param entryName
     */
    public EntryUnknownCoreException(String entryName) {
        _entryName = entryName;
    }

    /**
     * @return _username
     */
    public String getEntryName() {
        return _entryName;
    }

}
