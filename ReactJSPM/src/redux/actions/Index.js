/**
 * Created by chris on 2017/8/15.
 */
import {bindActionCreators} from 'redux';
import * as NetAction from './NetAction';

export const mapDispatchToProps = dispatch => {
  return {
    network: bindActionCreators(NetAction, dispatch)
  };
};
