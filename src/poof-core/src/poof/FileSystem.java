/** @version $Id: FileSystem.java,v 1.55 2014/12/02 01:23:11 ist178942 Exp $ */
package poof;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that represents the concept of a file system.
 * Has a root directory and users. May have a file in disk associated with it.
 */
public class FileSystem implements Serializable {

/*==============================================================================
 * Attributes
 *============================================================================*/

     /** Name of the file the file system is associated to. */
    private String _fileName;

    /** Whether the file manager was changed or not. */
    private boolean _changed = false;

    /** Root directory. */
    private Directory _rootDirectory;

    /** Users of the file system. */
    private Map<String, User> _users = new TreeMap<String, User>();

/*==============================================================================
 * Getters and Setters
 *============================================================================*/

    /**
     * @return the name of the file associated with the file system.
     */
    public String getFileName() {
        return _fileName;
    }

    /**
     * @param fileName
     *          the new value of _filename.
     */
    public void setFileName(String fileName) {
        _fileName = fileName;
    }

    /**
     * @return if the file system was changed or not.
     */
    public boolean getChanged() {
        return _changed;
    }

    /**
     * @param changed
     *          the new value of _changed.
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }

    /**
     * @return the root directory.
     */
    public Directory getRootDirectory() {
        return _rootDirectory;
    }

    /**
     * @param username
     *          the username of the pretended user.
     * @return the user who corresponds to the username.
     * @throws UserUnknownCoreException
     */
    public User getUser(String username) {
        return _users.get(username);
    }

    /**
     * @return the root user.
     */
    public User getRoot() {
        return getUser("root");
    }

/*==============================================================================
 * Methods
 *============================================================================*/

    /**
     * Create a fileSystem.
     * Creates the root user and the root directory.
     */
    public FileSystem() {
        User root = new User("root", "Super User", _rootDirectory);
        addUser(root.getUsername(), root);
        _rootDirectory = new Directory(root, _rootDirectory, "/", false);

        try {
            Directory home = createDirectory(root, _rootDirectory, "home", false);
            Directory home_root = createDirectory(root, home, root.getUsername(), false);
            root.setMainDirectory(home_root);
            _rootDirectory.setParent(_rootDirectory);
        }
        catch (CoreException ce) {
            // should not reach this point
            ce.printStackTrace();
        }
    }

    /**
     * Add a user to _users.
     *
     * @param username
     *          the username of the user.
     * @param user
     *          the user.
     */
    public void addUser(String username, User user) {
        _users.put(username, user);
    }

    /**
     * Register a new user from an import file.
     *
     * @param username
     *          the username of the new user.
     * @param name
     *          the name of the new user.
     */
    public void registerUser(String username, String name) {
        try {
            createUser(getRoot(), username, name);
        }
        catch (CoreException ce) {
            //should not reach this point
            ce.printStackTrace();
        }
    }

    /**
     * Create a new user.
     *
     * @see poof.FileSystem#registerUser(User,String,String)
     *
     * @throws AcessDeniedCoreException
     * @throws UserExistsCoreException
     */
    public void createUser (User user, String username, String name) throws AccessDeniedCoreException, UserExistsCoreException {
        assertRoot(user);
        assertUserDoesntExist(username);

        Directory home = (Directory) _rootDirectory.getEntry("home");
        try {
            assertEntryDoesntExist(home, username);
        }
        catch (EntryExistsCoreException eece) {
            try {
                removeEntry(user, home, username);
            }
            catch (CoreException ce) {
                // should not reach this point
                ce.printStackTrace();
            }
        }

        try {
            Directory home_user = createDirectory(user, home, username, false);
            User new_user = new User(username, name, home_user);
            addUser(username, new_user);
            home_user.setOwner(new_user);

            setChanged(true);
        }
        catch (EntryExistsCoreException eece) {
            // should not reach this point
            eece.printStackTrace();
        }
    }

    /**
     * List all entries of the current directory.
     *
     * @param directory
     *        current directory.
     * @return a string of a list of all entries in the current directory.
     */
    public String listAllEntries(Directory directory) {
        String s = new String();
        Map<String,Entry> entries = directory.getEntries();
        for (Map.Entry<String,Entry> kv : entries.entrySet()) {
            s += kv.getValue() + " " + kv.getKey() + '\n';
        }
        return s.substring(0, s.length() - 1 );
    }

    /**
     * List an entry of the current directory.
     *
     * @param directory
     *          current directory.
     * @param entryName
     *          the name of the entry.
     * @return information about an entry of the directory.
     * @throws EntryUnknownCoreException
     */
    public String listEntry(Directory directory, String entryName) throws EntryUnknownCoreException {
        assertEntryExists(directory, entryName);
        return directory.getEntry(entryName).toString() + entryName;
    }

