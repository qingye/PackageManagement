/**
 * Created by chris on 2017/8/4.
 */
import React, {Component} from 'react';
import AppBox from './AppBox';

class History extends Component {

  render() {
    return (
      <div className="content-section">
        <select>
          <option>全部</option>
          <option>4.8.0</option>
        </select>
        <div className="content-box" style={{marginTop: '1rem'}}>
          <AppBox />
        </div>
      </div>
    );
  }
}

export default History;
