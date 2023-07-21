import { useEffect } from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import NavbarBs from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom';

import { AuthenticatedUser } from '@components/Navbar/AuthenticatedUser';
import { SearchBar } from '@components/Navbar/SearchBar';
import { UnauthenticatedUser } from '@components/Navbar/UnauthenticatedUser';

import { useAppDispatch, useAppSelector } from '@hooks/redux';

import { userService } from '@services/userService';

import { setUser } from '@store/slices/userSlice';

export function Navbar() {
  const dispatch = useAppDispatch();
  const user = useAppSelector(state => state.user.user);

  async function fetchUser() {
    try {
      const { data } = await userService.getOAuth2User();
      dispatch(setUser(data));
    } catch (error) {}
  }

  useEffect(() => {
    fetchUser();
  }, []);

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
          <Nav>{user ? <AuthenticatedUser /> : <UnauthenticatedUser />}</Nav>
        </NavbarBs.Collapse>
      </Container>
    </NavbarBs>
  );
}
