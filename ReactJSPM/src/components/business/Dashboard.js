/**
 * Created by chris on 2017/8/1.
 */
import React, {Component} from 'react';
import { connect } from 'react-redux';
import Protocols from '../../network/config/Protocols';
import {mapDispatchToProps} from '../../redux/actions';

import Header from '../comps/Header';
import MenuList from '../comps/menu/MenuList';

const ALL = {
  appName: '全部',
  appIdentifier: 'all'
};

@connect(state => state, mapDispatchToProps)
class Dashboard extends Component {

  constructor(props) {
    super(props);
    this.state = {
      menuList: []
    };
    this.props.network.post(Protocols.GET_MENUS);
  }

  doResponse() {
    if (this.props.net !== undefined && this.props.net.response !== undefined && this.props.net.path === Protocols.GET_MENUS) {
      let menuList = [ALL];
      this.props.net.response.forEach(value => {
        menuList.push(value);
      });
      this.state.menuList = menuList;
    }
  }

  render() {
    this.doResponse();
    return (
      <div>
        <Header />
        <section>
          <MenuList menuList={this.state.menuList} selected={-1}/>
          {this.props.children}
        </section>
      </div>
    );
  }
}

export default Dashboard;
