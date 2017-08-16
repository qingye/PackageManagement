/**
 * Created by chris on 2017/8/4.
 */
import React, {Component} from 'react';
import {connect} from 'react-redux';
import {mapDispatchToProps} from '../../../redux/actions';
import Protocols from '../../../network/config/Protocols';

import AppBox from './AppBox';

@connect(state => state, mapDispatchToProps)
class History extends Component {

  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
    this.state = {
      appIdentifier: null,
      bundleId: null,
      selectedVersion: null,
      versions: [],
      apps: []
    }
  }

  getVersions() {
    this.props.network.post(Protocols.GET_VERSIONS, {
      appIdentifier: this.state.appIdentifier,
      bundleId: this.state.bundleId
    });
  }

  getAppList() {
    this.props.network.post(Protocols.GET_APPS, {
      appIdentifier: this.state.appIdentifier,
      bundleId: this.state.bundleId,
      version: this.state.selectedVersion
    });
  }

  handleChange(e) {
    let version = e.target.value;
    if (version === '全部') {
      version = null;
    }
    this.state.selectedVersion = version;
    this.getAppList();
    e.stopPropagation();
    e.preventDefault();
  }

  componentDidMount() {
    let bundleId = this.props.params.bundleId;
    this.state.bundleId = bundleId;

    let appIdentifier = bundleId.replace('hoc', '').replace('dev', '').replace('pl', '');
    if (appIdentifier.endsWith('.')) {
      appIdentifier = appIdentifier.substring(0, appIdentifier.length - 1);
    }
    this.state.appIdentifier = appIdentifier;

    this.getVersions();
    this.getAppList();
  }

  doResponse() {
    if (this.props.net !== undefined && this.props.net.response !== undefined) {
      let data = [];
      if (this.props.net.path === Protocols.GET_VERSIONS) {
        data.push('全部');
        let versions = this.props.net.response;
        versions.forEach(value => {
          data.push(value.version);
        });
        this.state.versions = data;
      } else if (this.props.net.path === Protocols.GET_APPS) {
        let apps = this.props.net.response;
        if (apps && apps.length > 0) {
          apps.forEach((app, index) => {
            data.push(<AppBox key={'app' + index} app={app} hide={true}/>);
          });
          this.state.apps = data;
        }
      }
    }
  }

  render() {
    this.doResponse();

    let version = '全部';
    if (this.state.selectedVersion) {
      version = this.state.selectedVersion;
    }

    let versions = [];
    if (this.state.versions) {
      this.state.versions.forEach((value, index) => {
        versions.push(<option key={'' + index}>{value}</option>)
      });
    }

    return (
      <div className="content">
        <select value={version} onChange={this.handleChange}>{versions}</select>
        <div className="content-box" style={{marginTop: '1rem'}}>
          {this.state.apps}
        </div>
      </div>
    );
  }
}

export default History;
