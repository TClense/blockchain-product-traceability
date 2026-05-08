// SPDX-License-Identifier: MIT
pragma experimental ABIEncoderV2;
pragma solidity ^0.4.25;

import "./User.sol";
import "./ProductInfo.sol";

contract ProductTrace is User {

    // 溯源码 -> ProductInfo
    mapping(string => ProductInfo) productInfos;
    string[] public traceCodes;

    constructor(
        address _producer,
        address _supplier,
        address _retailer
    ) public {

        // 初始化用户角色
        setUser(1, _producer);
        setUser(2, _supplier);
        setUser(3, _retailer);
    }

    // 检查溯源码是否已存在
    modifier onlyValidTraceCode(string _traceCode) {

        require(productInfos[_traceCode] == address(0), "该溯源码已存在");

        _;
    }

    // 检查溯源码是否存在
    modifier onlyExistingTraceCode(string _traceCode) {

        require(productInfos[_traceCode] != address(0), "该溯源码不存在");

        _;
    }

    // 生产商添加商品基本信息
    function addProductBaseInfo(
        string _traceCode,
        string _productName,
        string _producer,
        uint256 _productionTime,
        string _productionAddress,

        string _productForm,
        uint256 _batchQuantity

    ) public onlyProducer onlyValidTraceCode(_traceCode) {

        // 创建新的ProductInfo实例
        ProductInfo productInfo = new ProductInfo();

        // 设置商品基本信息
        productInfo.setProductBase(
            msg.sender,
            _productName,
            _producer,
            _productionTime,
            _productionAddress,

            _productForm,
            _batchQuantity
        );

        // 存储产品信息和溯源码
        productInfos[_traceCode] = productInfo;

        traceCodes.push(_traceCode);
    }

    // 获取商品基本信息
    function getProductBaseInfo(string _traceCode)
    public
    view
    onlyExistingTraceCode(_traceCode)

    returns (
        address,
        string,
        string,
        uint256,
        string,

        string,
        uint256,

        uint256
    )
    {

        return productInfos[_traceCode].getProductBase();
    }

    // 供应商添加基本信息
    function addSupplierInfo(
        string _traceCode,
        uint256 _storageTime,
        string _qualityCheck,
        string _shippingUnit,
        string _receivingUnit,
        string _receivingAddress
    ) public onlySupplier onlyExistingTraceCode(_traceCode) {

        productInfos[_traceCode].setSupplierInfo(
            msg.sender,
            _storageTime,
            _qualityCheck,
            _shippingUnit,
            _receivingUnit,
            _receivingAddress
        );
    }

    // 供应商获取基本信息
    function getSupplierInfo(string _traceCode)
    public
    view
    onlyExistingTraceCode(_traceCode)

    returns (
        address,
        uint256,
        string,
        string,
        string,
        string,
        uint256
    )
    {

        return productInfos[_traceCode].getSupplierInfo();
    }

    // 零售商添加基本信息
    function addRetailerInfo(
        string _traceCode,
        uint256 _storageTime,
        string _qualityCheck,
        string _shippingUnit,
        string _receivingUnit,
        string _receivingAddress
    ) public onlyRetailer onlyExistingTraceCode(_traceCode) {

        productInfos[_traceCode].setRetailerInfo(
            msg.sender,
            _storageTime,
            _qualityCheck,
            _shippingUnit,
            _receivingUnit,
            _receivingAddress
        );
    }

    // 零售商获取基本信息
    function getRetailerInfo(string _traceCode)
    public
    view
    onlyExistingTraceCode(_traceCode)

    returns (
        address,
        uint256,
        string,
        string,
        string,
        string,
        uint256
    )
    {

        return productInfos[_traceCode].getRetailerInfo();
    }

    // 获取所有溯源码
    function getAllTraceCodes() public view returns (string[]) {

        return traceCodes;
    }

    // 获取溯源码数量
    function getTraceCodesCount() public view returns (uint256) {

        return traceCodes.length;
    }
}