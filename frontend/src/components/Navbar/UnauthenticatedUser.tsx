import { Button } from 'react-bootstrap';

import { BASE_URL } from '@data/constants';

import { faGithub } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export function UnauthenticatedUser() {
  return (
    <a href={BASE_URL + '/oauth2/authorization/github'} className='text-decoration-none'>
      <Button variant='outline-light' className='d-flex align-items-center'>
        <FontAwesomeIcon icon={faGithub} className='sign-in' />
        Sign in
      </Button>
    </a>
  );
}
