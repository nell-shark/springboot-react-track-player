import { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Dropdown from 'react-bootstrap/Dropdown';
import Nav from 'react-bootstrap/Nav';
import NavbarBs from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

import { SearchBar } from '@components/Navbar/SearchBar';

import { BASE_URL } from '@data/constants';

import { axiosInstance } from '@services/axiosInstance';
import { userService } from '@services/userService';

import { faGithub } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export function Navbar() {
  const [login, setLogin] = useState<string>('');
  const [avatarUrl, setAvatarUrl] = useState<string>('');

  async function fetchUserInfo() {
    const { data } = await userService.getUserInfo();
    setLogin(() => data.login || '');
    setAvatarUrl(() => data.avatarUrl || '');
  }

  async function logout() {
    await axiosInstance.post('/logout');
    setLogin(() => '');
    setAvatarUrl(() => '');
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
            {!login && (
              <a href={BASE_URL + '/oauth2/authorization/github'} className='text-decoration-none'>
                <Button variant='outline-light' className='d-flex align-items-center'>
                  <FontAwesomeIcon icon={faGithub} className='sign-in' />
                  Sign in
                </Button>
              </a>
            )}
            {login && (
              <Dropdown>
                <Dropdown.Toggle variant='primary' id='dropdown-basic'>
                  <>
                    <img src={avatarUrl} alt='user' className='sign-in avatar rounded' />
                    {login}
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