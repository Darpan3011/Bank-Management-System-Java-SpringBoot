package com.darpan.finalbankmanagement.services;

import com.darpan.finalbankmanagement.entity.UserAccount;

public interface AccountService {

    UserAccount createAccount(UserAccount userAccount);

    UserAccount getAccountById(Long id);

    UserAccount addMoney(Long id, Double amount);

    UserAccount withdrawMoney(Long id, Double amount);

}
