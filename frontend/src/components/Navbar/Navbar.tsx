import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import Nav from 'react-bootstrap/Nav';
import NavbarBs from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

import { SearchBar } from '@components/Navbar/SearchBar';

import { BASE_URL } from '@data/constants';

import { axiosInstance } from '@services/axios-instance';
import { userService } from '@services/UserService';

import { faGithub } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export function Navbar() {
  const [login, setLogin] = useState<string>('');
  const [avatarUrl, setAvatarUrl] = useState<string>('');

  async function fetchUserInfo() {
    const { data } = await userService.getUserInfo();
    setLogin(() => data.login);
    setAvatarUrl(() => data.avatarUrl);
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
          MusicPlayer
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
              <Nav.Link href={BASE_URL + '/oauth2/authorization/github'}>
                <Button variant='outline-light' className='d-flex align-items-center'>
                  <FontAwesomeIcon icon={faGithub} className='avatar' />
                  Sign in
                </Button>
              </Nav.Link>
            )}
            {login && (
              <DropdownButton
                as={ButtonGroup}
                title={
                  <>
                    <img src={avatarUrl} alt='user' className='avatar rounded' />
                    {login}
                  </>
                }
              >
                <Dropdown.Item as={Link} to='/favorite'>
                  Favorite
                </Dropdown.Item>
                <Dropdown.Item onClick={logout}>Logout</Dropdown.Item>
              </DropdownButton>
            )}
          </Nav>
        </NavbarBs.Collapse>
      </Container>
    </NavbarBs>
  );
}
