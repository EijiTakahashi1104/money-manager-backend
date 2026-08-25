package money_manager_api.service;

import money_manager_api.entity.Expense;
import money_manager_api.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支出に関する処理のルール（ビジネスロジック）を書くクラス
 *
 * @Service: Spring Bootに「これは計算や処理を行うServiceクラスだよ」と教える目印
 */
@Service
public class ExpenseService {

    // Repositoryを使うための準備
    private final ExpenseRepository expenseRepository;

    // コンストラクタ（このクラスが呼ばれた時に、自動的にRepositoryをセットしてもらう）
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * すべての支出データを取得するメソッド
     *
     * @return 支出データのリスト（List<Expense>）
     */
    public List<Expense> findAllExpenses() {
        // JpaRepositoryが用意してくれている findAll() メソッドを使って、テーブルの中身を全部取得して返す
        return expenseRepository.findAll();
    }

    /**
     * 新しい支出データを保存するメソッド
     *
     * @param expense 画面から送られてきた支出データ
     * @return 保存が完了した支出データ（自動でIDが付与されたもの）
     */
    public Expense saveExpense(Expense expense) {
        // JpaRepositoryの save() を使うだけで、INSERT（登録）のSQLを自動で実行してくれます
        return expenseRepository.save(expense);
    }

    /**
     * 指定したIDの支出データを削除するメソッド
     *
     * @param id 削除したいデータのID
     */
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    /**
     * 支出データを更新する
     *
     * @param id 更新対象のID
     * @param updatedExpense 更新後のデータ
     * @return 更新されたExpenseオブジェクト
     */
    public Expense updateExpense(Long id, Expense updatedExpense) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setTitle(updatedExpense.getTitle());
            expense.setCategory(updatedExpense.getCategory());
            expense.setAmount(updatedExpense.getAmount());
            expense.setExpenseDate(updatedExpense.getExpenseDate());
            expense.setMemo(updatedExpense.getMemo());
            return expenseRepository.save(expense);
        }).orElseThrow(() -> new RuntimeException("対象のデータが見つかりません: " + id));
    }

    /**
     * 指定した年月の支出リストを取得する
     *
     * @param yearMonth 検索対象の年月（例: 2026-08）
     */
    public List<Expense> getExpensesByMonth(String yearMonth) {
        return expenseRepository.findByYearMonth(yearMonth);
    }
}