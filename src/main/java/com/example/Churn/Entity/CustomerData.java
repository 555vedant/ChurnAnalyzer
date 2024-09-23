package com.example.Churn.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private float MonthlyCharge;
    private int AccountWeeks;
    
    private int ContractRenewal; // 0 or 1
    private int DataPlan; // 0 or 1
    private float DataUsage;
    private int CustServCalls;
    private float DayMins;
    private int DayCalls;
    private float OverageFee;
    private float RoamMins;
}
