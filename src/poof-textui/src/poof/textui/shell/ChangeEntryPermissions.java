/** @version $Id: ChangeEntryPermissions.java,v 1.7 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.AccessDeniedCoreException;
import poof.EntryUnknownCoreException;
import poof.FileManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;

/**
 * §2.2.10.
 */
public class ChangeEntryPermissions extends Command<FileManager> {
    /**
     * @param FileManager
     *          the command receiver.
     */
    public ChangeEntryPermissions(FileManager fileManager) {
        super(MenuEntry.CHMOD, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String entryName = IO.readString(Message.nameRequest());
        boolean permission = IO.readBoolean(Message.publicAccess());
        try {
            _receiver.changePermission(entryName, permission);
        }
        catch (EntryUnknownCoreException euce) {
            throw new EntryUnknownException(euce.getEntryName());
        }
        catch (AccessDeniedCoreException adce) {
            throw new AccessDeniedException(adce.getUsername());
        }
    }
}
