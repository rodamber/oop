/** @version $Id: CreateFile.java,v 1.7 2014/12/02 01:42:22 ist178942 Exp $ */
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
 * §2.2.5.
 */
public class CreateFile extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public CreateFile(FileManager fileManager) {
        super(MenuEntry.TOUCH, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String fileName = IO.readString(Message.fileRequest());
        try {
            _receiver.createFile(fileName);
        }
        catch (EntryExistsCoreException eece) {
            throw new EntryExistsException(eece.getEntryName());
        }
        catch (AccessDeniedCoreException adce){
            throw new AccessDeniedException(adce.getUsername());
        }
    }
}
