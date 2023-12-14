import com.hn.jdstore.JdStoreApplication;
import com.hn.jdstore.dao.ProductDao;
import com.hn.jdstore.domain.entity.HanmaProductDo;
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
        HanmaProductDo productDo =new HanmaProductDo();
        productDo.setId(20L);
        productDo.setProductStock(1L);
        log.info("update={}", productDao.updateStock(productDo));
    }
}
