import axios from 'axios';

const API_BASE_URL = 'http://localhost:7001';

// 创建axios实例
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

// 获取所有团队
export const getAllTeams = () => {
  return apiClient.get('/team/all');
};

// 根据ID获取团队
export const getTeamById = (id) => {
  return apiClient.get(`/team/${id}`);
};

// 添加团队
export const addTeam = (teamData) => {
  return apiClient.post('/team', teamData);
};

// 更新团队
export const updateTeam = (teamData) => {
  return apiClient.put('/team', teamData);
};

// 删除团队
export const deleteTeam = (id) => {
  return apiClient.delete(`/team/${id}`);
};

// 根据关键词搜索团队
export const searchTeams = (keyword) => {
  return apiClient.get(`/team/search?keyword=${encodeURIComponent(keyword)}`);
};