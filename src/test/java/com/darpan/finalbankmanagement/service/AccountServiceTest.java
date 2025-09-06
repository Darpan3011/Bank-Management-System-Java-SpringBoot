package com.darpan.finalbankmanagement.service;

import com.darpan.finalbankmanagement.entity.UserAccount;
import com.darpan.finalbankmanagement.repository.AccountRepository;
import com.darpan.finalbankmanagement.servicesIml.AccountServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    
    @Mock
    private AccountRepository accountRepository;
    
    @InjectMocks
    private AccountServiceImp accountService;
    
    private UserAccount testAccount;
    
    @BeforeEach
    void setUp() {
        testAccount = new UserAccount();
        testAccount.setId(1L);
        testAccount.setFirstName("John");
        testAccount.setLastName("Doe");
        testAccount.setEmail("john@example.com");
        testAccount.setPhoneNumber("+1234567890");
        testAccount.setBankBalance(1000.0);
        testAccount.setAddress("123 Main St");
    }
    
    @Test
    public void testCreateAccount_Success() {
        // Given
        when(accountRepository.save(any(UserAccount.class))).thenReturn(testAccount);
        
        // When
        UserAccount result = accountService.createAccount(testAccount);
        
        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getFirstName()+ " "+result.getLastName());
        assertEquals("john@example.com", result.getEmail());
        verify(accountRepository, times(1)).save(testAccount);
    }
    
    @Test
    public void testGetAccountById_Success() {
        // Given
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        
        // When
        UserAccount result = accountService.getAccountById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getFirstName()+ " "+result.getLastName());
        verify(accountRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testAddMoney_Success() {
        // Given
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(UserAccount.class))).thenReturn(testAccount);
        
        // When
        accountService.addMoney(1L, 500.0);
        
        // Then
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(UserAccount.class));
    }
}
