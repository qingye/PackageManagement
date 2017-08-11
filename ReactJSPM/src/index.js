import 'core-js/fn/object/assign';
import React from 'react';
import ReactDOM from 'react-dom';

import {Router, hashHistory} from 'react-router';
import {routers} from './routers/routers';

// Render the main component into the dom
ReactDOM.render(
  <Router routes = {routers} history = {hashHistory}/>,
  document.getElementById('app')
);
