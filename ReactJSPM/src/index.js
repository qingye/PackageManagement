import 'core-js/fn/object/assign';
import React from 'react';
import ReactDOM from 'react-dom';

import {Router, hashHistory} from 'react-router';
import {routers} from './routers/routers';
import {Provider} from 'react-redux';
import store from './redux/stores/Store';

let stores = store();
stores.subscribe(() => {
  /**
   * Can subscribe the stores' state, like:
   * stores.getState()
   */
});

// Render the main component into the dom
ReactDOM.render(
  <Provider store={stores}>
    <Router routes={routers} history={hashHistory}/>
  </Provider>,
  document.getElementById('app')
);
