/**
 * Created by chris on 2017/8/1.
 */
import React, {Component} from 'react';

import Header from '../comps/Header';
import Menus from '../comps/Menus';
import ContentSection from './content/ContentSection';

class Dashboard extends Component {

  render() {
    return (
      <div>
        <Header />
        <section>
          <Menus />
          {this.props.children}
        </section>
      </div>
    );
  }
}

export default Dashboard;
