/**
 * Created by chris on 2017/8/3.
 */
import React, {Component} from 'react';
import {Link} from 'react-router';

class Menus extends Component {

  render() {
    return (
      <div className="menus">
        <div className="menu-wrap">
          <ul>
            <li className="selected">
              <Link to="/dashboard/content/all">全部</Link>
            </li>
            <li>
              <a href="#">萌店</a>
            </li>
            <li>
              <a href="#">萌小店</a>
            </li>
            <li>
              <a href="#">萌小贷</a>
            </li>
            <li>
              <a href="#">贷款王</a>
            </li>
          </ul>
        </div>
      </div>
    );
  }

}

export default Menus;
