/** @version $Id: CreateDirectory.java,v 1.7 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.AccessDeniedCoreException;
import poof.EntryExistsCoreException;
import poof.FileManager;
import poof.textui.AccessDeniedException;
import poof.textui.EntryExistsException;

/**
 * §2.2.6.
 */
public class CreateDirectory extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public CreateDirectory(FileManager fileManager) {
        super(MenuEntry.MKDIR, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String dirName = IO.readString(Message.directoryRequest());
        try {
            _receiver.createDirectory(dirName);
        }
        catch (AccessDeniedCoreException adce) {
            throw new AccessDeniedException(adce.getUsername());
        }
        catch (EntryExistsCoreException eece) {
            throw new EntryExistsException(eece.getEntryName());
        }
    }
}
