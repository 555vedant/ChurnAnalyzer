package com.example.Churn.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Churn.Entity.CustomerData;

public interface CustomerRepo extends JpaRepository<CustomerData , Integer>{
    
}
