import com.hn.jdstore.JdStoreApplication;
import com.hn.jdstore.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {JdStoreApplication.class})
@Slf4j
public class Test {

    @Resource
    private OrderService orderService;

    @org.junit.jupiter.api.Test
    void test() {
        System.out.println(orderService.findByOrderNo("21312312312"));
    }
}
