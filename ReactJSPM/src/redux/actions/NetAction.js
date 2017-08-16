/**
 * Created by chris on 2017/8/14.
 */
import {NetActions} from '../config';
import NetFetch from '../../network/NetFetch';

export const post = (path, data) => {
  return dispatch => {
    NetFetch.doPost(path, data).then(data => {
      dispatch(response(path, data))
    })
  }
};

export const get = (path) => {
  return dispatch => {
    NetFetch.doGet(path).then(data => {
      dispatch(response(path, data))
    })
  }
};

const response = (path, data) => {
  return {
    type: NetActions.NET_RESP,
    path,
    data
  }
};
