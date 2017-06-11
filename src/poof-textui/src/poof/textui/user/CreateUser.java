/** @version $Id: CreateUser.java,v 1.6 2014/12/02 01:42:22 ist178942 Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.textui.AccessDeniedException;
import poof.textui.UserExistsException;

import poof.AccessDeniedCoreException;
import poof.UserExistsCoreException;
import poof.FileManager;

/**
 * §2.3.1.
 */
public class CreateUser extends Command<FileManager> {
    /**
     * @param fileManager
     *          the command receiver.
     */
    public CreateUser(FileManager fileManager) {
        super(MenuEntry.CREATE_USER, fileManager);
    }

    /** @see ist.po.ui.Command#execute() */
    @Override
    public final void execute() throws DialogException, IOException {
        String username = IO.readString(Message.usernameRequest());
        String name = IO.readString(Message.nameRequest());

        try {
            _receiver.createUser(username, name);
        }
        catch (AccessDeniedCoreException adce) {
            throw new AccessDeniedException(adce.getUsername());
        }
        catch (UserExistsCoreException uece) {
            throw new UserExistsException(uece.getUsername());
        }
    }
}
