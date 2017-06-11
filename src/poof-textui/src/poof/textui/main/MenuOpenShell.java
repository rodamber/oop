/** @version $Id: MenuOpenShell.java,v 1.6 2014/12/02 01:42:21 ist178942 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileManager;

/**
 * Open shell menu.
 */
public class MenuOpenShell extends Command<FileManager> {

    /**
     * @param fileManager
     *          the command receiver.
     */
    public MenuOpenShell(FileManager fileManager) {
        super(MenuEntry.MENU_SHELL, fileManager,
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
        poof.textui.shell.MenuBuilder.menuFor(_receiver);
    }

}
