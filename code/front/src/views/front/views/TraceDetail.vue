<template>
  <div class="page-container">

    <div class="detail-card">

      <h2 class="card-title">
        商品溯源详情
      </h2>

      <p class="card-subtitle">
        追溯商品的生产、供应与零售全过程
      </p>

      <el-main
          v-loading="loading"
          element-loading-background="rgba(0, 0, 0, 0.5)"
          element-loading-text="数据加载中"
          element-loading-spinner="el-icon-loading">

        <!-- 商品基本信息 -->
        <el-card class="info-section" shadow="hover">

          <div slot="header" class="section-header">
            <span>商品基本信息</span>
          </div>

          <el-row :gutter="20">

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">溯源码：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.traceCode || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">商品名称：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.productName || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">生产商：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.producer || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">生产时间：</span>
                <span class="info-value">
                  {{ formatTime(detail.productBaseInfo?.productionTime) || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">生产地址：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.productionAddress || '-' }}
                </span>
              </div>
            </el-col>

            <!-- 第十周新增 -->
            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">商品形式：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.productForm || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">批次数量：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.batchQuantity || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">生产商地址：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.producerAddr || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">区块号：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.blockNumber || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">区块时间：</span>
                <span class="info-value">
                  {{ formatTime(detail.productBaseInfo?.blockTimestamp) || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">交易哈希：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.transaction.hash || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">区块哈希：</span>
                <span class="info-value">
                  {{ detail.productBaseInfo?.transaction.blockHash || '-' }}
                </span>
              </div>
            </el-col>

          </el-row>
        </el-card>

        <!-- 供应商信息 -->
        <el-card
            class="info-section"
            shadow="hover"
            v-if="Object.keys(detail.supplierInfo).length !== 0">

          <div slot="header" class="section-header">
            <span>供应商信息</span>
          </div>

          <el-row :gutter="20">

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">供应商地址：</span>
                <span class="info-value">
                  {{ detail.supplierInfo?.supplierAddr || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">入库时间：</span>
                <span class="info-value">
                  {{ formatTime(detail.supplierInfo?.storageTime) || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">质检情况：</span>
                <span class="info-value">
                  {{ detail.supplierInfo?.qualityCheck || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">发货单位：</span>
                <span class="info-value">
                  {{ detail.supplierInfo?.shippingUnit || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">收货单位：</span>
                <span class="info-value">
                  {{ detail.supplierInfo?.receivingUnit || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">收货地址：</span>
                <span class="info-value">
                  {{ detail.supplierInfo?.receivingAddress || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">区块号：</span>
                <span class="info-value">
                  {{ detail.supplierInfo?.blockNumber || '-' }}
                </span>
              </div>
            </el-col>

          </el-row>
        </el-card>

        <!-- 零售商信息 -->
        <el-card
            class="info-section"
            shadow="hover"
            v-if="Object.keys(detail.retailerInfo).length !== 0">

          <div slot="header" class="section-header">
            <span>零售商信息</span>
          </div>

          <el-row :gutter="20">

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">零售商地址：</span>
                <span class="info-value">
                  {{ detail.retailerInfo?.retailerAddr || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">入库时间：</span>
                <span class="info-value">
                  {{ formatTime(detail.retailerInfo?.storageTime) || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">质检情况：</span>
                <span class="info-value">
                  {{ detail.retailerInfo?.qualityCheck || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">发货单位：</span>
                <span class="info-value">
                  {{ detail.retailerInfo?.shippingUnit || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">收货单位：</span>
                <span class="info-value">
                  {{ detail.retailerInfo?.receivingUnit || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">收货地址：</span>
                <span class="info-value">
                  {{ detail.retailerInfo?.receivingAddress || '-' }}
                </span>
              </div>
            </el-col>

            <el-col :span="12">
              <div class="info-item">
                <span class="info-label">区块号：</span>
                <span class="info-value">
                  {{ detail.retailerInfo?.blockNumber || '-' }}
                </span>
              </div>
            </el-col>

          </el-row>
        </el-card>

      </el-main>
    </div>
  </div>
</template>

<script>

import { dateTimeUtils } from "@/utils/commonUtil";

export default {

  name: 'trace-detail',

  data() {

    return {

      dateTimeUtils,

      detail: {},

      loading: true
    }
  },

  async created() {

    try {

      this.loading = true;

      const { data } =
          await this.$http.get(
              '/trace/detail/' + this.$route.params.traceCode
          );

      const { data:block1 } =
          await this.$http.get(
              '/block/' + data.productBaseInfo.blockNumber
          );

      data.productBaseInfo.transaction =
          block1.transactions[0];

      data.productBaseInfo.blockTimestamp =
          block1.timestamp;

      if (Object.keys(data.supplierInfo).length !== 0) {

        const { data:block2 } =
            await this.$http.get(
                '/block/' + data.supplierInfo.blockNumber
            );

        data.supplierInfo.transaction =
            block2.transactions[0];

        data.supplierInfo.blockTimestamp =
            block2.timestamp;
      }

      if (Object.keys(data.retailerInfo).length !== 0) {

        const { data:block3 } =
            await this.$http.get(
                '/block/' + data.retailerInfo.blockNumber
            );

        data.retailerInfo.transaction =
            block3.transactions[0];

        data.retailerInfo.blockTimestamp =
            block3.timestamp;
      }

      this.detail = data || {};

    } catch (error) {

      this.$message.error('获取溯源信息失败');

      console.error(error);

    } finally {

      this.loading = false;
    }
  },

  methods: {

    formatTime(timestamp) {

      if (!timestamp) return '-';

      return dateTimeUtils.formatTimestamp(timestamp);
    }
  }
};
</script>

<style lang="scss" scoped>

.page-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 80px);
}

.detail-card {
  max-width: 1200px;
  margin: 0 auto;
}

.card-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  text-align: center;
  margin-bottom: 10px;
}

.card-subtitle {
  font-size: 14px;
  color: #909399;
  text-align: center;
  margin-bottom: 30px;
}

.info-section {
  margin-bottom: 20px;
}

.section-header {
  font-size: 18px;
  font-weight: 500;
}

.info-item {
  margin-bottom: 15px;
  display: flex;
  word-break: break-all;
}

.info-label {
  font-weight: 500;
  color: #606266;
  min-width: 100px;
}

.info-value {
  color: #303133;
  flex: 1;
}

</style>