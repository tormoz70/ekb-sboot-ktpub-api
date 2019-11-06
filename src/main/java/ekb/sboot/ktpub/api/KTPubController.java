package ekb.sboot.ktpub.api;

import ekb.sboot.ktpub.api.models.BaseResponse;
import ekb.sboot.ktpub.api.models.Hint;
import ekb.sboot.ktpub.api.models.RspHint;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;
import ru.bio4j.spring.helpers.jdbcHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ktpub-api")
public class KTPubController extends AbstractRestHandler {

    private final String sharedKey = "SHARED_KEY";

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @GetMapping
    public BaseResponse showStatus() {
        return new BaseResponse(SUCCESS_STATUS, 1);
    }


    @GetMapping("/search/hints")
    @ApiOperation(value = "Produce hints dictionary.", notes = "Returns list of hint objects in holder.")
    public RspHint hints(HttpServletRequest request) {
        RspHint rslt = new RspHint();
        String sql = "select * from table(KTPUB_API13.getHints(:p_released, :p_unoblig, :p_subn, :query$value))";
        Map<String, Object> prms = new HashMap<>();
        prms.put("p_released", "0");
        prms.put("p_unoblig", "0");
        prms.put("p_subn", "0");
        prms.put("query$value", null);

        rslt.setResponse(jdbcHelper.query(jdbcTemplate, sql, prms, Hint.class));
        return rslt;
    }
}
