/** @version $Id: ChangeOwner.java,v 1.7 2014/12/02 01:42:22 ist178942 Exp $ */
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
import poof.textui.UserUnknownException;
import poof.UserUnknownCoreException;


/**
 * §2.2.11.
 */
public class ChangeOwner extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public ChangeOwner(FileManager fileManager) {
        super(MenuEntry.CHOWN, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String entryName = IO.readString(Message.nameRequest());
        String username = IO.readString(Message.usernameRequest());
        try {
            _receiver.changeOwner(entryName, username);
        }
        catch (AccessDeniedCoreException adce) {
            throw new AccessDeniedException(adce.getUsername());
        }
        catch (EntryUnknownCoreException euce) {
            throw new EntryUnknownException(euce.getEntryName());
        }
        catch (UserUnknownCoreException uuce) {
            throw new UserUnknownException(uuce.getUsername());
        }
    }
}
