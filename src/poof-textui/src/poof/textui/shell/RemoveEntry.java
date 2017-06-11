/** @version $Id: RemoveEntry.java,v 1.7 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.AccessDeniedCoreException;
import poof.EntryUnknownCoreException;
import poof.IllegalRemovalCoreException;
import poof.FileManager;

import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.IllegalRemovalException;

/**
 * §2.2.3.
 */
public class RemoveEntry extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public RemoveEntry(FileManager fileManager) {
        super(MenuEntry.RM, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String entryName = IO.readString(Message.nameRequest());
        try {
            _receiver.removeEntry(entryName);
        }
        catch (AccessDeniedCoreException adce) {
            throw new AccessDeniedException(adce.getUsername());
        }
        catch (EntryUnknownCoreException euce) {
            throw new EntryUnknownException(euce.getEntryName());
        }
        catch (IllegalRemovalCoreException irce) {
            throw new IllegalRemovalException();
        }
    }
}
