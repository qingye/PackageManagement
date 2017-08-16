/**
 * Created by chris on 2017/8/15.
 */
import React, {Component} from 'react';
import {Link} from 'react-router';

class Menu extends Component {

  constructor(props) {
    super(props);
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(e) {
    this.props.select();
    e.stopPropagation();
    e.preventDefault();
  }

  render() {

    let clzName = this.props.selected ? 'selected' : '';
    let link = '/dashboard/content/' + (this.props.appIdentifier ? this.props.appIdentifier : '');

    return (
      <li className={clzName} onClick={this.handleClick}>
        <Link to={link}>{this.props.name}</Link>
      </li>
    );
  }
}

export default Menu;
