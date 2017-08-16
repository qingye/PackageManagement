/**
 * Created by chris on 2017/8/3.
 */
import React, {Component} from 'react';
import {connect} from 'react-redux';
import {mapDispatchToProps} from '../../../redux/actions';
import Protocols from '../../../network/config/Protocols';

import Upload from '../../comps/upload/Upload';
import AppBox from './AppBox';

const TIME_SLOT = 30 * 60 * 1000;  // auto refresh per 30 minutes
const ContentPadding = 10;
const AppBoxMargin = 16;
const AppBoxWidth = 352; // at least, real width(20rem) plus marginLR(16+16)

@connect(state => state, mapDispatchToProps)
class ContentSection extends Component {

  constructor(props) {
    super(props);

    this.handleResize = this.handleResize.bind(this);
    this.state = {
      apps: [],
      appIdentifier: '',
      contentBoxWidth: 0
    }
  }

  doRequest() {
    this.props.network.post(Protocols.GET_NEWEST_VERSION_BY_IDENTIFIER, {
      appIdentifier: this.state.appIdentifier
    });
  }

  doResponse() {
    if (this.state.appIdentifier === 'all') {
      this.state.apps = [];
    } else if (this.props.net !== undefined && this.props.net.response !== undefined && this.props.net.path === Protocols.GET_NEWEST_VERSION_BY_IDENTIFIER) {
      let apps = this.props.net.response;
      if (apps && apps.length > 0) {
        let data = [];
        apps.forEach((app, index) => {
          data.push(<AppBox key={'app' + index} app={app} hide={false} marginLeft={this.getMargin()}/>);
        });
        this.state.apps = data;
      }
    }
  }

  doTask() {
    if (this.state.appIdentifier !== 'all') {
      this.doRequest();
      setTimeout(()  => {
        this.doTask();
      }, TIME_SLOT);
    }
  }

  getMargin() {
    let margin = 0;
    if (this.state.contentBoxWidth > 0) {
      let column = Math.floor(this.state.contentBoxWidth / AppBoxWidth);
      margin = (this.state.contentBoxWidth - AppBoxWidth * column) / (column + 1) + AppBoxMargin;
    }
    return margin;
  }

  handleResize(e) {
    this.resizeClient();
    e.stopPropagation();
    e.preventDefault();
  }

  resizeClient() {
    let dom = document.getElementsByClassName('content-box')[0];
    if (dom && dom !== undefined) {
      this.setState({
        contentBoxWidth: dom.clientWidth - 2 * ContentPadding
      });
    }
  }

  componentDidMount() {
    this.resizeClient();
    window.addEventListener('resize', this.handleResize);
    this.state.appIdentifier = this.props.params.appIdentifier;
    this.doTask();
  }

  componentWillUnmount() {
    window.removeEventListener('resize', this.handleResize);
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.params.appIdentifier !== nextProps.params.appIdentifier) {
      this.state.appIdentifier = nextProps.params.appIdentifier;
      this.doTask();
    }
  }

  render() {
    this.doResponse();

    let contentBox = 'content-box' + (this.state.appIdentifier === 'all' ? ' hide' : '');
    let divHide = this.state.appIdentifier === 'all' ? '' : 'hide';
    return (
      <div className="content">
        <div className={contentBox}>
          {this.state.apps}
        </div>
        <div className={divHide}>
          <Upload />
        </div>
      </div>
    );
  }
}

export default ContentSection;
