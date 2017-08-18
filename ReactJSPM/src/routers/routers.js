/**
 * Created by chris on 2017/8/1.
 */
import App from '../components/App';
import Login from '../components/business/login/Login';
import Dashboard from '../components/business/Dashboard';
import Content from '../components/business/content/Content';
import History from '../components/business/content/History';
import Page from '../components/business/content/Page';

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
          path: 'content(/:appIdentifier)',
          component: Content
        },
        {
          path: '/history(/:bundleId)',
          component: History
        }
      ]
    }

    ,{
      path: 'page(/:info)',
      component: Page
    }
  ]
};
