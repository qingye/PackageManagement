/**
 * Created by chris on 2017/8/3.
 */
import React, {Component} from 'react';
import AppBox from './AppBox';

class ContentSection extends Component {

  render() {
    return (
      <div className="content-section">
        <div className="content-box">
          <AppBox />
          <AppBox />
        </div>
      </div>
    );
  }
}

export default ContentSection;
