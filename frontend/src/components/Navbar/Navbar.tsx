import {BASE_URL} from '@data/constants';
import {Button} from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import {Link} from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';
import NavbarBs from 'react-bootstrap/Navbar';
import {SearchBar} from '@components/Navbar/SearchBar';
import React, {useEffect, useState} from "react";
import {faGithub} from "@fortawesome/free-brands-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {userService} from "@services/UserService";

export function Navbar() {
    const [login, setLogin] = useState<string>("");
    const [avatarUrl, setAvatarUrl] = useState<string>("");

    useEffect(() => {
        async function fetch() {
            const {data} = await userService.getUserName();
            setLogin(() => data.login);
            setAvatarUrl(() => data.avatarUrl);
        }

        fetch();
    });

    return (
        <NavbarBs
            id="navigation"
            collapseOnSelect
            expand="lg"
            bg="dark"
            variant="dark"
            className="position-fixed w-100"
        >
            <Container>
                <NavbarBs.Brand>NellShark</NavbarBs.Brand>
                <NavbarBs.Toggle aria-controls="responsive-navbar-nav"/>
                <NavbarBs.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        <Link to="/tracks" className="nav-link">
                            Tracks
                        </Link>
                        <Link to="/about" className="nav-link">
                            About
                        </Link>
                    </Nav>
                    <Nav>
                        <SearchBar/>
                    </Nav>
                    <Nav>
                        <Nav.Link href={BASE_URL + '/oauth2/authorization/github'}>
                            <Button variant="outline-light" className="d-flex align-items-center">
                                {!login && <>
                                    <FontAwesomeIcon icon={faGithub} className="sign-in"/>
                                    Sign in</>}
                                {login && <>
                                    <img src={avatarUrl} alt="github" className="sign-in rounded"/>
                                    {login}</>}
                            </Button>
                        </Nav.Link>
                    </Nav>
                </NavbarBs.Collapse>
            </Container>
        </NavbarBs>
    );
}
