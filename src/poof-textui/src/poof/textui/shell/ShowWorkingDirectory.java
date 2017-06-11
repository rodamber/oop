/** @version $Id: ShowWorkingDirectory.java,v 1.7 2014/11/29 18:28:21 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileManager;

/**
 * §2.2.7.
 */
public class ShowWorkingDirectory extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public ShowWorkingDirectory(FileManager fileManager) {
        super(MenuEntry.PWD, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() {
            IO.println(_receiver.showWorkingDirectory());
    }

}
