import com.hn.jdstore.JdStoreApplication;
import com.hn.jdstore.dao.ProductDao;
import com.hn.jdstore.entity.HanmaProductEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {JdStoreApplication.class})
@Slf4j
public class Test {

    @Resource
    private ProductDao productDao;

    @org.junit.jupiter.api.Test
    void test() {
        HanmaProductEntity hanmaProductEntity =new HanmaProductEntity();
        hanmaProductEntity.setId(20L);
        hanmaProductEntity.setProductStock(1L);
        log.info("update={}", productDao.updateStock(hanmaProductEntity));
    }
}
