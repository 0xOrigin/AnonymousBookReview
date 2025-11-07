package io.github._0xorigin.anonymousbookreview.author.specification;

import io.github._0xorigin.anonymousbookreview.author.Author;
import io.github._0xorigin.queryfilterbuilder.FilterContext;
import io.github._0xorigin.queryfilterbuilder.SortContext;
import io.github._0xorigin.queryfilterbuilder.base.filteroperator.Operator;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class AuthorSpecification {

    private final FilterContext.Template<Author> filterTemplate = generateFilterTemplate();
    private final SortContext.Template<Author> sortTemplate = generateSortTemplate();

    private FilterContext.Template<Author> generateFilterTemplate() {
        return FilterContext.buildTemplateForType(Author.class)
            .queryParam(builder ->
                builder
                    .addFilter("name", Operator.EQ)
                    .addFilter("createdAt", (root, cq, cb) -> root.get("createdAt"), Operator.GTE, Operator.LTE, Operator.BETWEEN)
            )
            .requestBody(builder ->
                builder
                    .addFilter("name", Operator.EQ)
                    .addFilter("updatedAt", (root, cq, cb) -> root.get("updatedAt"), Operator.GTE, Operator.LTE, Operator.BETWEEN)
            )
            .buildTemplate();
    }

    private SortContext.Template<Author> generateSortTemplate() {
        return SortContext.buildTemplateForType(Author.class)
            .queryParam(builder ->
                builder
                    .addAscSort("name")
                    .addSorts("id")
                    .addDescSort("updatedAt", (root, cq, cb) -> root.get("updatedAt"))
            )
            .requestBody(builder ->
                builder
                    .addAscSort("name")
                    .addSorts("id")
                    .addDescSort("updatedAt", (root, cq, cb) -> root.get("updatedAt"))
            )
            .buildTemplate();
    }
}
