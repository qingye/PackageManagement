/**
 * Created by chris on 2017/8/1.
 */
import React, {Component} from 'react';

class Header extends Component {

  render() {
    return (
      <div className="header">
        <div className="image-logo"></div>
        <div className="group">
          User / Login
        </div>
        <div className="title">APP包管理</div>
      </div>
    );
  }
}

export default Header;
