/** @version $Id: ListAllEntries.java,v 1.13 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import java.util.List;

import poof.FileManager;

/**
 * §2.2.1.
 */
public class ListAllEntries extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public ListAllEntries(FileManager fileManager) {
        super(MenuEntry.LS, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        IO.println(_receiver.listAllEntries());
    }
}
