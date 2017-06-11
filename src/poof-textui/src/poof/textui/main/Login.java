/** @version $Id: Login.java,v 1.7 2014/11/29 18:28:20 ist178942 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.FileNotFoundException;
import java.io.IOException;

import poof.textui.UserUnknownException;
import poof.UserUnknownCoreException;
import poof.FileManager;

/**
 * §2.1.2.
 */
public class Login extends Command<FileManager> {

    /**
     * @param fileManager
     *          the command receiver.
     */
    public Login(FileManager fileManager) {
        super(MenuEntry.LOGIN, fileManager,
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
        String username = IO.readString(Message.usernameRequest());
        try {
            _receiver.login(username);
        }
        catch (UserUnknownCoreException uuce) {
            throw new UserUnknownException(uuce.getUsername());
        }
    }
}
