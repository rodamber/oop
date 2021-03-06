/** @version $Id: Deposit.java,v 1.2 2011/09/25 14:49:28 david Exp $ */
package bank.textui.bankaccount;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;

import java.io.IOException;

import bank.BankAccount;
import bank.InvalidDepositException;
import bank.NegativeAmountException;

/**
 * Class Deposit represents commands for making deposits.
 */
public class Deposit extends Command<BankAccount> {

	/**
	 * @param receiver
	 *            the target account.
	 */
	public Deposit(BankAccount receiver) {
		super(MenuEntry.DEPOSIT, receiver);
	}

	/**
	 * @see ist.po.ui.Command#execute()
	 */
	@Override
	public final void execute() throws DialogException, IOException {
		try {
			float amount = IO.readFloat(Message.requestAmount());
			_receiver.deposit(amount);
		} catch (InvalidDepositException e) {
			throw new InvalidDepositUIException();
		} catch (NegativeAmountException e) {
			throw new NegativeDepositUIException(e.getAmount());
		}
	}
}

// $Log: Deposit.java,v $
// Revision 1.2  2011/09/25 14:49:28  david
// New version of the bank application. The textui version has been updated
// and now uses exceptions as a bridge to convey to the menu class errors
// in operations.
//