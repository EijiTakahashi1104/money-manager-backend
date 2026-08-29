package money_manager_api.controller;

import money_manager_api.entity.MonthlyBudget;
import money_manager_api.service.MonthlyBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin("*")
public class MonthlyBudgetController {

    @Autowired
    private MonthlyBudgetService monthlyBudgetService;

    @GetMapping("/{yearMonth}")
    public List<MonthlyBudget> getBudget(@PathVariable String yearMonth) {
        return monthlyBudgetService.getBudgetByMonth(yearMonth);
    }

    @PostMapping
    public MonthlyBudget saveBudget(@RequestBody MonthlyBudget budget) {
        return monthlyBudgetService.saveOrUpdateBudget(budget);
    }
}