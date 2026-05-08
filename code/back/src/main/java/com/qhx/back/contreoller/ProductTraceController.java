package com.qhx.back.contreoller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.qhx.back.context.AddressContext;
import com.qhx.back.exception.WeBaseFrontException;
import com.qhx.back.model.Result;
import com.qhx.back.model.to.ProductBaseInfoRequest;
import com.qhx.back.model.to.RetailerInfoRequest;
import com.qhx.back.model.to.SupplierInfoRequest;
import com.qhx.back.util.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/trace")
@Api(tags = "商品溯源接口")
public class ProductTraceController {

    @Autowired
    private HttpUtil httpUtil;

    /**
     * 生产商添加商品基本信息
     */
    @PostMapping("/product/baseinfo")
    @ApiOperation(value = "生产商添加商品基本信息")
    public Result addProductBaseInfo(@RequestBody ProductBaseInfoRequest request) {

        String address = AddressContext.getAddress();

        httpUtil.sendTransaction(
                address,
                "addProductBaseInfo",
                Arrays.asList(
                        request.getTraceCode(),
                        request.getProductName(),
                        request.getProducer(),
                        String.valueOf(request.getProductionTime()),
                        request.getProductionAddress(),

                        // 第十周新增字段
                        request.getProductForm(),
                        String.valueOf(request.getBatchQuantity())
                )
        );

        return Result.success();
    }

    /**
     * 生产者获取生产信息列表
     */
    @GetMapping("/producer/list")
    @ApiOperation(value = "生产者获取生产信息列表")
    public Result getProducerList() {

        JSONArray productList = getAllTraceCodes();

        JSONArray resList = new JSONArray();

        for (int i = 0; i < productList.size(); i++) {

            String traceNumber = productList.getStr(i);

            JSONObject jsonObj = getProductBaseInfo(traceNumber);

            resList.add(jsonObj);
        }

        return Result.success(resList);
    }

    /**
     * 供应商添加基本信息
     */
    @PostMapping("/supplier/info")
    @ApiOperation(value = "供应商添加基本信息")
    public Result addSupplierInfo(@RequestBody SupplierInfoRequest request) {

        String address = AddressContext.getAddress();

        httpUtil.sendTransaction(
                address,
                "addSupplierInfo",
                Arrays.asList(
                        request.getTraceCode(),
                        String.valueOf(request.getStorageTime()),
                        request.getQualityCheck(),
                        request.getShippingUnit(),
                        request.getReceivingUnit(),
                        request.getReceivingAddress()
                )
        );

        return Result.success();
    }

    /**
     * 供应商获取基本信息列表
     */
    @GetMapping("/supplier/list")
    @ApiOperation(value = "供应商获取基本信息列表")
    public Result getSupplierList() {

        JSONArray productList = getAllTraceCodes();

        JSONArray resList = new JSONArray();

        for (int i = 0; i < productList.size(); i++) {

            String traceNumber = productList.getStr(i);

            JSONObject jsonObj = getSupplierInfo(traceNumber);

            if (jsonObj == null) {
                continue;
            }

            resList.add(jsonObj);
        }

        return Result.success(resList);
    }

    /**
     * 零售商添加基本信息
     */
    @PostMapping("/retailer/info")
    @ApiOperation(value = "零售商添加基本信息")
    public Result addRetailerInfo(@RequestBody RetailerInfoRequest request) {

        String address = AddressContext.getAddress();

        httpUtil.sendTransaction(
                address,
                "addRetailerInfo",
                Arrays.asList(
                        request.getTraceCode(),
                        String.valueOf(request.getStorageTime()),
                        request.getQualityCheck(),
                        request.getShippingUnit(),
                        request.getReceivingUnit(),
                        request.getReceivingAddress()
                )
        );

        return Result.success();
    }

    /**
     * 零售商获取基本信息列表
     */
    @GetMapping("/retailer/list")
    @ApiOperation(value = "零售商获取基本信息列表")
    public Result getRetailerList() {

        JSONArray productList = getAllTraceCodes();

        JSONArray resList = new JSONArray();

        for (int i = 0; i < productList.size(); i++) {

            String traceNumber = productList.getStr(i);

            JSONObject jsonObj = getRetailerInfo(traceNumber);

            if (jsonObj == null) {
                continue;
            }

            resList.add(jsonObj);
        }

        return Result.success(resList);
    }

