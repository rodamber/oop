/** @version $Id: Directory.java,v 1.22 2014/12/02 01:23:10 ist178942 Exp $ */
package poof;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class that represents the concept of a directory.
 */
public class Directory extends Entry {

/*==============================================================================
 * Attributes
 *============================================================================*/

    /** Entries of the directory. */
    private TreeMap<String, Entry> _entries = new TreeMap<String, Entry>();

/*=============================================================================
 * Getters and Setters
 *============================================================================*/

    /**
     * @param entryName
     *            the name of the entry.
     *
     * @return the entry.
     */
     public Entry getEntry(String entryName) {
         return (_entries.get(entryName));
     }

     /**
      * @return _entries.
      */
     public TreeMap<String, Entry> getEntries() {
         return _entries;
     }

    /**
     * @return the parent directory.
     */
    public Directory getParent() {
         return (Directory) getEntry("..");
    }

    /**
     * @param directory
     *          the new parent directory
     */
    public void setParent(Directory directory) {
        removeEntry("..");
        addEntry("..", directory);
    }

/*==============================================================================
 * Methods
 *============================================================================*/

    /**
     * Create a new directory.
     *
     * @param user
     *          the user who creates the directory.
     * @param parent
     *          the parent of the new directory.
     * @param name
     *          the name of the new directory.
     * @param permission
     *          the permission of the new directory.
     */
    public Directory (User user, Directory parent, String name, boolean permission) {
        super(user, name, permission);
        addEntry("..", parent);
        addEntry(".", this);
    }

    /**
     * Add an entry of the directory.
     *
     * @param entryName
     *            the name of the entry.
     * @param entry
     *            the entry.
     */
    public void addEntry(String entryName, Entry entry) {
         _entries.put(entryName, entry);
    }

    /**
     * Remove an entry of the directory.
     *
     * @param entryName
     *            the name of the entry.
     */
    public void removeEntry(String entryName) {
         _entries.remove(entryName);
    }

    /**
     * Check if the directory contains a certain entry.
     *
     * @param entryName
     *          the name of the entry.
     * @return true if the directory contains the entry, false if not.
     */
    public boolean contains(String entryName) {
        return _entries.containsKey(entryName);
    }

    /**
     * The size of the directory in bytes. Each entry has 8 bytes.
     */
    @Override
    public int size() {
        return (_entries.size() * 8);
    }

    /**
     * Two directories are equal if they have the same name.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Directory) {
            Directory directory = (Directory) o;
            return getName().equals(directory.getName())
                && getParent() == directory.getParent();
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "d " + super.toString();
    }

}
