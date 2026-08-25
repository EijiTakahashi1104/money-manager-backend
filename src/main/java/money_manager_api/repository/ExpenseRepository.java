package money_manager_api.repository;

import money_manager_api.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * 指定された年月（YYYY-MM）の支出リストを取得する
     * データベースの expense_date を文字列として前方一致で検索します
     */
    @Query("SELECT e FROM Expense e WHERE CAST(e.expenseDate AS string) LIKE :yearMonth%")
    List<Expense> findByYearMonth(@Param("yearMonth") String yearMonth);
}