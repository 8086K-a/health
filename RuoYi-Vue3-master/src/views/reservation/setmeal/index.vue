<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="助记码" prop="helpCode">
        <el-input
          v-model="queryParams.helpCode"
          placeholder="请输入助记码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年龄范围" prop="age">
        <el-input
          v-model="queryParams.age"
          placeholder="请输入年龄范围"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注意事项" prop="attention">
        <el-input
          v-model="queryParams.attention"
          placeholder="请输入注意事项"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图片路径" prop="img">
        <el-input
          v-model="queryParams.img"
          placeholder="请输入图片路径"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['reservation:setmeal:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['reservation:setmeal:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['reservation:setmeal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['reservation:setmeal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="setmealList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="套餐ID" align="center" prop="id" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="编码" align="center" prop="code" />
      <el-table-column label="助记码" align="center" prop="helpCode" />
      <el-table-column label="性别" align="center" prop="sex" />
      <el-table-column label="年龄范围" align="center" prop="age" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="注意事项" align="center" prop="attention" />
      <el-table-column label="图片路径" align="center" prop="img" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['reservation:setmeal:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['reservation:setmeal:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改套餐对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="setmealRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="助记码" prop="helpCode">
              <el-input v-model="form.helpCode" placeholder="请输入助记码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="年龄范围" prop="age">
              <el-input v-model="form.age" placeholder="请输入年龄范围" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入价格" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="注意事项" prop="attention">
              <el-input v-model="form.attention" placeholder="请输入注意事项" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="图片路径" prop="img">
              <el-input v-model="form.img" placeholder="请输入图片路径" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Setmeal">
import { listSetmeal, getSetmeal, delSetmeal, addSetmeal, updateSetmeal } from "@/api/reservation/setmeal"

const { proxy } = getCurrentInstance()

const setmealList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    code: undefined,
    helpCode: undefined,
    sex: undefined,
    age: undefined,
    price: undefined,
    attention: undefined,
    img: undefined,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询套餐列表 */
function getList() {
  loading.value = true
  listSetmeal(queryParams.value).then(response => {
    setmealList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    id: null,
    name: null,
    code: null,
    helpCode: null,
    sex: null,
    age: null,
    price: null,
    remark: null,
    attention: null,
    img: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("setmealRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加套餐"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getSetmeal(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改套餐"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["setmealRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSetmeal(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addSetmeal(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除套餐编号为"' + _ids + '"的数据项？').then(function() {
    return delSetmeal(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('reservation/setmeal/export', {
    ...queryParams.value
  }, `setmeal_${new Date().getTime()}.xlsx`)
}

getList()
</script>
