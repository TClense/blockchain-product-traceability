package com.qhx.back.model.to;

import lombok.Data;

@Data
public class SupplierInfoRequest {
        private String traceCode;
        private Long storageTime;
        private String qualityCheck;
        private String shippingUnit;
        private String receivingUnit;
        private String receivingAddress;

    }
