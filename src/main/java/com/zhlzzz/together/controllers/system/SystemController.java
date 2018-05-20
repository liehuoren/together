package com.zhlzzz.together.controllers.system;


import com.zhlzzz.together.controllers.ApiExceptions;
import com.zhlzzz.together.system.AboutEntity;
import com.zhlzzz.together.system.AboutParam;
import com.zhlzzz.together.system.AboutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/system", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "系统", tags = {"System"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SystemController {

    @Autowired
    private final AboutService aboutService;

    @PutMapping(path = "/about")
    @ApiOperation(value = "更新小程序介绍")
    @ResponseBody
    public AboutView updateAdvert(@Valid @RequestBody AboutParam aboutParam, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map((e)->e.toString()).collect(Collectors.joining(";\n"));
            throw ApiExceptions.badRequest(errors);
        }
        return new AboutView(aboutService.updateAbout(aboutParam));
    }

    @GetMapping(path = "/about")
    @ApiOperation(value = "获取小程序简介")
    @ResponseBody
    public AboutView getAboutCompany() {
        AboutEntity aboutEntity = aboutService.findAbout().orElseThrow(() -> ApiExceptions.notFound("不存小程序简介。"));
        return new AboutView(aboutEntity);
    }
}
