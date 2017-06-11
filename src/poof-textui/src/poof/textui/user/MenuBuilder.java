/** @version $Id: MenuBuilder.java,v 1.3 2014/11/29 18:28:21 ist178942 Exp $ */
package poof.textui.user;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FileManager;

/**
 * Menu builder for search operations.
 */
public class MenuBuilder {

    /**
     * @param fileManager
     *          the file manager that will manage the file systems.
     */
    public static void menuFor(FileManager fileManager) {
        Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
                new CreateUser(fileManager),
                new ListAllUsers(fileManager),
                });
        menu.open();
    }

}
