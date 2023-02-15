import { Button, Form } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import googleLogo from '@assets/google.svg';
import { GITHUB_PROFILE_URL } from '@data/constants';

export function Navigation() {
  return (
    <Navbar
      id="navigation"
      collapseOnSelect
      expand="lg"
      bg="dark"
      variant="dark"
      className="position-fixed w-100"
    >
      <Container>
        <Navbar.Brand href={GITHUB_PROFILE_URL}>NellShark</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="/">Tracks</Nav.Link>
            <Nav.Link href="/about">About</Nav.Link>
          </Nav>
          <Nav>
            <Form className="d-flex">
              <Form.Control
                type="search"
                placeholder="Search"
                className="me-2"
                aria-label="Search"
              />
              <button type="submit" style={{ display: 'none' }} />
            </Form>
          </Nav>
          <Nav>
            <Nav.Link
              href={
                process.env.REACT_APP_BASE_URL + '/oauth2/authorization/google'
              }
            >
              <Button variant="outline-light">
                <img src={googleLogo} alt="google.svg" className="sign-in" />
                Sign in
              </Button>
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}
