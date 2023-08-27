package com.gpingguo.model.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 平台
 * </p>
 *
 * @author gpingguo
 * @since 2022-11-7
 */
@AllArgsConstructor
public enum PlatformEnum {

    ALI("ali"),
    BAIDU("baidu"),
    TENCENT("tencent");

    @Getter
    private final String name;
}
