/** @version $Id: Open.java,v 1.13 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.FileNotFoundException;
import java.io.IOException;

import poof.FileManager;

/**
 * Open existing file.
 */
public class Open extends Command<FileManager> {

    /**
     * @param fileManager
     *          the command receiver.
     */
    public Open(FileManager fileManager) {
        super(MenuEntry.OPEN, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        if (_receiver.getFileSystem() != null && _receiver.fileSystemWasChanged() && IO.readBoolean(Message.saveBeforeExit())) {
            Save save = new Save(_receiver);
            save.execute();
        }
        String filename = IO.readString(Message.openFile());
        try {
            _receiver.openFileSystem(filename);
        }
        catch (FileNotFoundException fnfe) {
            IO.println(Message.fileNotFound());
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

}
