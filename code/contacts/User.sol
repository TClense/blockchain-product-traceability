// SPDX-License-Identifier: MIT
pragma solidity ^0.4.25;
import "./Ownable.sol";
contract User is Ownable  {

    // TODO
    // 定义用户类型

     // 函数修改器
    modifier onlyProducer() {
        // TODO
        _;
    }

    modifier onlySupplier() {
        // TODO
        _;
    }

    modifier onlyRetailer() {
       // TODO
        _;
    }

    // TODO
    // 对应的get和set方法

}