package com.darpan.finalbankmanagement.controller;

import com.darpan.finalbankmanagement.UserPrincipalService;
import com.darpan.finalbankmanagement.entity.UserAccount;
import com.darpan.finalbankmanagement.servicesIml.AccountServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImp accountServiceImp;
    private final UserPrincipalService userPrincipalService;

    public AccountController(AccountServiceImp accountService, UserPrincipalService userPrincipalService) {
        this.accountServiceImp = accountService;
        this.userPrincipalService = userPrincipalService;
    }

    @PostMapping("/create")
    public ResponseEntity createAccount(@RequestBody UserAccount userAccount){
        try {
            UserAccount userAccount1 = accountServiceImp.createAccount(userAccount);
            if(userAccount1 == null){
                return ResponseEntity.status(404).body("Account creation failed");
            }
            return ResponseEntity.ok(userAccount1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/add/{amount}")
    public ResponseEntity addMoney(@PathVariable Double amount){
        if(amount <= 0){
            return ResponseEntity.badRequest().body("Amount should be greater than zero");
        }
        try {
            accountServiceImp.addMoney(userPrincipalService.getLoggedInUserId(), amount);
            UserAccount userAccount1 = accountServiceImp.getAccountById(userPrincipalService.getLoggedInUserId());
            return ResponseEntity.ok(userAccount1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdraw/{amount}")
    public ResponseEntity<?> withdrawMoney(@PathVariable Double amount) {
        if (amount <= 0) {
            return ResponseEntity.badRequest().body("Amount should be greater than zero");
        }
        try {
            UserAccount userAccount = accountServiceImp.withdrawMoney(userPrincipalService.getLoggedInUserId(), amount);
            return ResponseEntity.ok(userAccount);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAccountById(){
        try {
                    UserAccount userAccount = accountServiceImp.getAccountById(userPrincipalService.getLoggedInUserId());
                    if(userAccount == null){
                        return ResponseEntity.status(404).body("No account found");
                    }
                    return ResponseEntity.ok(userAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity getBalance(){
        try {
                Double amount = accountServiceImp.getAccountById(userPrincipalService.getLoggedInUserId()).getBankBalance();
                return ResponseEntity.ok(amount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
