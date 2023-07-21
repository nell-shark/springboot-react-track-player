import { Dropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { userService } from '@services/userService';

import { removeUser } from '@store/slices/userSlice';


export function AuthenticatedUser() {
  const dispatch = useAppDispatch();
  const user = useAppSelector(state => state.user.user);

  async function logout() {
    await userService.logout();
    dispatch(removeUser());
  }

  return (
    <Dropdown>
      <Dropdown.Toggle variant='primary' id='dropdown-basic'>
        <>
          <img src={user!.avatarUrl} alt='user' className='sign-in avatar rounded' />
          {user!.login}
        </>
      </Dropdown.Toggle>
      <Dropdown.Menu>
        <Dropdown.Item as={Link} to='/favorite/tracks'>
          Favorite Tracks
        </Dropdown.Item>
        <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
}
