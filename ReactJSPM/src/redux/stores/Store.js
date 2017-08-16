/**
 * Created by chris on 2017/8/14.
 */
import {createStore, applyMiddleware, combineReducers} from 'redux';
import thunk from 'redux-thunk';
import net from '../reducers/NetReducer';

const reducers = combineReducers({
  net
});

const createStoreWithMiddleware = applyMiddleware(
  thunk
)(createStore);

export default function store(initialState) {
  return createStoreWithMiddleware(reducers, initialState);
}
