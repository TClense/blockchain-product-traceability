package com.qhx.back.model.to;

import lombok.Data;

@Data
public class ProductBaseInfoRequest {

        // 溯源码
        private String traceCode;

        // 商品名称
        private String productName;

        // 生产商
        private String producer;

        // 生产时间
        private Long productionTime;

        // 生产地址
        private String productionAddress;

        // 新增：商品形式
        private String productForm;

        // 新增：批次数量
        private Integer batchQuantity;
}