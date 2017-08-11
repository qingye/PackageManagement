require('normalize.css/normalize.css');
require('styles/App.scss');
require('styles/iconfont/iconfont.css');

import React from 'react';

class App extends React.Component {
  render() {
    return (
      <div>
        {this.props.children}
      </div>
    );
  }
}

export default App;
