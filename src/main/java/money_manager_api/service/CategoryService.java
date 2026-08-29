package money_manager_api.service;

import lombok.RequiredArgsConstructor;
import money_manager_api.entity.Category;
import money_manager_api.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // カテゴリ一覧を取得
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    // 基本予算（デフォルト値）を更新する
    @Transactional
    public Category updateDefaultBudget(Long id, BigDecimal newBudget) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("指定されたカテゴリが見つかりません。 ID: " + id));

        category.setDefaultBudgetAmount(newBudget);
        return categoryRepository.save(category);
    }
}