import com.alibaba.fastjson2.JSON;
import com.hn.jdstore.JdStoreApplication;
import com.hn.jdstore.dao.SearchDao;
import com.hn.jdstore.dto.ProductDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {JdStoreApplication.class})
@Slf4j
public class Test {

    @Resource
    private SearchDao searchDao;

    @org.junit.jupiter.api.Test
    void test() {
        List<Map<String, Object>> maps = searchDao.searchAll("%1%");
        List<ProductDto> productDtos = JSON.parseArray(JSON.toJSONString(maps), ProductDto.class);
        log.info("productDtos={}", productDtos);
    }
}
