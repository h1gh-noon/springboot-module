package com.hn.jdstore.model;

import lombok.Data;

@Data
public class IPLocation {

    private String address;
    private Content content;
    private int status;

    @Data
    static class AddressDetail {
        private String city;
        private int city_code;
        private String province;
    }

    @Data
    static class Content {
        private String address;
        private AddressDetail address_detail;
        private Point point;
    }

    @Data
    static class Point {
        private String x;
        private String y;
    }
}
