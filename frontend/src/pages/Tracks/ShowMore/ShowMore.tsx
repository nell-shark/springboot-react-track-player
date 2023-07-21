import Button from 'react-bootstrap/Button';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { setPage } from '@store/slices';

export function ShowMore() {
  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);

  function fetchNextPage() {
    dispatch(setPage(state.page + 1));
  }

  return (
    <>
      {state.hasNextPage && (
        <Button variant='outline-dark' onClick={fetchNextPage} className='show-more' disabled={state.isLoadingPage}>
          Show more
        </Button>
      )}
    </>
  );
}
