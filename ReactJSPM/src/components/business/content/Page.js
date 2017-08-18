/**
 * Created by chris on 2017/8/17.
 */
import React, {Component} from 'react';
import QRCode from 'qrcode.react';
import {connect} from 'react-redux';
import {mapDispatchToProps} from '../../../redux/actions';
import Protocols from '../../../network/config/Protocols';
import Header from '../../comps/Header';

@connect(state => state, mapDispatchToProps)
class Page extends Component {

  constructor(props) {
    super(props);
    this.state = {
      info: null,
      app: null
    };
  }

  componentDidMount() {
    this.state.info = eval('(' + window.atob(this.props.params.info) + ')');
    this.getAppList();
  }

  getAppList() {
    this.props.network.post(Protocols.GET_APPS, {
      appIdentifier: this.state.info.appIdentifier,
      bundleId: this.state.info.bundleId,
      version: this.state.info.version
    });
  }

  doResponse() {
    if (this.props.net !== undefined && this.props.net.response !== undefined && this.props.net.path === Protocols.GET_APPS) {
      let apps = this.props.net.response;
      if (apps && apps.length > 0) {
        this.state.app = apps[0];
      }
    }
  }

  render() {
    this.doResponse();

    let appName = '';
    let iconUrl = '';
    let qrcode = '';
    let fileUrl = '';
    let iconClassName = '';

    if (this.state.app) {
      let platform = this.state.app.type.toLowerCase();
      appName = this.state.app.appName;
      iconUrl = this.state.app.iconUrl;
      qrcode = this.state.app.htmlUrl === '' ? this.state.app.fileUrl : this.state.app.htmlUrl;
      fileUrl = this.state.app.fileUrl;
      iconClassName = 'iconfont icon-' + platform + ' icon-font-size';
    }

    return (
      <div className="page">
        <Header logoVisible={false}/>
        <img className="info-icon-big" src={iconUrl}/>
        <div>
          <QRCode value={qrcode} size={200} level='L'/>
        </div>
        <br />
        <div>
          <i className={iconClassName}/>
          <span className="info-name">{appName}</span>
        </div>
        <div>
          <a className="info-round-btn info-round-btn-padding" href={fileUrl}>下载安装</a>
        </div>
      </div>
    );
  }
}

export default Page;
