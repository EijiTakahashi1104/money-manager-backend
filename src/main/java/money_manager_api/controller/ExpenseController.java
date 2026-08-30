package money_manager_api.controller;

import money_manager_api.entity.Expense;
import money_manager_api.service.ExpenseService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支出APIの受付窓口（Controller）
 *
 * @RestController: このクラスが、画面（HTML）ではなく「データ（JSON）」を返すAPIであることを示す目印
 * @RequestMapping("/api/expenses"): このクラス内の処理はすべて「http://localhost:8080/api/expenses」へのアクセスで動くように設定
 */
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin("*")
public class ExpenseController {

    // Serviceを使うための準備
    private final ExpenseService expenseService;

    // コンストラクタ
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * 支出一覧を取得するAPI
     *
     * @GetMapping: HTTPの「GET」メソッドでアクセスされた時に動く
     */
    @GetMapping
    public List<Expense> getAllExpenses() {
        // Serviceにお願いして、すべての支出データを取得し、それをそのまま画面（リクエスト元）へ返す
        return expenseService.findAllExpenses();
    }

    /**
     * 支出データを登録するAPI
     *
     * @PostMapping: HTTPの「POST」メソッド（データを登録して！というリクエスト）で動く
     * @RequestBody: リクエストの本体（JSON）を、JavaのExpenseクラスに自動で変換して受け取る
     */
    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    /**
     * 支出データを削除するAPI
     *
     * @DeleteMapping("/{id}"): "/api/expenses/数字" というURLにDELETEメソッドでアクセスされた時に動く
     * @PathVariable: URLの "{id}" の部分を引数の "Long id" として受け取る
     */
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    /**
     * 支出データを更新するAPI
     *
     * @param id 更新対象のID
     * @param expense リクエストボディ（更新内容）
     * @return 更新されたExpenseオブジェクト
     */
    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    /**
     * 指定された年月の支出データを取得するAPI
     * URL例: /api/expenses/month/2026-08
     */
    @GetMapping("/month/{yearMonth}")
    public List<Expense> getExpensesByMonth(@PathVariable String yearMonth) {
        return expenseService.getExpensesByMonth(yearMonth);
    }

    // ★ 検索API
    @GetMapping("/search")
    public List<Expense> search(
            @RequestParam String monthStr,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword) {
        return expenseService.searchExpenses(monthStr, categoryId, keyword);
    }

    // ★ CSVダウンロードAPI
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCsv(
            @RequestParam String monthStr,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword) {

        byte[] csvData = expenseService.exportCsv(monthStr, categoryId, keyword);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses_" + monthStr + ".csv")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(csvData);
    }
}