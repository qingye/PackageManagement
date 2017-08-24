/**
 * Created by chris on 2017/8/3.
 */
import React, {Component} from 'react';
import {hashHistory} from 'react-router';
import Menu from './Menu';

class MenuList extends Component {

  constructor(props) {
    super(props);
    this.state = {
      menuList: [],
      selected: this.props.selected
    }
  }

  select(index) {
    return () => this.state.selected = index;
  }

  refreshMenus() {
    let path = hashHistory.getCurrentLocation().pathname;
    path = path.substring(path.lastIndexOf('/') + 1);

    let menuList = [];
    let menus = this.props.menuList;
    if (menus && menus.length > 0) {
      menus.forEach((menu, index) => {
        if (path === menu.appIdentifier) {
          this.state.selected = index;
        }

        let menuName = menu.appName.replace(/[a-zA-Z0-9_]*/g, '');
        menuList.push(
          <Menu key={index}
                name={menuName}
                appIdentifier={menu.appIdentifier}
                selected={this.state.selected === index}
                select={this.select(index)}/>
        );
      });
    }
    this.state.menuList = menuList;
  }

  componentWillReceiveProps(nextProps) {
    if (this.state.selected !== nextProps.selected) {
      this.state.selected = nextProps.selected;
    }
  }

  render() {
    this.refreshMenus();
    return (
      <div className="menu-list">
        <div className="menu-wrap">
          <ul>
            {this.state.menuList}
          </ul>
        </div>
      </div>
    );
  }
}

export default MenuList;