    /**
     * List all file system users.
     *
     * @return all current file system users.
     */
    public String listAllUsers() {
        String s = "";
        for (User user: _users.values() ) {
            s += user.getUsername()
                + ":"
                + user.getName()
                + ":"
                + showWorkingDirectory(user.getMainDirectory())
                + '\n';
        }
        return s.substring(0, s.length() - 1);
    }

    /**
     * Remove an entry of the current directory.
     *
     * @param user
     *          the user who wants to remove the entry.
     * @param directory
     *          the current directory.
     * @param entryName
     *          the name of the entry.
     *
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     * @throws IllegalRemovalCoreException
     */
    public void removeEntry(User user, Directory directory, String entryName) throws AccessDeniedCoreException, EntryUnknownCoreException, IllegalRemovalCoreException {
        assertEntryExists(directory, entryName);
        assertEntryIsRemovable(directory, entryName);
        assertPermissionToRemoveEntry(user, directory, entryName);

        directory.removeEntry(entryName);
    }

    /**
     * Change the current directory.
     *
     * @param directory
     *          the current directory.
     * @param dirName
     *          the name of the future current directory.
     * @return the new current directory.
     *
     * @throws EntryUnknownCoreException
     * @throws IsNotDirectoryCoreException
     */
    public Directory changeDirectory(Directory directory, String dirName) throws EntryUnknownCoreException, IsNotDirectoryCoreException {
        assertEntryExists(directory, dirName);
        assertIsDirectory(directory, dirName);

        setChanged(true);
        return (Directory) directory.getEntry(dirName);
    }

    /**
     * Register a new directory from an import file.
     *
     * @param path
     *          the directory absolute path.
     * @param owner
     *          the username of the owner of the new directory.
     * @param permission
     *          if the directory is public or private.
     * @return the registered directory.
     *
     * @throws AccessDeniedCoreException
     * @throws EntryExistsCoreException
     */
    public Directory registerDirectory(String path, String owner, String permission) {
        try {
            assertUserExists(owner);
        }
        catch (UserUnknownCoreException uuce) {
            // should not reach this point
            uuce.printStackTrace();
        }

        String[] directories = path.split("/");
        boolean perm = false;

        int len = directories.length;
        Directory dir = getRootDirectory();

        for (int i = 1; i < len; i++) {
            try {
                if (i == len - 1 && permission.equals("public")) {
                    perm = true;
                }
                dir = createDirectory(getRoot(), dir, directories[i], perm);
                dir.setOwner(getUser(owner));
            }
            catch (AccessDeniedCoreException adce) {
                // should not reach this point, because user is root
                adce.printStackTrace();
            }
            catch (EntryExistsCoreException eece) {
                dir = (Directory) dir.getEntry(directories[i]);
            }
        }
        return dir;
    }

    /**
     * Create a new directory.
     *
     * @param user
     *          the user who creates the directory.
     * @param directory
     *          the current directory.
     * @param dirName
     *          the name of the new directory.
     * @param permission
     *          if the directory is public or private.
     *
     * @return the created directory.
     *
     * @throws AccessDeniedCoreException
     * @throws EntryExistsCoreException
     */
    public Directory createDirectory(User user, Directory directory, String dirName, boolean permission) throws AccessDeniedCoreException, EntryExistsCoreException {
        assertEntryDoesntExist(directory, dirName);
        assertPermissionToModifyEntry(user, directory);

        Directory newDirectory = new Directory(user, directory, dirName, permission);
        directory.addEntry(dirName, newDirectory);

        setChanged(true);
        return newDirectory;
    }

    /**
     * Show absolute path of working directory.
     *
     * @param directory
     *          the current directory.
     * @return absolute path to working directory.
     */
    public String showWorkingDirectory(Directory directory) {
        if (directory == getRootDirectory()) {
            return getRootDirectory().getName();
        }
        return auxShowWorkingDirectory(directory);
    }

    /**
     * Show absolute path.
     *
     * @see poof.FileManager#ShowWorkingDirectory(Directory)
     */
    private String auxShowWorkingDirectory(Directory directory) {
        if (directory != getRootDirectory()) {
            return auxShowWorkingDirectory(directory.getParent()) + "/" + directory.getName();
        }
        return "";
    }

