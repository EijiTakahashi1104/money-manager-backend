package money_manager_api.controller;

import lombok.RequiredArgsConstructor;
import money_manager_api.entity.Category;
import money_manager_api.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    // 基本予算の更新API
    @PutMapping("/{id}/default-budget")
    public Category updateDefaultBudget(@PathVariable Long id, @RequestBody Map<String, BigDecimal> requestBody) {
        BigDecimal budget = requestBody.get("default_budget_amount");
        return categoryService.updateDefaultBudget(id, budget);
    }
}