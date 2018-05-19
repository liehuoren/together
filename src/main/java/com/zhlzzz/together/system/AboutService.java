package com.zhlzzz.together.system;

import java.util.Optional;

public interface AboutService {

    AboutEntity updateAbout(AboutParam aboutParam);
    Optional<? extends AboutEntity>  findAbout();

}
