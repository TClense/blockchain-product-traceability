<template>
  <div class="supplier">
    <!-- 添加供应商信息按钮 -->
    <div class="header">
      <Authorization :roles="['2']">
        <el-button type="primary" @click="showAddSupplierDialog">添加供应商信息</el-button>
      </Authorization>
    </div>

    <!-- 添加供应商信息弹窗 -->
    <el-dialog title="添加供应商信息" :visible.sync="addSupplierDialogVisible" width="500px">
      <el-form :model="supplierForm" :rules="supplierRules" ref="supplierForm" label-width="100px">
        <el-form-item label="溯源码" prop="traceCode">
          <el-select v-model="supplierForm.traceCode" placeholder="请选择溯源码" filterable>
            <el-option
                v-for="item in traceCodeOptions"
                :key="item"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="入库时间" prop="storageTime">
          <el-date-picker
              v-model="supplierForm.storageTime"
              type="datetime"
              placeholder="请选择入库时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="timestamp">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="质检情况" prop="qualityCheck">
          <el-select v-model="supplierForm.qualityCheck" placeholder="请选择质检情况" filterable>
            <el-option
                v-for="item in ['优质','合格', '不合格']"
                :key="item"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="发货单位" prop="shippingUnit">
          <el-input v-model="supplierForm.shippingUnit" placeholder="请输入发货单位"></el-input>
        </el-form-item>

        <el-form-item label="收货单位" prop="receivingUnit">
          <el-input v-model="supplierForm.receivingUnit" placeholder="请输入收货单位"></el-input>
        </el-form-item>

        <el-form-item label="收货地址" prop="receivingAddress">
          <el-input v-model="supplierForm.receivingAddress" placeholder="请输入收货地址"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddSupplier">取 消</el-button>
        <el-button type="primary" @click="submitSupplierForm">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 供应商信息列表 -->
    <div class="supplier-list">
      <el-table :data="supplierList" style="width: 100%" v-loading="loading">

        <el-table-column prop="traceCode" label="溯源码" width="180">
          <template slot-scope="scope">
            <div class="trace-number-cell">
              <i
                  class="el-icon-document-copy"
                  v-if="scope.row.traceCode"
                  style="cursor: pointer;margin-right: 4px;"
                  @click="copyData(scope.row.traceCode)">
              </i>

              <span class="copy-text">{{ scope.row.traceCode }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="storageTime" label="入库时间" width="200">
          <template slot-scope="scope">
            {{ scope.row.storageTime | formatDate }}
          </template>
        </el-table-column>

        <el-table-column prop="qualityCheck" label="质检情况" width="150"></el-table-column>

        <el-table-column prop="shippingUnit" label="发货单位" width="150"></el-table-column>

        <el-table-column prop="receivingUnit" label="收货单位" width="150"></el-table-column>

        <el-table-column prop="receivingAddress" label="收货地址"></el-table-column>

      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Supplier',

  data() {
    return {
      addSupplierDialogVisible: false,
      loading: false,

      supplierForm: {
        traceCode: '',
        storageTime: '',
        qualityCheck: '',
        shippingUnit: '',
        receivingUnit: '',
        receivingAddress: ''
      },

      supplierRules: {
        traceCode: [
          { required: true, message: '请选择溯源码', trigger: 'change' }
        ],

        storageTime: [
          { required: true, message: '请选择入库时间', trigger: 'change' }
        ],

        qualityCheck: [
          { required: true, message: '请输入质检情况', trigger: 'blur' }
        ],

        shippingUnit: [
          { required: true, message: '请输入发货单位', trigger: 'blur' }
        ],

        receivingUnit: [
          { required: true, message: '请输入收货单位', trigger: 'blur' }
        ],

        receivingAddress: [
          { required: true, message: '请输入收货地址', trigger: 'blur' }
        ]
      },

      supplierList: [],
      traceCodeOptions: []
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
    this.fetchSupplierList();
    this.fetchTraceCodes();
  },

  methods: {

    showAddSupplierDialog() {
      this.addSupplierDialogVisible = true;
    },

    cancelAddSupplier() {
      this.addSupplierDialogVisible = false;
      this.resetSupplierForm();
    },

    resetSupplierForm() {
      this.$refs.supplierForm.resetFields();
    },

    // 获取溯源码列表
    async fetchTraceCodes() {

      try {

        const response = await this.$http.get('/trace/producer/list');

        if (response.code === 200) {

          this.traceCodeOptions =
              (response.data || []).map(item => item.traceCode);
        }

      } catch (error) {

        this.$message.error('获取溯源码列表失败');
      }
    },

    // 提交供应商信息
    submitSupplierForm() {

      this.$refs.supplierForm.validate(async (valid) => {

        if (valid) {

          try {

            const formData = {

              traceCode: this.supplierForm.traceCode,

              storageTime: this.supplierForm.storageTime,

              qualityCheck: this.supplierForm.qualityCheck,

              shippingUnit: this.supplierForm.shippingUnit,

              receivingUnit: this.supplierForm.receivingUnit,

              receivingAddress: this.supplierForm.receivingAddress
            };

            const response =
                await this.$http.post('/trace/supplier/info', formData);

            if (response.code === 200) {

              this.$message.success('供应商信息添加成功');

              this.addSupplierDialogVisible = false;

              this.resetSupplierForm();

              this.fetchSupplierList();

            } else {

              this.$message.error(response.mes || '添加失败');
            }

          } catch (error) {

            this.$message.error('请求失败');
          }
        }
      });
    },

    // 获取供应商列表
    async fetchSupplierList() {

      this.loading = true;

      try {

        const response =
            await this.$http.get('/trace/supplier/list');

        if (response.code === 200) {

          this.supplierList = response.data || [];

        } else {

          this.$message.error(response.mes || '获取供应商列表失败');
        }

      } catch (error) {

        this.$message.error('请求失败');

      } finally {

        this.loading = false;
      }
    },
  }
};
</script>

<style scoped>

.supplier {
  padding: 20px;
}

.header {
  margin-bottom: 20px;
  text-align: right;
}

.supplier-list {
  margin-top: 20px;
}

</style>