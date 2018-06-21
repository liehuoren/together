package com.lawoba.together.system;

public interface AboutService {

    AboutEntity updateAbout(String company, String logo, String introduction);
    AboutEntity getAbout();
}
