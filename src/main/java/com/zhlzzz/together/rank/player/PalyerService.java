package com.zhlzzz.together.rank.player;

import org.springframework.core.env.Environment;

public interface PalyerService {

    PalyerEntity savePalyer(Long userId, String palyerName, String shardId, Environment evn) ;

}
