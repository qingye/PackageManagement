/**
 * Created by chris on 2017/8/1.
 */
import React, {Component} from 'react';
import {hashHistory} from 'react-router';

class Header extends Component {

  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(e) {
    hashHistory.push('/');
    e.stopPropagation();
    e.preventDefault();
  }

  render() {
    return (
      <div className="header">
        <div className="image-logo" onClick={this.handleClick}></div>
        <div className="group">
          User / Login
        </div>
        <div className="title">APP包管理</div>
      </div>
    );
  }
}

export default Header;
