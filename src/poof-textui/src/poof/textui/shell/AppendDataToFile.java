/** @version $Id: AppendDataToFile.java,v 1.4 2014/11/29 18:28:20 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.AccessDeniedCoreException;
import poof.EntryUnknownCoreException;
import poof.FileManager;
import poof.IsNotFileCoreException;
import poof.textui.AccessDeniedException;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;

/**
 * §2.2.8.
 */
public class AppendDataToFile extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public AppendDataToFile(FileManager fileManager) {
        super(MenuEntry.APPEND, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String fileName = IO.readString(Message.fileRequest());
        String text = IO.readString(Message.textRequest());
        try {
            _receiver.writeFile(fileName, text);
        }
        catch (EntryUnknownCoreException euce) {
            throw new EntryUnknownException(euce.getEntryName());
        }
        catch (IsNotFileCoreException infce) {
            throw new IsNotFileException(infce.getEntryName());
        }
        catch (AccessDeniedCoreException adce) {
            throw new AccessDeniedException(adce.getUsername());
        }
    }

}
