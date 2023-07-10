import { useEffect } from 'react';
import { Button } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Dropdown from 'react-bootstrap/Dropdown';
import Nav from 'react-bootstrap/Nav';
import NavbarBs from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

import { SearchBar } from '@components/Navbar/SearchBar';

import { BASE_URL } from '@data/constants';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { userService } from '@services/userService';

import { removeUser, setUser } from '@store/slices/userSlice';

import { faGithub } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


export function Navbar() {
  const dispatch = useAppDispatch();
  const userState = useAppSelector(state => state.user);

  async function fetchUserInfo() {
    try {
      const { data } = await userService.getOAuth2UserInfo();
      dispatch(setUser({ login: data.login, avatarUrl: data.avatarUrl }));
    } catch (error) {
      console.error(error);
    }
  }

  async function logout() {
    await userService.logout();
    dispatch(removeUser());
  }

  useEffect(() => {
    fetchUserInfo();
  });

  return (
    <NavbarBs id='navigation' collapseOnSelect expand='lg' bg='dark' variant='dark' className='position-fixed w-100'>
      <Container>
        <NavbarBs.Brand as={Link} to='/'>
          TrackPlayer
        </NavbarBs.Brand>
        <NavbarBs.Toggle aria-controls='responsive-navbar-nav' />
        <NavbarBs.Collapse id='responsive-navbar-nav'>
          <Nav className='me-auto'>
            <Link to='/tracks' className='nav-link'>
              Tracks
            </Link>
            <Link to='/about' className='nav-link'>
              About
            </Link>
          </Nav>
          <Nav>
            <SearchBar />
          </Nav>
          <Nav>
            {!userState.login && (
              <a href={BASE_URL + '/oauth2/authorization/github'} className='text-decoration-none'>
                <Button variant='outline-light' className='d-flex align-items-center'>
                  <FontAwesomeIcon icon={faGithub} className='sign-in' />
                  Sign in
                </Button>
              </a>
            )}
            {userState.login && (
              <Dropdown>
                <Dropdown.Toggle variant='primary' id='dropdown-basic'>
                  <>
                    <img src={userState.avatarUrl} alt='user' className='sign-in avatar rounded' />
                    {userState.login}
                  </>
                </Dropdown.Toggle>
                <Dropdown.Menu>
                  <Dropdown.Item as={Link} to='/favorite'>
                    Favorite
                  </Dropdown.Item>
                  <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            )}
          </Nav>
        </NavbarBs.Collapse>
      </Container>
    </NavbarBs>
  );
}
