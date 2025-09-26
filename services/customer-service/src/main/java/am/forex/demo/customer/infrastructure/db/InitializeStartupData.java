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
                        ('customer1', 'customer1@gmail.com', 4523.67),
                        ('customer2', 'customer2@gmail.com', 1289.45),
                        ('customer3', 'customer3@gmail.com', 9876.23),
                        ('customer4', 'customer4@gmail.com', 234.56),
                        ('customer5', 'customer5@gmail.com', 7654.12),
                        ('customer6', 'customer6@gmail.com', 3210.89),
                        ('customer7', 'customer7@gmail.com', 5432.10),
                        ('customer8', 'customer8@gmail.com', 876.54),
                        ('customer9', 'customer9@gmail.com', 1098.76),
                        ('customer10', 'customer10@gmail.com', 4321.00),
                        ('customer11', 'customer11@gmail.com', 567.89),
                        ('customer12', 'customer12@gmail.com', 8901.23),
                        ('customer13', 'customer13@gmail.com', 2345.67),
                        ('customer14', 'customer14@gmail.com', 6789.01),
                        ('customer15', 'customer15@gmail.com', 3456.78);
                """;

        r2dbcEntityTemplate.getDatabaseClient()
                .sql(sql)
                .then()
                .subscribe();
    }
}