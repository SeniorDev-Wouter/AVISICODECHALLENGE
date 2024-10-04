import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/HomeView.vue';
import Projects from '../views/ProjectsView.vue';
import Project from '../views/ProjectView.vue';
import Rubrics from '../views/RubricsView.vue';
import Game from '../views/GameView.vue';
import RubricDetail from '../views/RubricView.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/projects', component: Projects },
  { path: '/rubrics', component: Rubrics },
  { path: '/game', component: Game },
  { path: '/rubric/:rubric', component: RubricDetail}, // Add dynamic route
  { path: '/project/:project', component: Project}, // Add dynamic route

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
