import {Button, Container} from "react-bootstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import React from "react";
import {faBackwardStep, faForwardStep, faPause, faPlay} from "@fortawesome/free-solid-svg-icons";

interface AudioPlayerProps {
    isActive: boolean
    isPlaying: boolean
}

export function AudioPlayer({isActive, isPlaying}: AudioPlayerProps) {
    return (
        <div className="fixed-bottom w-100 bg-light border-top">
            <Container className="d-flex align-items-center">
                <Button variant="outline-link" className="border-0" active={isActive}>
                    <FontAwesomeIcon icon={faBackwardStep}/>
                </Button>
                <Button variant="outline-link" className="border-0" active={isActive}>
                    {isPlaying
                        ? <FontAwesomeIcon icon={faPause} size="2x"/>
                        : <FontAwesomeIcon icon={faPlay} size="2x"/>}
                </Button>
                <Button variant="outline-link" className="border-0" active={isActive}>
                    <FontAwesomeIcon icon={faForwardStep}/>
                </Button>
            </Container>
        </div>
    );
}
