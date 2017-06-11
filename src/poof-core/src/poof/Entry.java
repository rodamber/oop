/** @version $Id: Entry.java,v 1.18 2014/12/02 01:23:11 ist178942 Exp $ */
package poof;

import java.io.Serializable;

/**
 *  An entry of the file system. Has a user, a name and permisson.
 */
public abstract class Entry implements Serializable {

/*==============================================================================
 * Attributes
 *============================================================================*/

    /** The user who owns the entry. */
    private User _owner;

    /** The name of the entry. */
    private String _name;

    /** The permission of the entry. */
    private boolean _permission;

/*==============================================================================
 * Getters and Setters
 *============================================================================*/

    /**
     * @return  _owner.
     */
    public User getOwner() {
        return _owner;
    }

    /**
     * @param owner
     *          the new value of _owner.
     */
    public void setOwner(User owner) {
        _owner = owner;
    }

    /**
     * @return _name.
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name
     *          the new name of the entry.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * @return _permission.
     */
    public boolean getPermission() {
        return _permission;
    }

    /**
     * @param permission
     *          the new value of _permission.
     */
    public void setPermission(boolean permission) {
        _permission = permission;
    }

/*==============================================================================
 * Methods
 *============================================================================*/

    /**
     * Create a new entry.
     *
     * @param owner
     *          the owner of the new entry.
     * @param name
     *          the name of the new entry.
     * @param permission
     *          the permission of the new entry.
     */
    public Entry (User owner, String name, boolean permission) {
        _owner      = owner;
        _name       = name;
        _permission = permission;
    }

    /**
     * The size of the entry.
     */
     public abstract int size();

     /**
      * @see java.lang.Object#equals(Object)
      */
     @Override
     public boolean equals(Object o) {
        if (o instanceof Entry) {
            Entry entry = (Entry) o;
            return _name.equals(entry.getName())
                && _owner.equals(entry.getOwner())
                && _permission == entry.getPermission();
        }
        return false;
     }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String perm = (_permission == true) ? "w" : "-";
        return perm + " " + _owner.getUsername() + " " + size();
    }

}
