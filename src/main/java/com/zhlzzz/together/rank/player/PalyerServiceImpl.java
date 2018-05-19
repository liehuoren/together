package com.zhlzzz.together.rank.player;

import com.zhlzzz.together.rank.HttpUtils;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class PalyerServiceImpl implements PalyerService {

    private final PalyerRepository palyerRepository;
    private final UserRepository userRepository;

    @Override
    public PalyerEntity savePalyer(Long userId, String palyerName, String shardId, Environment evn) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(userId));
        PalyerEntity palyerEntity = new PalyerEntity();
        palyerEntity.setUserId(user.getId());
        palyerEntity.setPalyerName(palyerName);
        palyerEntity.setShardId(shardId);
        Map<String,String> urlVariables = new HashMap<String,String>();
        urlVariables.put("shardId",shardId);
        urlVariables.put("palyerName",palyerName);
        ResponseEntity<Object> response = HttpUtils.getPubgResponse(evn.getProperty("pubg.palyerUrl"),evn.getProperty("pubg.apiKey"),urlVariables);
        System.out.println(response.getBody());
        Map<String,Object> o = (Map<String, Object>) response.getBody();
//        List<SeasonEntity> seasons = (List<SeasonEntity>) o.get("data");
//        for (SeasonEntity season:seasons) {
//            if (season.getAttributes().isCurrentSeason() && !season.getAttributes().isOffSeason())
//                return  season.getId();
//        }
        return null;//palyerRepository.save(palyerEntity);
    }


}
