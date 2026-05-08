// SPDX-License-Identifier: MIT
pragma solidity ^0.4.25;

contract ProductInfo {

    uint8 private _status = 0;

    // 商品基本信息
    struct ProductBase {
        address producerAddr;
        string productName;
        string producer;
        uint256 productionTime;
        string productionAddress;

        // 新增字段
        string productForm;
        uint256 batchQuantity;

        uint256 blockNumber;
    }

    // 供应商信息
    struct SupplierInfo {
        address supplierAddr;
        uint256 storageTime;
        string qualityCheck;
        string shippingUnit;
        string receivingUnit;
        string receivingAddress;
        uint256 blockNumber;
    }

    // 零售商信息
    struct RetailerInfo {
        address retailerAddr;
        uint256 storageTime;
        string qualityCheck;
        string shippingUnit;
        string receivingUnit;
        string receivingAddress;
        uint256 blockNumber;
    }

    ProductBase private _productBase;
    SupplierInfo private _supplierInfo;
    RetailerInfo private _retailerInfo;

    // 获取商品基本信息
    function getProductBase() public view returns (
        address,
        string,
        string,
        uint256,
        string,

        string,
        uint256,

        uint256
    ) {

        return (
            _productBase.producerAddr,
            _productBase.productName,
            _productBase.producer,
            _productBase.productionTime,
            _productBase.productionAddress,

            _productBase.productForm,
            _productBase.batchQuantity,

            _productBase.blockNumber
        );
    }

    // 获取供应商信息
    function getSupplierInfo() public view returns (
        address,
        uint256,
        string,
        string,
        string,
        string,
        uint256
    ) {

        return (
            _supplierInfo.supplierAddr,
            _supplierInfo.storageTime,
            _supplierInfo.qualityCheck,
            _supplierInfo.shippingUnit,
            _supplierInfo.receivingUnit,
            _supplierInfo.receivingAddress,
            _supplierInfo.blockNumber
        );
    }

    // 获取零售商信息
    function getRetailerInfo() public view returns (
        address,
        uint256,
        string,
        string,
        string,
        string,
        uint256
    ) {

        return (
            _retailerInfo.retailerAddr,
            _retailerInfo.storageTime,
            _retailerInfo.qualityCheck,
            _retailerInfo.shippingUnit,
            _retailerInfo.receivingUnit,
            _retailerInfo.receivingAddress,
            _retailerInfo.blockNumber
        );
    }

    // 设置商品基本信息
    function setProductBase(
        address _producerAddr,
        string _productName,
        string _producer,
        uint256 _productionTime,
        string _productionAddress,

        string _productForm,
        uint256 _batchQuantity

    ) public {

        require(_status == 0, "商品基本信息已设置");

        _productBase = ProductBase(
            _producerAddr,
            _productName,
            _producer,
            _productionTime,
            _productionAddress,

            _productForm,
            _batchQuantity,

            block.number
        );

        _status = 1;
    }

    // 设置供应商信息
    function setSupplierInfo(
        address _supplierAddr,
        uint256 _storageTime,
        string _qualityCheck,
        string _shippingUnit,
        string _receivingUnit,
        string _receivingAddress
    ) public {

        require(_status == 1, "商品基本信息未设置");

        _supplierInfo = SupplierInfo(
            _supplierAddr,
            _storageTime,
            _qualityCheck,
            _shippingUnit,
            _receivingUnit,
            _receivingAddress,
            block.number
        );

        _status = 2;
    }

    // 设置零售商信息
    function setRetailerInfo(
        address _retailerAddr,
        uint256 _storageTime,
        string _qualityCheck,
        string _shippingUnit,
        string _receivingUnit,
        string _receivingAddress
    ) public {

        require(_status == 2, "供应商信息未设置");

        _retailerInfo = RetailerInfo(
            _retailerAddr,
            _storageTime,
            _qualityCheck,
            _shippingUnit,
            _receivingUnit,
            _receivingAddress,
            block.number
        );

        _status = 3;
    }
}