/**
 * Created by chris on 2017/8/1.
 */
import App from '../components/App';
import Login from '../components/business/login/Login';
import Dashboard from '../components/business/Dashboard';
import ContentSection from '../components/business/content/ContentSection';
import History from '../components/business/content/History';

export const routers = {
  path: '/',
  component: App,
  indexRoute: {
    component: Dashboard
  },
  childRoutes: [
    {
      path: 'login',
      component: Login
    }

    ,{
      path: 'dashboard',
      component: Dashboard,
      childRoutes: [
        {
          path: 'content/:bundle',
          component: ContentSection
        },
        {
          path: '/history/:bundle',
          component: History
        }
      ]
    }
  ]
};
