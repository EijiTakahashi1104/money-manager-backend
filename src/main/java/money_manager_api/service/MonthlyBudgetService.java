package money_manager_api.service;

import money_manager_api.entity.MonthlyBudget;
import money_manager_api.repository.MonthlyBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyBudgetService {

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;

    public Optional<MonthlyBudget> getBudgetByMonth(String yearMonth) {
        return monthlyBudgetRepository.findByYearMonth(yearMonth);
    }

    public MonthlyBudget saveOrUpdateBudget(MonthlyBudget budget) {
        Optional<MonthlyBudget> existingBudget = monthlyBudgetRepository.findByYearMonth(budget.getYearMonth());

        if (existingBudget.isPresent()) {
            MonthlyBudget updated = existingBudget.get();
            updated.setBudgetAmount(budget.getBudgetAmount());
            return monthlyBudgetRepository.save(updated);
        } else {
            return monthlyBudgetRepository.save(budget);
        }
    }
}