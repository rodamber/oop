/** @version $Id: ListAllUsers.java,v 1.9 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import java.util.List;

import poof.FileManager;


/**
 * §2.3.2.
 */
public class ListAllUsers extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public ListAllUsers(FileManager fileManager) {
        super(MenuEntry.LIST_USERS, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException  {
        IO.println(_receiver.listAllUsers());
    }
}
