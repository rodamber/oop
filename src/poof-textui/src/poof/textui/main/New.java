/** @version $Id: New.java,v 1.10 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileManager;

/**
 * Open a new file.
 */
public class New extends Command<FileManager> {

    /**
     * @param fileManager
     *          the command receiver.
     */
    public New(FileManager fileManager) {
        super(MenuEntry.NEW, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        if (_receiver.getFileSystem() != null && _receiver.fileSystemWasChanged() && IO.readBoolean(Message.saveBeforeExit())) {
            Save save = new Save(_receiver);
            save.execute();
        }
        _receiver.createFileSystem();
    }

}
