package com.gpingguo.model.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * ip类型
 * </p>
 *
 * @author gpingguo
 * @since 2022-11-7
 */
@AllArgsConstructor
public enum IpTypeEnum {

    IPV4("ipv4"),
    IPV6("ipv6");

    @Getter
    private final String name;
}
