package com.rakuraku.apps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class AppsProvider {
    // Header에 달린 AppsId 가져옴
    public String getAppsId(HttpServletRequest request) {
        return request.getHeader("App-Id");
    }

}
