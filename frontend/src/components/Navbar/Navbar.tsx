import {BASE_URL} from '@data/constants';
import {Button} from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import {Link} from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';
import NavbarBootstrap from 'react-bootstrap/Navbar';
import {SearchBar} from '@components/Navbar/SearchBar';
import googleLogo from '@assets/google.svg';

export function Navbar() {
  return (
    <NavbarBootstrap
      id="navigation"
      collapseOnSelect
      expand="lg"
      bg="dark"
      variant="dark"
      className="position-fixed w-100"
    >
      <Container>
        <NavbarBootstrap.Brand>NellShark</NavbarBootstrap.Brand>
        <NavbarBootstrap.Toggle aria-controls="responsive-navbar-nav" />
        <NavbarBootstrap.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Link to="/tracks" className="nav-link">
              Tracks
            </Link>
            <Link to="/about" className="nav-link">
              About
            </Link>
          </Nav>
          <Nav>
            <SearchBar />
          </Nav>
          <Nav>
            <Nav.Link href={BASE_URL + '/oauth2/authorization/google'}>
              <Button variant="outline-light">
                <img src={googleLogo} alt="google.svg" className="sign-in" />
                Sign in
              </Button>
            </Nav.Link>
          </Nav>
        </NavbarBootstrap.Collapse>
      </Container>
    </NavbarBootstrap>
  );
}
