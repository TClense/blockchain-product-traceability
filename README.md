# 区块链商品可追溯性系统

基于 FISCO BCOS + WeBASE 的区块链商品溯源平台，实现商品从生产、供应到零售的全流程可信追踪。

---

# 项目简介

本项目是一个基于联盟链的商品溯源系统，采用：

* Solidity 智能合约
* FISCO BCOS 联盟链
* WeBASE 区块链管理平台
* SpringBoot 后端
* Vue 前端

实现商品全生命周期的可信记录与溯源查询。

系统能够记录：

* 商品生产信息
* 商品供应链流转信息
* 零售商信息
* 区块链交易哈希
* 区块高度
* 上链时间

从而保证商品信息不可篡改。

---

# 技术栈

## 区块链

* FISCO BCOS
* Solidity
* WeBASE

## 后端

* SpringBoot
* Maven
* Java

## 前端

* Vue2
* Element UI
* Axios

---

# 系统功能

## 生产商模块

* 新建商品
* 录入商品基本信息
* 录入商品形式
* 录入批次数量

## 供应商模块

* 添加供应链流转信息
* 添加运输信息
* 添加质检信息

## 零售商模块

* 添加零售流转信息
* 商品最终入库记录

## 溯源查询模块

* 查询完整商品溯源链
* 查询区块哈希
* 查询交易哈希
* 查询区块时间

---

# 项目结构

```text
code
├── back        # SpringBoot 后端
├── front       # Vue 前端
├── contacts    # Solidity 智能合约
```

---

# 运行环境

## 后端

* JDK 8
* Maven
* SpringBoot

## 前端

* Node.js
* npm
* Vue CLI

## 区块链环境

* Ubuntu 20.04
* FISCO BCOS
* WeBASE

---

# 项目截图

后续补充。

---

# 项目特点

* 区块链数据不可篡改
* 商品全链路追踪
* 前后端分离架构
* 联盟链部署
* 智能合约交互

---

# 作者

TClense

---

# License

MIT License
