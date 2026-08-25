package money_manager_api.repository;

import money_manager_api.entity.MonthlyBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudget, Long> {
    Optional<MonthlyBudget> findByYearMonth(String yearMonth);
}