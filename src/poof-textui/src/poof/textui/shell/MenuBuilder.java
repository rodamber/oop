/** @version $Id: MenuBuilder.java,v 1.4 2014/11/29 18:28:21 ist178942 Exp $ */
package poof.textui.shell;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FileManager;

/**
 * Menu builder for shell operations.
 */
public class MenuBuilder {

    /**
     * @param fileManager
     *          the file manager that will manage the file systems.
     */
    public static void menuFor(FileManager fileManager) {
        Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
                new ListAllEntries(fileManager),
                new ListEntry(fileManager),
                new RemoveEntry(fileManager),
                new ChangeWorkingDirectory(fileManager),
                new CreateFile(fileManager),
                new CreateDirectory(fileManager),
                new ShowWorkingDirectory(fileManager),
                new AppendDataToFile(fileManager),
                new ShowFileData(fileManager),
                new ChangeEntryPermissions(fileManager),
                new ChangeOwner(fileManager),
                });
        menu.open();
    }

}
