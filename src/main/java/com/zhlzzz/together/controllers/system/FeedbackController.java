package com.zhlzzz.together.controllers.system;

import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.system.feedback.FeedbackEntity;
import com.zhlzzz.together.system.feedback.FeedbackParam;
import com.zhlzzz.together.system.feedback.FeedbackService;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "意见反馈", tags = {"Feedback"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackController {

    @Autowired
    private final FeedbackService feedbackService;

    @PostMapping(path = "/feedbacks")
    @ApiOperation(value = "接收意见反馈")
    @ResponseBody
    public ResponseEntity<String> saveFeedback(@Valid @RequestBody FeedbackParam feedbackParam, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        FeedbackEntity feedbackEntity = feedbackService.saveFeedback(feedbackParam);
        if (feedbackEntity != null)
            return new ResponseEntity<>(HttpStatus.OK);
        throw ApiExceptions.badRequest("反馈失败，稍后重试");
    }

    @GetMapping(path = "/feedbacks")
    @ApiOperation(value = "获取反馈意见列表")
    @ResponseBody
    public Set<FeedbackView> getFeedbackList() {
        Set<FeedbackEntity> feedbackEntities = feedbackService.findAll();
        return CollectionUtils.map(feedbackEntities,(r) ->{ return new FeedbackView(r);});
    }


}
