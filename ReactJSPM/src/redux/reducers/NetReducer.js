/**
 * Created by chris on 2017/8/14.
 */
import * as actions from '../config';

export default (state = {}, action) => {
  switch (action.type) {
    case actions.NetActions.NET_RESP:
      state = {
        path: action.path,
        response: action.data
      };
      //console.log('reducer.net.state.response = ' + JSON.stringify(state.response));
      break;
  }
  return state;
};
