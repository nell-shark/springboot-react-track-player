import {
  createSearchParams,
  useNavigate,
  useSearchParams,
} from 'react-router-dom';

import { Form } from 'react-bootstrap';
import { useRef } from 'react';

export function SearchBar() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const searchRef = useRef<HTMLInputElement | null>(null);

  async function search(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    const value = searchRef?.current?.value.trim();
    if (!value?.trim().length) return;

    navigate({
      pathname: '/tracks',
      search: createSearchParams({
        search: searchRef.current!.value,
      }).toString(),
    });
  }

  return (
    <Form className="d-flex" onSubmit={search}>
      <Form.Control
        type="search"
        placeholder="Search"
        className="me-2"
        aria-label="Search"
        ref={searchRef}
        defaultValue={searchParams.get('search') || ''}
      />
    </Form>
  );
}
