/** @version $Id: MenuOpenUserManagement.java,v 1.4 2014/11/29 18:28:20 ist178942 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileManager;

/**
 * Open user management menu.
 */
public class MenuOpenUserManagement extends Command<FileManager> {

    /**
     * @param fileManager
     *          the command receiver.
     */
    public MenuOpenUserManagement(FileManager fileManager) {
        super(MenuEntry.MENU_USER_MGT, fileManager,
             new ValidityPredicate<FileManager>(fileManager) {
                @Override
                public boolean isValid() {
                    return _receiver.getFileSystem() != null;
                }
            });
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() {
        poof.textui.user.MenuBuilder.menuFor(_receiver);
    }

}
