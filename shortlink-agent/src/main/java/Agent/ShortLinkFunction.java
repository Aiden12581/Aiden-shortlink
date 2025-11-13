package Agent;

import com.example.shortlinkagent.dto.ShortLinkCreateReqDTO;
import org.springframework.ai.chat.prompt.function.FunctionCallback;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ShortLinkFunction implements FunctionCallback {

    @Override
    public String function() {
        return """
            根据用户需求创建一个短链接。
            如果用户没有提供 originUrl，请要求其补充。
            """;
    }

    @Override
    public String apply(Map<String, Object> args) {
        // 模拟调用短链服务
        String originUrl = (String) args.get("originUrl");
        String gid = (String) args.getOrDefault("gid", "default");
        Integer validDateType = (Integer) args.getOrDefault("validDateType", 0);

        if (originUrl == null || originUrl.isEmpty()) {
            return "缺少原始链接，请提供 URL。";
        }

        // 模拟生成 shortUri
        String shortUri = UUID.randomUUID().toString().substring(0, 6);
        String fullShortUrl = "https://s.cn/" + shortUri;

        return String.format("""
            已生成短链：
            - 原始链接：%s
            - 短链地址：%s
            - 分组：%s
            - 有效期：%s
            
            建议：该内容适合发布在微博，注意添加话题标签。
            """,
                originUrl, fullShortUrl, gid,
                validDateType == 0 ? "永久有效" : "自定义有效期"
        );
    }

    // 告诉 LLM 这个函数的参数结构
    @Override
    public FunctionCallback.FunctionDefinition definition() {
        return FunctionCallback.FunctionDefinition.builder()
                .name("create_short_link")
                .description("根据用户请求生成短链接")
                .addParameter("originUrl", "string", "原始长链接", true)
                .addParameter("gid", "string", "所属分组ID，如 default/group_a", false)
                .addParameter("validDateType", "integer", "有效期类型：0=永久，1=临时", false)
                .build();
    }
}

