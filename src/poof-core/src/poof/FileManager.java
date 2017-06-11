/** @version $Id: FileManager.java,v 1.51 2014/12/02 01:23:11 ist178942 Exp $ */
package poof;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *  A manager of file systems.
 */
public class FileManager implements Serializable {

/*==============================================================================
 * Attributes
 *============================================================================*/

    /** User's current directory. */
    private Directory _directory;

    /** Current file system. */
    private FileSystem _fileSystem;

    /** Logged user. */
    private User _user;

/*==============================================================================
 * Getters
 *============================================================================*/

    /**
     * @return _directory
     */
    public Directory getDirectory() {
        return _directory;
    }

   /**
     * @return _fileSystem.
     */
    public FileSystem getFileSystem() {
        return _fileSystem;
    }

    /**
     * @return _user.
     */
    public User getUser() {
        return _user;
    }

/*==============================================================================
 * Methods
 *============================================================================*/

    /**
     * Create a new file system.
     */
    public void createFileSystem() {
        _fileSystem = new FileSystem();
        _user       = _fileSystem.getRoot();
        _directory  = _user.getMainDirectory();
    }

    /**
     * Get the name of the file associated with the file system.
     *
     * @return the name of the file associated.
     */
    public String getFileName() {
        return _fileSystem.getFileName();
    }

    /**
     * Import a saved file system from a specially formatted string.
     *
     * @param datafile
     *          the string to parse.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void dataImport(String datafile) throws FileNotFoundException, IOException {
        createFileSystem();
        BufferedReader reader = new BufferedReader(new FileReader(datafile));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] data = line.split("\\|");
            int len = data.length;

            try {
                if (data[0].equals("USER")) {
                    _fileSystem.registerUser(data[1], data[2]);
                }
                else if (data[0].equals("DIRECTORY")) {
                    _fileSystem.registerDirectory(data[1], data[2], data[3]);
                }
                else if (data[0].equals("FILE")) {
                    _fileSystem.registerFile(data[1], data[2], data[3], data[4]);
                }
            }
            catch (CoreException fme) {
                // should not reach this point
                fme.printStackTrace();
            }
        }
    }

    /**
     * Open a saved file system.
     *
     * @param filename
     *          the name of the file to read from.
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void openFileSystem(String filename) throws ClassNotFoundException, FileNotFoundException, IOException {
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));

        _fileSystem = (FileSystem) ois.readObject();
        _user       = (User) ois.readObject();
        _directory  = _user.getMainDirectory();

        ois.close();
        _fileSystem.setChanged(false);
    }

    /**
     * Save current fileSystem to a binary file.
     *
     * @param filename
     *          the name of the file to save to.
     * @throws IOException
     */
    public void saveFileSystem(String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
        oos.writeObject(_fileSystem);
        oos.writeObject(_user);
        oos.close();

        _fileSystem.setFileName(filename);
        _fileSystem.setChanged(false);
    }

    /**
     * Save current fileSystem to the file it is associated to.
     *
     * @throws IOException
     */
    public void saveFileSystem() throws IOException {
        saveFileSystem(_fileSystem.getFileName());
    }

    /**
     * Check if the file system was changed.
     *
     * @return true if the file system was changed, false if not.
     */
    public boolean fileSystemWasChanged() {
        return _fileSystem.getChanged();
    }

    /**
     * Check if current user is root.
     *
     * @return true if current user is root, false if not.
     */
     public boolean userIsRoot() {
         return _user == _fileSystem.getRoot();
     }

    /**
     * Log in a file system.
     *
     * @param username
     *          the username of the user to log in.
     * @throws UserUnknownCoreException
     */
    public void login(String username) throws UserUnknownCoreException {
        assertUserExists(username);
        _user      = _fileSystem.getUser(username);
        _directory = _user.getMainDirectory();
    }

    /**
     * List all entries of the current directory.
     *
     * @return a list of all the entries in the current directory.
     */
    public String listAllEntries() {
        return _fileSystem.listAllEntries(_directory);
    }

    /**
     * List entry.
     *
     * @param entryName
     *          the name of the entry.
     * @return a string with information about the specified entry of the current directory.
     * @throws EntryUnknownCoreException
     */
    public String listEntry(String entryName) throws EntryUnknownCoreException {
        return _fileSystem.listEntry(_directory, entryName);
    }

