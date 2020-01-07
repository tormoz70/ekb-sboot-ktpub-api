package ekb.sboot.ktpub.api;

import ekb.sboot.ktpub.api.exception.DataFormatException;
import ekb.sboot.ktpub.api.models.BaseResponse;
import ekb.sboot.ktpub.api.models.Hint;
import ekb.sboot.ktpub.api.models.RspHint;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.bio4j.spring.common.LogWrapper;
import ru.bio4j.spring.common.security.SecurityFilterBase;
import ru.bio4j.spring.dba.JdbcHelper;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ktpub-api")
public class KTPubController extends AbstractRestHandler {
    LogWrapper LOG = LogWrapper.getLogger(SecurityFilterBase.class);

    private final String sharedKey = "SHARED_KEY";

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

    @Autowired
    private JdbcHelper jdbcHelper;

    @GetMapping
    public BaseResponse showStatus() {
        return new BaseResponse(SUCCESS_STATUS, 1);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Produce login func.", notes = "Returns UserDetails.")
    public UserDetails login(HttpServletRequest request, Principal principal) {
        return null;
    }

    @GetMapping("/search/hints")
    @ApiOperation(value = "Produce hints dictionary.", notes = "Returns list of hint objects in holder.")
    public RspHint hints(HttpServletRequest request) {
        LOG.debug("Start process request {hints}...");
        RspHint rslt = new RspHint();
        String sql = "select * from table(KTPUB_API18.getHints(:p_released, :p_unoblig, :p_subn, :p_query))";
        Map<String, Object> prms = new HashMap<>();
        prms.put("p_unoblig", "0");
        prms.put("p_released", "1");
        prms.put("p_subn", "0");
        prms.put("p_query", null);

        rslt.setResponse(jdbcHelper.query(sql, prms, Hint.class));
        LOG.debug("End processing request {hints}!");
        return rslt;
    }
}
