<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item prop="searchValue">
        <el-input
          v-model="queryParams.searchValue"
          placeholder="项目编码/项目名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="handleQuery">搜索</el-button>
        <el-button
            type="primary"
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['reservation:checkitem:add']"
        >新增</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="checkitemList" @selection-change="handleSelectionChange">
      <el-table-column type="index" label="序号" align="center" width="50" />
      <el-table-column label="项目编码" align="center" prop="code" />
      <el-table-column label="项目名称" align="center" prop="name" />
      <el-table-column label="适用性别" align="center" prop="sex">
        <template #default="scope">
          <dict-tag :options="health_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
      <el-table-column label="适用年龄" align="center" prop="age" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="类型" align="center" prop="type" >
        <template #default="scope">
          <dict-tag :options="health_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="项目说明" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="180" align="center" class-name="small-padding fixed-width">
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

    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="checkitemRef" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="项目编码" prop="code">
              <el-input v-model="form.code" placeholder="请输入编码"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="项目名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入名称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别">
              <el-select v-model="form.sex" placeholder="请选择性别" clearable>
                <el-option
                    v-for="item in health_sex"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="适用年龄">
              <el-input v-model="form.age" placeholder="请输入年龄"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="类型">
              <el-radio-group v-model="form.type">
                <el-radio v-for="dict in health_type"
                          :key="dict.value"
                          :label="dict.value"
                >{{ dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="价格">
              <el-input v-model="form.price" placeholder="请输入价格"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="项目说明">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入备注"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="注意事项">
              <el-input v-model="form.attention" type="textarea" placeholder="请输入注意事项"/>
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
import { useDict } from '@/utils/dict'
import { listCheckitem, getCheckitem, delCheckitem, addCheckitem, updateCheckitem } from "@/api/reservation/checkitem"

const { proxy } = getCurrentInstance()
const { health_sex, health_type } = useDict('health_sex', 'health_type')

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
    code: null,
    name: null,
    type: null,
    searchValue: null,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

function getList() {
  loading.value = true
  listCheckitem(queryParams.value).then(response => {
    checkitemList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function cancel() {
  open.value = false
  reset()
}

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

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加检查项"
}

function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getCheckitem(_id).then(response => {
    form.value = response.data
    form.value.type = String(form.value.type)
    open.value = true
    title.value = "修改检查项"
  })
}

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

function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除检查项编号为"' + _ids + '"的数据项？').then(function() {
    return delCheckitem(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

getList()
</script>
