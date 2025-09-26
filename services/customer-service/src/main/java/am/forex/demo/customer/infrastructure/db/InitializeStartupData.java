package am.forex.demo.customer.infrastructure.db;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 18:27:32
 */
@Component
@RequiredArgsConstructor
public class InitializeStartupData {
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    @PostConstruct
    public void init() {
        String sql = """
                INSERT INTO customer.customer (name, email, balance) VALUES
                ('customer1', 'customer1@gmailcom', 4523.67),
                ('customer2', 'customer2@gmailcom', 1289.45),
                ('customer3', 'customer3@gmailcom', 9876.23),
                ('customer4', 'customer4@gmailcom', 234.56),
                ('customer5', 'customer5@gmailcom', 7654.12),
                ('customer6', 'customer6@gmailcom', 3210.89),
                ('customer7', 'customer7@gmailcom', 5432.10),
                ('customer8', 'customer8@gmailcom', 876.54),
                ('customer9', 'customer9@gmailcom', 1098.76),
                ('customer10', 'customer10@gmailcom', 4321.00),
                ('customer11', 'customer11@gmailcom', 567.89),
                ('customer12', 'customer12@gmailcom', 8901.23),
                ('customer13', 'customer13@gmailcom', 2345.67),
                ('customer14', 'customer14@gmailcom', 6789.01),
                ('customer15', 'customer15@gmailcom', 3456.78);
                """;

        r2dbcEntityTemplate.getDatabaseClient()
                .sql(sql)
                .then()
                .subscribe();
    }
}