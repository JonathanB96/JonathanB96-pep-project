package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account registerAccount(Account account) {
        // Check if the username is not blank and password is at least 4 characters long
        if (account.getUsername().trim().isEmpty() || account.getPassword().length() < 4) {
            return null;
        }

        // Check if an account with the given username already exists
        Account existingAccount = accountDAO.findAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            return null;
        }

        // Save the new account to the database
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account) {
        Account existingAccount = accountDAO.findAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        }
        return null;
    }
}

