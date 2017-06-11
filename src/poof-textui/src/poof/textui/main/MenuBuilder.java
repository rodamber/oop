/** @version $Id: MenuBuilder.java,v 1.5 2014/11/29 18:28:20 ist178942 Exp $ */
package poof.textui.main;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FileManager;

/**
 * Menu builder.
 */
public abstract class MenuBuilder {

    /**
     * @param fileManager
     *          the file manager that will manage the file systems.
     */
    public static void menuFor(FileManager fileManager) {
        Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
                new New(fileManager),
                new Open(fileManager),
                new Save(fileManager),
                new Login(fileManager),
                new MenuOpenShell(fileManager),
                new MenuOpenUserManagement(fileManager)
        });
        menu.open();
    }

}
