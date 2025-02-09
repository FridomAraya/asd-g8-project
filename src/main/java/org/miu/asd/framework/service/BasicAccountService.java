package org.miu.asd.framework.service;

import org.miu.asd.framework.dao.AccountDAO;
import org.miu.asd.framework.domain.Account;
import org.miu.asd.framework.domain.AccountEntry;
import org.miu.asd.framework.domain.AccountEvent;

import java.util.Observable;

public abstract class BasicAccountService extends Observable implements AccountService {
    private AccountDAO accountDAO;

    public BasicAccountService(AccountDAO accountDao) {
        this.accountDAO = accountDao;
    }

    @Override
    public Account getAccount(String accountID) {
        return accountDAO.getAccount(accountID);
    }

    @Override
    public void deposit(String accountID, Double amountOfMoney, AccountEvent accountEvent) {
        Account account = accountDAO.getAccount(accountID);
        AccountEntry accountEntry = performDeposit(account,amountOfMoney,accountEvent);
        accountDAO.updateAccount(account);
        notifyObservers(accountEntry);
    }

    @Override
    public void withdraw(String accountID, Double amountOfMoney, AccountEvent accountEvent) {
        Account account = accountDAO.getAccount(accountID);
        AccountEntry accountEntry = performWithdraw(account,amountOfMoney,accountEvent);
        accountDAO.updateAccount(account);
        notifyObservers(accountEntry);
    }

    protected abstract AccountEntry performWithdraw(Account account, Double amountOfMoney, AccountEvent accountEvent);

    protected abstract AccountEntry performDeposit(Account account, Double amountOfMoney, AccountEvent accountEvent);
}
