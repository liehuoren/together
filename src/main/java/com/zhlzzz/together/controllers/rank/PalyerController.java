package com.zhlzzz.together.controllers.rank;

import com.zhlzzz.together.rank.player.PalyerEntity;
import com.zhlzzz.together.rank.player.PalyerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/rank/{userId:\\d+}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "用户游戏内信息", tags = {"Rank"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PalyerController {

    @Autowired
    private PalyerService palyerService;
    @Autowired
    private Environment env;

    @GetMapping
    @ApiOperation(value = "排行榜")
    @ResponseBody
    public PalyerView addPalyer(@PathVariable Long userId,@RequestParam String nickName,@RequestParam String shardId){
        PalyerEntity palyerEntity = palyerService.savePalyer(userId,nickName,shardId,env);
        return new PalyerView(palyerEntity);
    }


//    @GetMapping
//    @ApiOperation(value = "排行榜")
//    @ResponseBody
//    public static void getRank() throws Exception {

        //获取seasonId
//        String seasonUrl = "https://api.playbattlegrounds.com/shards/{shardsId}/seasons";
//        SeasonService rankService = new SeasonServiceImpl();
//        String seasonId = rankService.getCurrentSeasonId(seasonUrl,apiKey,"pc-na");
//        endpointUrl = endpointUrl + "/shards/pc-as/players?filter[playerNames]=Mr-Leon";
//        endpointUrl ="https://api.playbattlegrounds.com/status";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        headers.add("Authorization","Bearer "+apiKey);
//        headers.add("Accept","application/vnd.api+json");
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//        ResponseEntity<String> response ;
//        response = restTemplate.exchange(endpointUrl, HttpMethod.GET, entity, String.class);
//        String body = response.getBody();
//        System.out.println(body);
//    }
//
//    public static void main(String[] arge) throws Exception {
//        getRank();
//    }
}
