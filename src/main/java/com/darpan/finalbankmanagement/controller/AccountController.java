package com.darpan.finalbankmanagement.controller;

import com.darpan.finalbankmanagement.UserPrincipalService;
import com.darpan.finalbankmanagement.dto.AccountCreateRequest;
import com.darpan.finalbankmanagement.entity.UserAccount;
import com.darpan.finalbankmanagement.servicesIml.AccountServiceImp;
import com.darpan.finalbankmanagement.validation.ValidAmount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;

@RestController
@RequestMapping("/account")
@Tag(name = "Account Management", description = "APIs for managing bank accounts")
public class AccountController {

    private final AccountServiceImp accountServiceImp;
    private final UserPrincipalService userPrincipalService;

    public AccountController(AccountServiceImp accountService, UserPrincipalService userPrincipalService) {
        this.accountServiceImp = accountService;
        this.userPrincipalService = userPrincipalService;
    }

    @Operation(summary = "Create a new bank account", description = "Creates a new bank account with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Account creation failed")
    })
    @PostMapping("/create")
    public ResponseEntity createAccount(@Valid @RequestBody AccountCreateRequest request){
        try {
            UserAccount userAccount = new UserAccount();
            userAccount.setFirstName(request.getAccountHolderName());
            userAccount.setEmail(request.getEmail());
            userAccount.setPhoneNumber(request.getPhoneNumber());
            userAccount.setBankBalance(request.getInitialBalance());
            userAccount.setAddress(request.getAddress());
            
            UserAccount createdAccount = accountServiceImp.createAccount(userAccount);
            if(createdAccount == null){
                return ResponseEntity.status(404).body("Account creation failed");
            }
            return ResponseEntity.ok(createdAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(summary = "Add money to account", description = "Deposits the specified amount to the user's account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Money added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid amount or validation error")
    })
    @PostMapping("/add/{amount}")
    public ResponseEntity addMoney(@Parameter(description = "Amount to deposit", required = true) @PathVariable @ValidAmount Double amount){
        try {
            accountServiceImp.addMoney(userPrincipalService.getLoggedInUserId(), amount);
            UserAccount userAccount1 = accountServiceImp.getAccountById(userPrincipalService.getLoggedInUserId());
            return ResponseEntity.ok(userAccount1);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdraw/{amount}")
    public ResponseEntity<?> withdrawMoney(@PathVariable @ValidAmount Double amount) {
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
