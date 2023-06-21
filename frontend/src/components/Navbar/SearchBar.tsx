import { FormEvent, useEffect, useRef } from 'react';
import { Form } from 'react-bootstrap';
import { createSearchParams, useLocation, useNavigate, useSearchParams } from 'react-router-dom';

export function SearchBar() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const searchRef = useRef<HTMLInputElement | null>(null);
  const location = useLocation();

  async function search(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const value = searchRef?.current?.value.trim();
    if (!value?.trim().length) return;

    navigate({
      pathname: '/tracks',
      search: createSearchParams({
        filter: searchRef.current!.value
      }).toString()
    });
  }

  useEffect(() => {
    if (location.pathname !== '/tracks') searchRef.current!.value = '';
  }, [location]);

  return (
    <Form className='d-flex' onSubmit={search}>
      <Form.Control
        type='search'
        placeholder='Search'
        className='me-2'
        aria-label='Search'
        ref={searchRef}
        defaultValue={searchParams.get('filter') || ''}
      />
    </Form>
  );
}
