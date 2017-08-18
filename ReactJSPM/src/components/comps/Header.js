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

    let logoClassName = 'image-logo';
    if (this.props.logoVisible !== undefined && this.props.logoVisible == false) {
      logoClassName += ' invisible';
    }

    return (
      <div className="header">
        <div className={logoClassName} onClick={this.handleClick}></div>
        <div className="group">
          User / Login
        </div>
        <div className="title">APP包管理</div>
      </div>
    );
  }
}

export default Header;