    /**
     * Remove the specified entry.
     *
     * @param entryName
     *          the name of the entry.
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     * @throws IllegalRemovalCoreException
     */
    public void removeEntry(String entryName) throws AccessDeniedCoreException, EntryUnknownCoreException, IllegalRemovalCoreException {
        _fileSystem.removeEntry(_user, _directory, entryName);
    }

    /**
     * Change the current directory.
     *
     * @param dirName
     *          the name of the directory.
     * @throws EntryUnknownCoreException
     * @throws IsNotDirectoryCoreException
     */
    public void changeDirectory(String dirName) throws EntryUnknownCoreException, IsNotDirectoryCoreException {
        _directory = _fileSystem.changeDirectory(_directory, dirName);
    }

    /**
     * Create a new file.
     *
     * @param fileName
     *          the name of the file.
     * @throws AccessDeniedCoreException
     * @throws EntryExistsCoreException
     */
    public void createFile(String fileName) throws AccessDeniedCoreException, EntryExistsCoreException {
        _fileSystem.createFile(_user, _directory, fileName);
    }

    /**
     * Create a new directory in the current directory.
     *
     * @param dirName
     *          the name of the directory.
     * @throws AccessDeniedCoreException
     * @throws EntryExistsCoreException
     */
    public void createDirectory(String dirName) throws AccessDeniedCoreException, EntryExistsCoreException {
        _fileSystem.createDirectory(_user, _directory, dirName, false);
    }

    /**
     * Show absolute path of working directory.
     *
     * @return absolute path to working directory.
     */
    public String showWorkingDirectory() {
        return _fileSystem.showWorkingDirectory(_directory);
    }

    /**
     * Write a new line to file.
     *
     * @param fileName
     *          the name of the file.
     * @param content
     *          the content to write.
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     * @throws IsNotFileCoreException
     */
    public void writeFile(String fileName, String content) throws AccessDeniedCoreException, EntryUnknownCoreException, IsNotFileCoreException {
        _fileSystem.writeFile(_user, _directory, fileName, content);
    }

    /**
     * Show the content of the file.
     *
     * @param fileName
     *          the name of the file.
     * @return the content of the file.
     *
     * @throws EntryUnknownCoreException
     * @throws IsNotFileCoreException
    */
    public String showFile(String fileName) throws EntryUnknownCoreException, IsNotFileCoreException {
        return _fileSystem.showFile(_directory, fileName);
    }

    /**
     * Change the permission of an entry.
     *
     * @param entryName
     *          the name of the entry.
     * @param permission
     *          public or private.
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     */
    public void changePermission(String entryName, boolean permission) throws AccessDeniedCoreException, EntryUnknownCoreException {
        _fileSystem.changePermission(_user, _directory, entryName, permission);
    }

    /**
     * Change the owner of an entry.
     *
     * @param entryName
     *          the name of the entry.
     * @param newOwner
     *          the username of the new owner.
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     * @throws UserUnknownCoreException
     */
    public void changeOwner(String entryName, String newOwner) throws AccessDeniedCoreException, EntryUnknownCoreException, UserUnknownCoreException {
        _fileSystem.changeOwner(_user, _directory, entryName, newOwner);
    }

    /**
     * Create a new user.
     *
     * @param username
     *          the username of the new user.
     * @param name
     *          the name of the new user.
     * @throws AccessDeniedCoreException
     * @throws UserExistsCoreException
     */
    public void createUser(String username, String name) throws AccessDeniedCoreException, UserExistsCoreException {
            _fileSystem.createUser(_user, username, name);
    }

    /**
     * List all current file system users.
     *
     * @return a string of a list of all the users of the file system.
     */
    public String listAllUsers() {
        return _fileSystem.listAllUsers();
    }

/*==============================================================================
 * Asserts
 *============================================================================*/

    /**
     * Assert that a user exists.
     *
     * @param username
     *          the username of the user.
     * @throws UserUnknownCoreException
     */
    public void assertUserExists(String username) throws UserUnknownCoreException {
        _fileSystem.assertUserExists(username);
    }

}
