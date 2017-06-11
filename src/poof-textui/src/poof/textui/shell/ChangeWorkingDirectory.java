/** @version $Id: ChangeWorkingDirectory.java,v 1.6 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.EntryUnknownCoreException;
import poof.IsNotDirectoryCoreException;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotDirectoryException;
import poof.FileManager;

/**
 * §2.2.4.
 */
public class ChangeWorkingDirectory extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public ChangeWorkingDirectory(FileManager fileManager) {
        super(MenuEntry.CD, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String dirName = IO.readString(Message.directoryRequest());
        try {
             _receiver.changeDirectory(dirName);
        }
        catch (EntryUnknownCoreException euce) {
            throw new EntryUnknownException(euce.getEntryName());
        }
        catch (IsNotDirectoryCoreException indce) {
            throw new IsNotDirectoryException(indce.getEntryName());
        }
    }

}
