package com.darpan.finalbankmanagement.servicesIml;

import com.darpan.finalbankmanagement.entity.Transaction;
import com.darpan.finalbankmanagement.entity.UserAccount;
import com.darpan.finalbankmanagement.entity.UserPrincipal;
import com.darpan.finalbankmanagement.repository.AccountRepository;
import com.darpan.finalbankmanagement.repository.TransactionRepository;
import com.darpan.finalbankmanagement.services.AccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.InsufficientResourcesException;

@Service
public class AccountServiceImp implements AccountService, UserDetailsService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImp(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserAccount createAccount(UserAccount userAccount) {
        try {
            userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
            accountRepository.save(userAccount);
            return userAccount;
        } catch (Exception e) {
            throw new RuntimeException("Error creating account");
        }
    }

    @Override
    public UserAccount getAccountById(Long id) {
        try {
            return accountRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting account");
        }
    }

    @Override
    public UserAccount addMoney(Long id, Double amount) {
        try {
            UserAccount userAccount = getAccountById(id);
            double bankBalance = userAccount.getBankBalance();
            accountRepository.updateBalanceByUserId(id, amount + bankBalance);


            Transaction transaction = new Transaction();
            transaction.setCurrBalance(bankBalance);
            transaction.setTransactionAmount(amount);
            transaction.setDescription("Deposited");
            transaction.setNewBalance(amount + bankBalance);
            transaction.setType(Transaction.TransactionType.DEPOSIT);
            transaction.setUser(userAccount);

            transactionRepository.save(transaction);

            return getAccountById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error adding money to account");
        }


    }

    @Override
    public UserAccount withdrawMoney(Long id, Double amount) {
        try {
            UserAccount userAccount = getAccountById(id);
            double bankBalance = userAccount.getBankBalance();
            if(bankBalance < amount){
                throw new RuntimeException("Insufficient balance");
            }
            accountRepository.updateBalanceByUserId(id, bankBalance - amount);

            Transaction transaction = new Transaction();
            transaction.setCurrBalance(bankBalance);
            transaction.setTransactionAmount(amount);
            transaction.setDescription("Withdrawn");
            transaction.setNewBalance(bankBalance - amount);
            transaction.setType(Transaction.TransactionType.WITHDRAW);
            transaction.setUser(userAccount);

            transactionRepository.save(transaction);
            return getAccountById(id);
        }
        catch (Exception e) {
            throw new RuntimeException("Error withdrawing money from account", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = accountRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }
}
