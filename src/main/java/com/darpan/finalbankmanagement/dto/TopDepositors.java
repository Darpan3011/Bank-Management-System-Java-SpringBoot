package com.darpan.finalbankmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopDepositors {
    private Long accountId;
    private Long totalDeposited;
}
