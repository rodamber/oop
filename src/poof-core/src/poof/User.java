/** @version $Id: User.java,v 1.12 2014/12/02 01:23:11 ist178942 Exp $ */
package poof;

import java.io.Serializable;

/**
 * Class that represents the concept of user of a file system.
 * Has a unique identifier, a name, and a main directory.
 */
public class User implements Serializable {

/*==============================================================================
 * Attributes
 *============================================================================*/

    /** The username. */
    private String _username;

    /** The name. */
    private String _name;

    /** The main directory. */
    private Directory _directory;

/*==============================================================================
 * Getters and Setters
 *============================================================================*/

    /**
     * @return the username of the user.
     */
    public String getUsername() {
        return _username;
    }

    /**
     * @param username
     *          the new username.
     */
    public void setUsername(String username) {
        _username = username;
    }

    /**
     * @return the name of the user.
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name
     *          the new name.
     */
    public void setName(String name) {
        _name = name;
    }

    /**
     * @return _directory.
     */
    public Directory getMainDirectory() {
        return _directory;
    }

    /**
     * @param directory
     *          the new main directory of the user.
     */
    public void setMainDirectory(Directory directory) {
        _directory = directory;
    }

/*==============================================================================
 * Methods
 *============================================================================*/

    /**
     * Create a user.
     *
     * @param username
     *          the username of the new user.
     * @param name
     *          the name of the new user.
     * @param directory
     *          the parent directory of the user's main directory.
     */
    public User(String username, String name, Directory directory) {
        _name      = name;
        _username  = username;
        _directory = directory;
    }

    /**
     * Two users are equal if they have the same username.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
       if (o instanceof User) {
           User user = (User) o;
           return getUsername() == user.getUsername();
       }
       return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return _username + ":" + _name;
    }
}
