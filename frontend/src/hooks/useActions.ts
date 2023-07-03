import { bindActionCreators } from 'redux';

import { useDispatch } from 'react-redux';

import ActionCreators from '../store/action-creators';

export function useActions() {
  const dispatch = useDispatch();
  return bindActionCreators(ActionCreators, dispatch);
}