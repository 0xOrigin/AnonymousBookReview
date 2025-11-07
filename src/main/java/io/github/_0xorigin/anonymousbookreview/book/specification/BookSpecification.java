package io.github._0xorigin.anonymousbookreview.book.specification;

import io.github._0xorigin.anonymousbookreview.book.Book;
import io.github._0xorigin.queryfilterbuilder.FilterContext;
import io.github._0xorigin.queryfilterbuilder.SortContext;
import io.github._0xorigin.queryfilterbuilder.base.filteroperator.Operator;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class BookSpecification {
    private final FilterContext.Template<Book> filterTemplate = generateFilterTemplate();
    private final SortContext.Template<Book> sortTemplate = generateSortTemplate();

    private FilterContext.Template<Book> generateFilterTemplate() {
        return FilterContext.buildTemplateForType(Book.class)
            .queryParam(builder ->
                builder
                    .addFilter("title", Operator.EQ, Operator.CONTAINS, Operator.STARTS_WITH, Operator.ENDS_WITH)
                    .addFilter("isbn", Operator.EQ)
                    .addFilter("author", Operator.EQ, Operator.IN)
                    .addFilter("createdAt", (root, cq, cb) -> root.get("createdAt"), Operator.GTE, Operator.LTE, Operator.BETWEEN)
            )
            .requestBody(builder ->
                builder
                    .addFilter("title", Operator.EQ)
                    .addFilter("author.name", Operator.EQ, Operator.CONTAINS, Operator.ICONTAINS, Operator.IN)
                    .addFilter("numberOfPages", Operator.BETWEEN, Operator.GTE, Operator.LTE)
                    .addFilter("publishedDate", (root, cq, cb) -> root.get("publishedDate"), Operator.GTE, Operator.LTE, Operator.BETWEEN)
                    .addFilter("updatedAt", (root, cq, cb) -> root.get("updatedAt"), Operator.GTE, Operator.LTE, Operator.BETWEEN)
            )
            .buildTemplate();
    }

    private SortContext.Template<Book> generateSortTemplate() {
        return SortContext.buildTemplateForType(Book.class)
            .queryParam(builder ->
                builder
                    .addSorts("title")
                    .addAscSort("numberOfPages")
                    .addSorts("publishedDate")
                    .addDescSort("updatedAt", (root, cq, cb) -> root.get("updatedAt"))
            )
            .requestBody(builder ->
                builder
                    .addAscSort("title")
                    .addDescSort("isbn")
                    .addSorts("id")
                    .addSorts("author.name")
                    .addDescSort("updatedAt", (root, cq, cb) -> root.get("updatedAt"))
            )
            .buildTemplate();
    }
}
