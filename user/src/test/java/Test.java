import com.hn.user.UserServerApplication;
import com.hn.user.domain.request.WechatUserInfoRequest;
import com.hn.user.service.WechatUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {UserServerApplication.class})
@Slf4j
public class Test {

    @Resource
    private WechatUserService wechatUserService;

    @org.junit.jupiter.api.Test
    void test() {
        WechatUserInfoRequest userInfoDto = new WechatUserInfoRequest();
        userInfoDto.setNickname("zz");
        userInfoDto.setOpenid("aa");
        userInfoDto.setHeadimgurl("img");
        System.out.println(wechatUserService.wechatUserLogin(userInfoDto));
    }
}
