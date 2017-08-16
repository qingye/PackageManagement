/**
 * Created by chris on 2017/8/4.
 */
import React, {Component} from 'react';
import QRCode from 'qrcode.react';
import {Link} from 'react-router';

class AppBox extends Component {

  render() {

    let platform = this.props.app.type.toLowerCase();
    let iconClassName = 'iconfont app-icon-right icon-' + platform;
    let iconBgClassName = 'app-icon-bg ' + platform;

    let qrcode = this.props.app.fileUrl ? this.props.app.fileUrl : '';
    let historyBtnStyle = 'info-round-btn' + (this.props.hide ? ' hide' : '');
    let history = '/history/' + this.props.app.bundleId;

    return (
      <div className="app-box" style={{marginLeft: this.props.marginLeft}}>
        <i className={iconClassName} />
        <div className={iconBgClassName}></div>
        <div className="app-info-box">

          {/* App Icon */}
          <img className="info-icon" src={this.props.app.iconUrl}/>

          {/* Download URL */}
          <QRCode value={qrcode} size={100} level='L'/>

          {/* App Name */}
          <div className="info-name">{this.props.app.appName}</div>

          {/* Bundle or Package */}
          <div className="info-table-row">
            <div className="info-table-cell">Id</div>
            <div className="info-table-cell">{this.props.app.bundleId}</div>
          </div>

          {/* Version */}
          <div className="info-table-row">
            <div className="info-table-cell">最新版本</div>
            <div className="info-table-cell">{this.props.app.version}</div>
          </div>

          {/* TimeStamp */}
          <div className="info-table-row">
            <div className="info-table-cell">更新时间</div>
            <div className="info-table-cell">{new Date(this.props.app.createTimeStamp).toLocaleString()}</div>
          </div>

          <div>
            <a className="info-round-btn" href={this.props.app.fileUrl}>下载当前版本</a>
            <Link className={historyBtnStyle} style={{marginLeft: '1rem'}} to={history}>查看历史版本</Link>
          </div>
        </div>
      </div>
    );
  }
}

export default AppBox;
