package com.zhlzzz.together.controllers.system;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping(path = "/feedbacks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "意见反馈", tags = {"Feedback"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeedbackController {

    @PostMapping
    @ApiOperation(value = "接收意见反馈")
    @ResponseBody
    public ResponseEntity<String> saveFeedback(@RequestParam Long userId, @Email @RequestParam String email, @RequestParam String content) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