    /**
     * Register a new file form an import file.
     *
     * @param path
     *          the file absolute path.
     * @param owner
     *          the file owner's username.
     * @param permission
     *          if the file is public or private.
     * @param content
     *          the content of the file.
     * @return the file registered.
     *
     * @throws AccessDeniedCoreException
     * @throws EntryExistsCoreException
     */
    public File registerFile(String path, String owner, String permission, String content) throws AccessDeniedCoreException, EntryExistsCoreException {
        String[] fields = path.split("/");
        String pathToDir = new String();

        int len = fields.length;
        for (int i = 0; i < len - 1; i++) {
            pathToDir += fields[i] + '/';
        }
        Directory dir = registerDirectory(pathToDir, owner, "private");
        File file = createFile(getRoot(), dir, fields[fields.length - 1]);

        file.append(content);
        file.setOwner(getUser(owner));
        file.setPermission(permission.equals("public"));

        return file;
    }

    /**
     * Create a new file.
     *
     * @param user
     *          the user who creates the directory.
     * @param directory
     *          the current directory.
     * @param fileName
     *          the name of the file.
     *
     * @return the created file.
     *
     * @throws EntryExistsCoreException
     * @throws AccessDeniedCoreException
     */
    public File createFile(User user, Directory directory, String fileName) throws AccessDeniedCoreException, EntryExistsCoreException {
        assertEntryDoesntExist(directory, fileName);
        assertPermissionToModifyEntry(user, directory);

        File newFile = new File(user, fileName, false);
        directory.addEntry(fileName, newFile);

        setChanged(true);
        return newFile;
    }

    /**
     * Write a line to a file.
     *
     * @param user
     *          the user who writes to the file.
     * @param directory
     *          the current directory.
     * @param fileName
     *          the name of the file to write to.
     * @param content
     *          the content to write to the file.
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     * @throws IsNotFileCoreException
     */
    public void writeFile(User user, Directory directory, String fileName, String content) throws AccessDeniedCoreException, EntryUnknownCoreException, IsNotFileCoreException {
        assertEntryExists(directory, fileName);
        assertIsFile(directory, fileName);

        File file = (File) directory.getEntry(fileName);
        assertPermissionToModifyEntry(user, file);

        file.append(content);
    }

    /**
     * Show the content of the file.
     *
     * @param directory
     *          the current directory.
     * @param fileName
     *          the name of the file.
     * @return the content of the file.
     *
     * @throws EntryUnknownCoreException
     * @throws IsNotFileCoreException
     */
    public String showFile(Directory directory, String fileName) throws EntryUnknownCoreException, IsNotFileCoreException {
        assertEntryExists(directory, fileName);
        assertIsFile(directory, fileName);

        File file = (File) directory.getEntry(fileName);
        return file.getContent();
    }

    /**
     * Change the permissions of an entry.
     *
     * @param user
     *          the user who changes permission.
     * @param directory
     *          the current directory.
     * @param entryName
     *          the name of the entry.
     * @param permission
     *          the new permission of the entry.
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     */
    public void changePermission(User user, Directory directory, String entryName, boolean permission) throws AccessDeniedCoreException, EntryUnknownCoreException {
        assertEntryExists(directory, entryName);

        Entry entry = directory.getEntry(entryName);
        assertPermissionToChangePermission(user, entry);

        entry.setPermission(permission);
        setChanged(true);
    }

    /**
     * Change the owner of an entry.
     *
     * @param user
     *          the user who changes the owner.
     * @param directory
     *          the current directory.
     * @param entryName
     *          the name of the entry to be changed.
     * @param newOwner
     *          the username of the new owner.
     *
     * @throws AccessDeniedCoreException
     * @throws EntryUnknownCoreException
     * @throws UserUnknownCoreException
     */
    public void changeOwner(User user, Directory directory, String entryName, String newOwner) throws AccessDeniedCoreException, EntryUnknownCoreException, UserUnknownCoreException {
        assertUserExists(user.getUsername());
        assertEntryExists(directory, entryName);
        Entry entry = directory.getEntry(entryName);
        assertPermissionToModifyEntry(user, entry);

        entry.setOwner(getUser(newOwner));
        setChanged(true);
    }

/*==============================================================================
 * Asserts
 *============================================================================*/

    /**
     * Assert that a user has permission to write on an entry.
     *
     * @param user
     *          the user.
     * @param entry
     *          the entry.
     * @throws AccessDeniedCoreException
     */
    public void assertPermissionToModifyEntry(User user, Entry entry) throws AccessDeniedCoreException {
        if (user != getRoot() && user != entry.getOwner() && !entry.getPermission()) {
            throw new AccessDeniedCoreException(user.getUsername());
        }
    }

