/**
 * Created by chris on 2017/8/11.
 */
require('es6-promise').polyfill();
require('isomorphic-fetch');

import BaseUrl from './config/NetConfig';

class NetFetch {

  static doPost(path, data) {
    return this.doRequest('POST', path, data);
  }

  static doGet(path) {
    return this.doRequest('GET', path, '');
  }

  static doRequest(method, path, data) {
    let url = BaseUrl() + path;
    return fetch(url, {
      method: method,
      headers: {
        'Content-Type': 'application/json; charset=utf-8'
      },
      body: JSON.stringify(data)
    }).then(response => {
      //console.log(response);
      if (response.ok) {
        return this.handleContentByType(response);
      }
      throw new Error('服务累了,让它休息会儿~');
    }).then(json => {
      return eval(json);
    }).catch(e => {
      return {
        code: 999999,
        msg: e
      }
    });
  }

  static handleContentByType(response) {
    let contentType = response.headers.get('Content-Type');
    let type = contentType ? contentType.split(';')[0] : 'application/json';
    switch (type) {
      case 'text/html':
        return response.text();
      case 'application/json':
        return response.json();
    }
  }
}

export default NetFetch;
