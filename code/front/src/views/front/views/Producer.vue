<template>
  <div class="producer">

    <!-- 新建商品按钮 -->
    <div class="header">

      <Authorization :roles="['1']">

        <el-button
            type="primary"
            @click="showAddProductDialog">

          新建商品

        </el-button>

      </Authorization>

    </div>

    <!-- 录入商品基本信息弹窗 -->
    <el-dialog
        title="录入商品基本信息"
        :visible.sync="addProductDialogVisible"
        width="500px">

      <el-form
          :model="productForm"
          :rules="productRules"
          ref="productForm"
          label-width="100px">

        <el-form-item
            label="溯源码"
            prop="traceCode">

          <el-input
              v-model="productForm.traceCode"
              placeholder="请输入溯源码">
          </el-input>

        </el-form-item>

        <el-form-item
            label="商品名称"
            prop="productName">

          <el-input
              v-model="productForm.productName"
              placeholder="请输入商品名称">
          </el-input>

        </el-form-item>

        <el-form-item
            label="生产商"
            prop="producer">

          <el-input
              v-model="productForm.producer"
              placeholder="请输入生产商">
          </el-input>

        </el-form-item>

        <el-form-item
            label="生产时间"
            prop="productionTime">

          <el-date-picker
              v-model="productForm.productionTime"
              type="datetime"
              placeholder="请选择生产时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="timestamp">
          </el-date-picker>

        </el-form-item>

        <el-form-item
            label="生产地址"
            prop="productionAddress">

          <el-input
              v-model="productForm.productionAddress"
              placeholder="请输入生产地址">
          </el-input>

        </el-form-item>

        <!-- 第十周新增 -->
        <el-form-item
            label="商品形式"
            prop="productForm">

          <el-input
              v-model="productForm.productForm"
              placeholder="请输入商品形式">
          </el-input>

        </el-form-item>

        <!-- 第十周新增 -->
        <el-form-item
            label="批次数量"
            prop="batchQuantity">

          <el-input-number
              v-model="productForm.batchQuantity"
              :min="1"
              style="width: 100%">
          </el-input-number>

        </el-form-item>

      </el-form>

      <span slot="footer" class="dialog-footer">

        <el-button @click="cancelAddProduct">
          取 消
        </el-button>

        <el-button
            type="primary"
            @click="submitProductForm">

          确 定

        </el-button>

      </span>

    </el-dialog>

    <!-- 商品列表 -->
    <div class="product-list">

      <el-table
          :data="productList"
          style="width: 100%"
          v-loading="loading">

        <el-table-column
            prop="traceCode"
            label="溯源码"
            width="180">

          <template slot-scope="scope">

            <div class="trace-number-cell">

              <i
                  class="el-icon-document-copy"
                  v-if="scope.row.traceCode"
                  style="cursor: pointer;margin-right: 4px;"
                  @click="copyData(scope.row.traceCode)">
              </i>

              <span class="copy-text">
                {{ scope.row.traceCode }}
              </span>

            </div>

          </template>

        </el-table-column>

        <el-table-column
            prop="productName"
            label="商品名称"
            width="180">
        </el-table-column>

        <el-table-column
            prop="producer"
            label="生产商"
            width="180">
        </el-table-column>

        <el-table-column
            prop="productionTime"
            label="生产时间"
            width="200">

          <template slot-scope="scope">

            {{ scope.row.productionTime | formatDate }}

          </template>

        </el-table-column>

        <el-table-column
            prop="productionAddress"
            label="生产地址"
            width="180">
        </el-table-column>

        <!-- 第十周新增 -->
        <el-table-column
            prop="productForm"
            label="商品形式"
            width="180">
        </el-table-column>

        <!-- 第十周新增 -->
        <el-table-column
            prop="batchQuantity"
            label="批次数量"
            width="120">
        </el-table-column>

      </el-table>

    </div>

  </div>
</template>

<script>

export default {

  name: 'Producer',

  data() {

    return {

      addProductDialogVisible: false,

      loading: false,

      productForm: {

        traceCode: '',

        productName: '',

        producer: '',

        productionTime: '',

        productionAddress: '',

        // 第十周新增
        productForm: '',

        batchQuantity: 1
      },

      productRules: {

        traceCode: [
          {
            required: true,
            message: '请输入溯源码',
            trigger: 'blur'
          }
        ],

        productName: [
          {
            required: true,
            message: '请输入商品名称',
            trigger: 'blur'
          }
        ],

        producer: [
          {
            required: true,
            message: '请输入生产商',
            trigger: 'blur'
          }
        ],

        productionTime: [
          {
            required: true,
            message: '请选择生产时间',
            trigger: 'change'
          }
        ],

        productionAddress: [
          {
            required: true,
            message: '请输入生产地址',
            trigger: 'blur'
          }
        ],

        // 第十周新增
        productForm: [
          {
            required: true,
            message: '请输入商品形式',
            trigger: 'blur'
          }
        ],

        // 第十周新增
        batchQuantity: [
          {
            required: true,
            message: '请输入批次数量',
            trigger: 'change'
          }
        ]
      },

      productList: []
    };
  },

  filters: {

    formatDate(timestamp) {

      if (!timestamp) return '';

      const date = new Date(timestamp);

      return date.toLocaleString();
    }
  },

  mounted() {

    this.fetchProductList();
  },

  methods: {

    showAddProductDialog() {

      this.addProductDialogVisible = true;
    },

    cancelAddProduct() {

      this.addProductDialogVisible = false;

      this.resetProductForm();
    },

    resetProductForm() {

      this.$refs.productForm.resetFields();
    },

    submitProductForm() {

      this.$refs.productForm.validate(async (valid) => {

        if (valid) {

          try {

            const formData = {

              traceCode: this.productForm.traceCode,

              productName: this.productForm.productName,

              producer: this.productForm.producer,

              productionTime: this.productForm.productionTime,

              productionAddress: this.productForm.productionAddress,

              // 第十周新增
              productForm: this.productForm.productForm,

              batchQuantity: this.productForm.batchQuantity
            };

            const response = await this.$http.post(
                '/trace/product/baseinfo',
                formData
            );

            if (response.code === 200) {

              this.$message.success('商品信息录入成功');

              this.addProductDialogVisible = false;

              this.resetProductForm();

              this.fetchProductList();

            } else {

              this.$message.error(response.mes || '录入失败');
            }

          } catch (error) {

            this.$message.error('请求失败');
          }
        }
      });
    },

    async fetchProductList() {

      this.loading = true;

      try {

        const response =
            await this.$http.get('/trace/producer/list');

        if (response.code === 200) {

          this.productList = response.data || [];

        } else {

          this.$message.error(
              response.mes || '获取商品列表失败'
          );
        }

      } catch (error) {

        this.$message.error('请求失败');

      } finally {

        this.loading = false;
      }
    }
  }
};

</script>

<style scoped>

.producer {
  padding: 20px;
}

.header {
  margin-bottom: 20px;
  text-align: right;
}

.product-list {
  margin-top: 20px;
}

</style>