    /**
     * Assert permission to remove a directory.
     *
     * @param user
     *          the user trying to remove the entry.
     * @param parent
     *          the parent directory of the entry to be removed.
     * @param entryName
     *          the name of the entry to be removed.
     * @throws AccessDeniedCoreException
     */
    public void assertPermissionToRemoveEntry(User user, Directory parent, String entryName) throws AccessDeniedCoreException {
        if (user == getRoot()) {
            return;
        }

        if (user != parent.getOwner() && !parent.getPermission()) {
            throw new AccessDeniedCoreException(user.getUsername());
        }

        Entry entry = parent.getEntry(entryName);
        if (user != entry.getOwner() && !entry.getPermission()) {
            throw new AccessDeniedCoreException(user.getUsername());
        }
    }

    /**
     * Assert that a user has permission to change the permission of an entry.
     *
     * @param user
     *          the user.
     * @param entry
     *          the entry.
     * @throws AccessDeniedCoreException
     */
    public void assertPermissionToChangePermission(User user, Entry entry) throws AccessDeniedCoreException {
        if (user != getRoot() && user != entry.getOwner()) {
            throw new AccessDeniedCoreException(user.getUsername());
        }
    }

    /**
     * Assert that a user has root permissions.
     *
     * @param user
     *          the user.
     * @throws AccessDeniedCoreException
     */
    public void assertRoot(User user) throws AccessDeniedCoreException {
        if (user != getRoot()) {
            throw new AccessDeniedCoreException(user.getUsername());
        }
    }

    /**
     * Assert that a user has owner permissions of an entry.
     *
     * @param user
     * @param entry
     *
     * @throws AccessDeniedException
     */
    public void assertOwner(User user, Entry entry) throws AccessDeniedCoreException  {
        if (user != entry.getOwner()) {
            throw new AccessDeniedCoreException(user.getUsername());
        }
    }

    /**
     * Assert that a user exists in the file system.
     *
     * @param user
     * @throws UserUnknownCoreException
     */
    public void assertUserExists(String username) throws UserUnknownCoreException {
        if (!_users.containsKey(username)) {
            throw new UserUnknownCoreException(username);
        }
    }

    /**
     * Assert that a user does not exist in a file system.
     *
     * @param username
     *          the username of the user.
     * @throws UserExistsCoreException
     */
    public void assertUserDoesntExist(String username) throws UserExistsCoreException {
        if (_users.containsKey(username)) {
            throw new UserExistsCoreException(username);
        }
    }

    /**
     * Assert that an entry exists in the directory.
     *
     * @param directory
     *          the directory.
     * @param entryName
     *          the name of the entry.
     * @throws EntryUnknownCoreException
     */
    public void assertEntryExists(Directory directory, String entryName) throws EntryUnknownCoreException {
        if (!directory.contains(entryName)) {
            throw new EntryUnknownCoreException(entryName);
        }
    }

    /**
     * Assert that an entry does not exist in the directory.
     *
     * @param directory
     *          the directory.
     * @param entryName
     *          the name of the entry.
     * @throws EntryExistsCoreException
     */
    public void assertEntryDoesntExist(Directory directory, String entryName) throws EntryExistsCoreException {
       if (directory.contains(entryName)) {
           throw new EntryExistsCoreException(entryName);
       }
    }

    /**
     * Assert that an entry is removable.
     *
     * @param directory
     *         the current directory.
     * @param entryName
     *         the name of the entry.
     * @throws IllegalRemovalCoreException
     */
    public void assertEntryIsRemovable(Directory directory, String entryName) throws IllegalRemovalCoreException {
        Entry entry = directory.getEntry(entryName);
        if (directory == entry || directory.getParent() == entry) {
            throw new IllegalRemovalCoreException();
        }
    }

    /**
     * Assert than an entry is a directory.
     *
     * @param directory
     *          the current directory.
     * @param entryName
     *          the name of the entry.
     * @throws IsNotDirectoryCoreException
     */
    public void assertIsDirectory(Directory directory, String entryName) throws IsNotDirectoryCoreException {
       Entry entry = directory.getEntry(entryName);
       if ( !(entry instanceof Directory) ) {
           throw new IsNotDirectoryCoreException(entryName);
       }
    }

    /**
     * Assert than an entry is a file.
     *
     * @param directory
     *          the current directory.
     * @param entryName
     *          the name of the entry.
     * @throws IsNotFileCoreException
     */
    public void assertIsFile(Directory directory, String entryName) throws IsNotFileCoreException {
       Entry entry = directory.getEntry(entryName);
       if ( !(entry instanceof File) ) {
           throw new IsNotFileCoreException(entryName);
       }
    }

}
