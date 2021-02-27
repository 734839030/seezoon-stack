package com.seezoon.admin.modules.sys.dto.vue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public class RouteMeta {

    private final String title;
    private final String icon;
    private String frameSrc;

    public void setFrameSrc(String frameSrc) {
        this.frameSrc = frameSrc;
    }
}
