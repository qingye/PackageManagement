/**
 * Created by chris on 2017/8/4.
 */
import React, {Component} from 'react';
import QRCode from 'qrcode.react';
import {Link} from 'react-router';

class AppBox extends Component {

  render() {

    let pt = true; // true - android; false - ios
    let iconClassName = 'iconfont app-icon-right' + (pt ? ' icon-android' : ' icon-ios');
    let iconBgClassName = 'app-icon-bg' + (pt ? ' android' : ' ios');

    return (
      <div className="app-box">
        <i className={iconClassName} />
        <div className={iconBgClassName}></div>
        <div className="app-info-box">

          {/* App Icon */}
          <img className="info-icon" src="../../../images/123.png"/>

          {/* Download URL */}
          <QRCode value='https://www.baidu.com' size={100}/>

          {/* App Name */}
          <div className="info-name">萌店</div>

          {/* Bundle or Package */}
          <div className="info-table-row">
            <div className="info-table-cell">Id</div>
            <div className="info-table-cell">com.hs.yjsellerhoc</div>
          </div>

          {/* Version */}
          <div className="info-table-row">
            <div className="info-table-cell">最新版本</div>
            <div className="info-table-cell">4.8.0</div>
          </div>

          {/* TimeStamp */}
          <div className="info-table-row">
            <div className="info-table-cell">更新时间</div>
            <div className="info-table-cell">2017/7/4 下午5:52:29</div>
          </div>

          <div>
            <a className="info-round-btn" href="#">下载当前版本</a>
            <Link className="info-round-btn" style={{marginLeft: '1rem'}} to="/history/all">查看历史版本</Link>
          </div>
        </div>
      </div>
    );
  }
}

export default AppBox;
