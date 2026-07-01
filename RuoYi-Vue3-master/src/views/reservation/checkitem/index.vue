<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
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
          v-hasPermi="['reservation:checkitem:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['reservation:checkitem:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['reservation:checkitem:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['reservation:checkitem:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkitemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="检查项ID" type="index" align="center" prop="id" />
      <el-table-column label="编码" align="center" prop="code" />
          <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="sex" />
      <el-table-column label="年龄范围" align="center" prop="age" />
  
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="注意事项" align="center" prop="attention" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['reservation:checkitem:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['reservation:checkitem:remove']">删除</el-button>
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

    <!-- 添加或修改检查项对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="checkitemRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入编码" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称" />
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
            <el-form-item label="注意事项" prop="attention">
              <el-input v-model="form.attention" placeholder="请输入注意事项" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" />
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

<script setup name="Checkitem">
import { listCheckitem, getCheckitem, delCheckitem, addCheckitem, updateCheckitem } from "@/api/reservation/checkitem"

const { proxy } = getCurrentInstance()

const checkitemList = ref([])
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
    code: undefined,
    name: undefined,
    sex: undefined,
    age: undefined,
    price: undefined,
    type: undefined,
    attention: undefined,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询检查项列表 */
function getList() {
  loading.value = true
  listCheckitem(queryParams.value).then(response => {
    checkitemList.value = response.rows
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
    code: null,
    name: null,
    sex: null,
    age: null,
    price: null,
    type: null,
    attention: null,
    remark: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("checkitemRef")
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
  title.value = "添加检查项"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getCheckitem(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改检查项"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["checkitemRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCheckitem(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCheckitem(form.value).then(() => {
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
  proxy.$modal.confirm('是否确认删除检查项编号为"' + _ids + '"的数据项？').then(function() {
    return delCheckitem(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('reservation/checkitem/export', {
    ...queryParams.value
  }, `checkitem_${new Date().getTime()}.xlsx`)
}

getList()
</script>
