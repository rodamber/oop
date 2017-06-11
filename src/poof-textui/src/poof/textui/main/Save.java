/** @version $Id: Save.java,v 1.8 2014/11/29 18:28:20 ist178942 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileManager;


/**
 * Save to file under current name (if unnamed, query for name).
 */
public class Save extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public Save(FileManager fileManager) {
        super(MenuEntry.SAVE, fileManager,
                new ValidityPredicate<FileManager>(fileManager) {
                    @Override
                    public boolean isValid() {
                        return _receiver.getFileSystem() != null;
                    }
                });
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        if (_receiver.getFileName() != null) {
            _receiver.saveFileSystem();
        }
        else if (_receiver.fileSystemWasChanged()) {
            _receiver.saveFileSystem(IO.readString(Message.newSaveAs()));
        }
    }
}
