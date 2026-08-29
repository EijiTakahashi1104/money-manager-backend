package money_manager_api.service;

import money_manager_api.entity.MonthlyBudget;
import money_manager_api.repository.MonthlyBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonthlyBudgetService {

    @Autowired
    private MonthlyBudgetRepository monthlyBudgetRepository;

    public List<MonthlyBudget> getBudgetByMonth(String yearMonth) {
        return monthlyBudgetRepository.findByYearMonth(yearMonth);
    }

    public MonthlyBudget saveOrUpdateBudget(MonthlyBudget budget) {
        // 1. まずは指定された月の予算をListとして全件取得する
        List<MonthlyBudget> existingBudgets = monthlyBudgetRepository.findByYearMonth(budget.getYearMonth());

        // 2. 取得したListの中から、今回保存したいカテゴリIDと一致するデータを抽出する
        java.util.Optional<MonthlyBudget> existingBudget = existingBudgets.stream()
                .filter(b -> b.getCategoryId().equals(budget.getCategoryId()))
                .findFirst();

        // 3. 一致するデータが存在したかどうかの判定（isPresentが使えるようになる）
        if (existingBudget.isPresent()) {
            MonthlyBudget updated = existingBudget.get();
            updated.setBudgetAmount(budget.getBudgetAmount());
            return monthlyBudgetRepository.save(updated);
        } else {
            return monthlyBudgetRepository.save(budget);
        }
    }
}