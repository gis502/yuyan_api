<template>
  <div class="team-management">
    <el-page-header title="返回" @back="$router.back()">
      <template #content>
        <span class="page-title">团队管理</span>
      </template>
    </el-page-header>

    <el-card class="search-card">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-input
            v-model="searchKeyword"
            placeholder="请输入团队名称或地区进行搜索"
            clearable
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button @click="handleSearch">
                <el-icon><Search /></el-icon>
              </el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="12" class="text-right">
          <el-button 
            type="danger" 
            :disabled="multipleSelection.length === 0"
            @click="handleBatchDelete"
          >
            <el-icon><Delete /></el-icon>
            批量删除
          </el-button>
          <el-button type="primary" @click="showAddDialog">
            <el-icon><Plus /></el-icon>
            新增团队
          </el-button>
        </el-col>
      </el-row>
    </el-card>

    <el-card>
      <el-table
        :data="teams"
        stripe
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="团队名称" width="200" />
        <el-table-column prop="region" label="地区" width="120" />
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="showEditDialog(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination-container">
        <el-pagination
          v-if="total > 0"
          layout="prev, pager, next"
          :total="total"
          :current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑团队对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      :before-close="handleCloseDialog"
    >
      <el-form
        :model="teamForm"
        :rules="formRules"
        ref="teamFormRef"
        label-width="80px"
      >
        <el-form-item label="团队名称" prop="name">
          <el-input
            v-model="teamForm.name"
            placeholder="请输入团队名称"
          />
        </el-form-item>
        <el-form-item label="地区" prop="region">
          <el-input
            v-model="teamForm.region"
            placeholder="请输入地区"
          />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="teamForm.description"
            type="textarea"
            placeholder="请输入团队描述"
            :rows="3"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleCloseDialog">取消</el-button>
          <el-button type="primary" @click="handleSubmitForm">确认</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { 
  getAllTeams, 
  addTeam, 
  updateTeam, 
  deleteTeam, 
  searchTeams 
} from '@/api/teamApi';
import { 
  Search, 
  Plus,
  Delete
} from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';

// 响应式数据
const teams = ref([]);
const loading = ref(false);
const searchKeyword = ref('');
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const multipleSelection = ref([]); // 存储多选的项目

// 对话框相关
const dialogVisible = ref(false);
const dialogTitle = ref('');
const isEdit = ref(false);
const teamForm = reactive({
  id: null,
  name: '',
  region: '',
  description: ''
});

// 表单引用
const teamFormRef = ref();

// 表单验证规则
const formRules = {
  name: [
    { required: true, message: '请输入团队名称', trigger: 'blur' },
    { min: 2, max: 100, message: '长度在2到100个字符', trigger: 'blur' }
  ],
  region: [
    { required: true, message: '请输入地区', trigger: 'blur' },
    { min: 1, max: 20, message: '地区名称长度应在1到20个字符之间', trigger: 'blur' }
  ]
};

// 获取团队列表
const fetchTeams = async () => {
  loading.value = true;
  try {
    const response = await getAllTeams();
    teams.value = response.data;
    total.value = response.data.length; // 简单处理总数
  } catch (error) {
    console.error('获取团队列表失败:', error);
    ElMessage.error('获取团队列表失败');
  } finally {
    loading.value = false;
  }
};

// 搜索团队
const handleSearch = async () => {
  if (!searchKeyword.value.trim()) {
    fetchTeams();
    return;
  }
  
  loading.value = true;
  try {
    const response = await searchTeams(searchKeyword.value);
    teams.value = response.data;
    total.value = response.data.length;
  } catch (error) {
    console.error('搜索团队失败:', error);
    ElMessage.error('搜索团队失败');
  } finally {
    loading.value = false;
  }
};

// 显示新增对话框
const showAddDialog = () => {
  resetForm();
  dialogTitle.value = '新增团队';
  isEdit.value = false;
  dialogVisible.value = true;
};

// 显示编辑对话框
const showEditDialog = (row) => {
  Object.assign(teamForm, row);
  dialogTitle.value = '编辑团队';
  isEdit.value = true;
  dialogVisible.value = true;
};

// 提交表单
const handleSubmitForm = async () => {
  if (!teamFormRef.value) return;
  
  try {
    await teamFormRef.value.validate();
    
    let response;
    if (isEdit.value) {
      // 更新团队
      response = await updateTeam(teamForm);
    } else {
      // 添加团队
      response = await addTeam(teamForm);
    }
    
    if (response.data > 0) {
      ElMessage.success(isEdit.value ? '更新成功' : '添加成功');
      handleCloseDialog();
      fetchTeams(); // 刷新列表
    } else {
      ElMessage.error(isEdit.value ? '更新失败' : '添加失败');
    }
  } catch (error) {
    console.error(isEdit.value ? '更新团队失败:' : '添加团队失败:', error);
    ElMessage.error(isEdit.value ? '更新失败' : '添加失败');
  }
};

// 删除团队
const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm(
      '此操作将永久删除该团队, 是否继续?',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    );
    
    const response = await deleteTeam(id);
    if (response.data > 0) {
      ElMessage.success('删除成功');
      fetchTeams(); // 刷新列表
    } else {
      ElMessage.error('删除失败');
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除团队失败:', error);
      ElMessage.error('删除失败');
    }
  }
};

// 批量删除
const handleBatchDelete = async () => {
  if (multipleSelection.value.length === 0) {
    ElMessage.warning('请至少选择一项');
    return;
  }

  try {
    await ElMessageBox.confirm(
      `此操作将永久删除 ${multipleSelection.value.length} 个团队, 是否继续?`,
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    );

    const ids = multipleSelection.value.map(item => item.id);
    let successCount = 0;
    
    for (const id of ids) {
      try {
        const response = await deleteTeam(id);
        if (response.data > 0) {
          successCount++;
        }
      } catch (error) {
        console.error(`删除团队ID ${id} 失败:`, error);
      }
    }
    
    if (successCount > 0) {
      ElMessage.success(`成功删除 ${successCount} 个团队`);
      fetchTeams(); // 刷新列表
    } else {
      ElMessage.error('删除失败');
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error);
      ElMessage.error('批量删除失败');
    }
  }
};

// 处理多选变化
const handleSelectionChange = (val) => {
  multipleSelection.value = val;
};

// 关闭对话框
const handleCloseDialog = () => {
  dialogVisible.value = false;
  if (teamFormRef.value) {
    teamFormRef.value.clearValidate();
  }
};

// 重置表单
const resetForm = () => {
  teamForm.id = null;
  teamForm.name = '';
  teamForm.region = '';
  teamForm.description = '';
};

// 分页处理
const handlePageChange = (page) => {
  currentPage.value = page;
  // 这里可以添加分页逻辑，当前简化处理
};

// 组件挂载时获取数据
onMounted(() => {
  fetchTeams();
});
</script>

<style scoped>
.team-management {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.search-card {
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.text-right {
  text-align: right;
}

.text-right .el-button {
  margin-left: 10px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.el-card {
  border-radius: 8px;
  border: 1px solid #ebeef5;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.el-table .el-table__cell {
  padding: 12px 0;
}

.el-table th {
  background-color: #f8f9fa;
  color: #606266;
  font-weight: 600;
}
</style>