    /**
     * 获取完整溯源信息
     */
    @GetMapping("/detail/{traceCode}")
    @ApiOperation(value = "获取完整的溯源信息")
    public Result getDetailTraceInfo(@PathVariable String traceCode) {

        JSONObject fullTraceInfo = getFullTraceInfo(traceCode);

        return Result.success(fullTraceInfo);
    }

    /**
     * 获取完整溯源信息
     */
    private JSONObject getFullTraceInfo(String traceCode) {

        JSONObject result = new JSONObject();

        result.set("traceCode", traceCode);

        JSONObject productBaseInfo = getProductBaseInfo(traceCode);

        if (productBaseInfo == null) {

            throw new WeBaseFrontException("未找到该溯源信息");
        }

        result.set("productBaseInfo", productBaseInfo);

        JSONObject supplierInfo = getSupplierInfo(traceCode);

        if (supplierInfo == null) {

            result.set("supplierInfo", new JSONObject());

        } else {

            result.set("supplierInfo", supplierInfo);
        }

        JSONObject retailerInfo = getRetailerInfo(traceCode);

        if (retailerInfo == null) {

            result.set("retailerInfo", new JSONObject());

        } else {

            result.set("retailerInfo", retailerInfo);
        }

        return result;
    }

    /**
     * 获取零售商信息
     */
    private JSONObject getRetailerInfo(String traceCode) {

        JSONArray result = httpUtil.call(
                "getRetailerInfo",
                Arrays.asList(traceCode)
        );

        if (StrUtil.isBlank(result.getStr(3))) {

            return null;
        }

        JSONObject jsonObj = new JSONObject();

        jsonObj.set("retailerAddr", result.getStr(0));
        jsonObj.set("storageTime", result.getLong(1));
        jsonObj.set("qualityCheck", result.getStr(2));
        jsonObj.set("shippingUnit", result.getStr(3));
        jsonObj.set("receivingUnit", result.getStr(4));
        jsonObj.set("receivingAddress", result.getStr(5));
        jsonObj.set("blockNumber", result.getLong(6));
        jsonObj.set("traceCode", traceCode);

        return jsonObj;
    }

    /**
     * 获取供应商信息
     */
    private JSONObject getSupplierInfo(String traceCode) {

        JSONArray result = httpUtil.call(
                "getSupplierInfo",
                Arrays.asList(traceCode)
        );

        if (StrUtil.isBlank(result.getStr(3))) {

            return null;
        }

        JSONObject jsonObj = new JSONObject();

        jsonObj.set("supplierAddr", result.getStr(0));
        jsonObj.set("storageTime", result.getLong(1));
        jsonObj.set("qualityCheck", result.getStr(2));
        jsonObj.set("shippingUnit", result.getStr(3));
        jsonObj.set("receivingUnit", result.getStr(4));
        jsonObj.set("receivingAddress", result.getStr(5));
        jsonObj.set("blockNumber", result.getLong(6));
        jsonObj.set("traceCode", traceCode);

        return jsonObj;
    }

    /**
     * 获取商品基本信息
     */
    private JSONObject getProductBaseInfo(String traceCode) {

        JSONArray result = httpUtil.call(
                "getProductBaseInfo",
                Arrays.asList(traceCode)
        );

        if (result.size() == 1) {

            return null;
        }

        JSONObject jsonObj = new JSONObject();

        jsonObj.set("producerAddr", result.getStr(0));
        jsonObj.set("productName", result.getStr(1));
        jsonObj.set("producer", result.getStr(2));
        jsonObj.set("productionTime", result.getLong(3));
        jsonObj.set("productionAddress", result.getStr(4));

        // 第十周新增字段
        jsonObj.set("productForm", result.getStr(5));
        jsonObj.set("batchQuantity", result.getInt(6));

        jsonObj.set("blockNumber", result.getLong(7));

        jsonObj.set("traceCode", traceCode);

        return jsonObj;
    }

    /**
     * 获取所有溯源码
     */
    private JSONArray getAllTraceCodes() {

        JSONArray result = httpUtil.call("getAllTraceCodes");

        return result.getJSONArray(0);
    }
}