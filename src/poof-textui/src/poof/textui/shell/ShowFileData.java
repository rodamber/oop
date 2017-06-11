/** @version $Id: ShowFileData.java,v 1.8 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.EntryUnknownCoreException;
import poof.FileManager;
import poof.IsNotFileCoreException;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;

/**
 * §2.2.9.
 */
public class ShowFileData extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public ShowFileData(FileManager fileManager) {
        super(MenuEntry.CAT, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String fileName = IO.readString(Message.fileRequest());
        try {
            String content = _receiver.showFile(fileName);
            if (!content.equals("")) {
                IO.println(content);
            }
        }
        catch (EntryUnknownCoreException euce) {
            throw new EntryUnknownException(euce.getEntryName());
        }
        catch (IsNotFileCoreException infce) {
            throw new IsNotFileException(infce.getEntryName());
        }
    }
}
