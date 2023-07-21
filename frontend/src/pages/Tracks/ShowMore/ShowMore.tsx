import Button from 'react-bootstrap/Button';
import { useSearchParams } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { getTracListPage } from '@store/slices';

export function ShowMore() {
  const dispatch = useAppDispatch();
  const state = useAppSelector(state => state.trackPlayer);
  const [searchParams] = useSearchParams();

  function fetchNextPage() {
    dispatch(getTracListPage({ page: state.page + 1, filter: searchParams.get('filter') || undefined }));
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
