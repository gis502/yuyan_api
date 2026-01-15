import { createRouter, createWebHistory } from 'vue-router'
import TeamManagement from '../views/TeamManagement.vue'
import Home from '../views/Home.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/teams',
    name: 'TeamManagement',
    component: TeamManagement
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router