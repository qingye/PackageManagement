/**
 * Created by chris on 2017/8/15.
 */
import React, {Component} from 'react';
import {hashHistory} from 'react-router';
import BaseUrl from '../../../network/config/NetConfig';
import Protocols from '../../../network/config/Protocols';

let timerHandler = null;

class Upload extends Component {

  constructor(props) {
    super(props);

    this.handleDragOver = this.handleDragOver.bind(this);
    this.handleDrop = this.handleDrop.bind(this);
    this.handleDragEnd = this.handleDragEnd.bind(this);
    this.state = {
      dragStatus: false,
      display: 'none',
      timeSlot: 0
    };
  }

  startTimer() {
    this.setState({
      display: 'block',
      timeSlot: 6
    });

    timerHandler = window.setInterval(() => {
      0 == this.state.timeSlot ? this.stopTimer() : this.setState({timeSlot: this.state.timeSlot - 1})
    }, 1000);
  }

  stopTimer() {
    if (timerHandler) {
      window.clearInterval(timerHandler);
      timerHandler = null;
    }
    this.setState({
      display: 'none'
    });
    location.reload(true);
    hashHistory.push('/');
  }

  uploadFiles(file) {
    let formData = new FormData();
    formData.append('uploadFile', file);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', BaseUrl() + Protocols.UPLOAD_FILE, true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    xhr.upload.onprogress = e => {
      let percent = 0;
      if (e.lengthComputable) {
        percent = parseInt(100 * e.loaded / e.total);
      }
      this.refs.progress.innerHTML = percent + '%';
    };

    xhr.upload.onload = e => {
      this.refs.progress.innerHTML = '';
      this.startTimer();
    };
    xhr.send(formData);
  }

  dragStatusChange(status) {
    this.setState({
      dragStatus: status
    });
  }

  handleDragOver(e) {
    this.dragStatusChange(true);
    e.stopPropagation();
    e.preventDefault();
  }

  handleDrop(e) {
    this.uploadFiles(e.dataTransfer.files[0]);
    this.dragStatusChange(false);
    e.stopPropagation();
    e.preventDefault();
  }

  handleDragEnd(e) {
    this.dragStatusChange(false);
    e.stopPropagation();
    e.preventDefault();
  }

  render() {
    let opacity = 'uploadDiv' + (this.state.dragStatus ? ' opacity' : '');
    return (
      <div className="uploadBox">
        <div className={opacity} onDrop={this.handleDrop} onDragOver={this.handleDragOver} onDragLeave={this.handleDragEnd}>
          <div className="box">
            拖拽上传文件&nbsp;<span ref="progress">&nbsp;</span>
          </div>
          <div className="uploadSuccess" style={{display: this.state.display}}>
            上传成功, <span ref="countDown">{this.state.timeSlot}</span>秒后刷新!
          </div>
        </div>
      </div>
    );
  }
}

export default Upload;
