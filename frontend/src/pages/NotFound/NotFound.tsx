import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';

import { useTitle } from '@hooks/useTitle';

import { Page } from '@typings/page';

interface NotFoundProps extends Page {}

export function NotFound({ title }: NotFoundProps) {
  useTitle(title);

  return (
    <div className='text-center'>
      <h1>Sorry, couldn't find this page</h1>
      <Link to='/tracks'>
        <Button variant='outline-primary'>Go home</Button>
      </Link>
    </div>
  );
}
