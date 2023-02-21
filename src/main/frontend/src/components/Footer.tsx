import {
  EMAIL_PROFILE_URL,
  GITHUB_PROFILE_URL,
  TELEGRAM_PROFILE_URL,
} from '@data/constants';
import { faGithub, faTelegram } from '@fortawesome/free-brands-svg-icons';

import { Container } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAt } from '@fortawesome/free-solid-svg-icons';

export function Footer() {
  return (
    <Container>
      <footer className="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
        <div className="col-md-4 d-flex align-items-center">
          <a
            href={GITHUB_PROFILE_URL}
            className="mb-3 me-2 mb-md-0 text-muted text-decoration-none lh-1"
          >
            <span className="mb-3 mb-md-0 text-muted">
              © Krivolapov Vladislav
            </span>
          </a>
        </div>

        <ul className="nav col-md-4 justify-content-end list-unstyled d-flex">
          <li className="ms-3">
            <a className="text-muted" href={EMAIL_PROFILE_URL}>
              <FontAwesomeIcon icon={faAt} />
            </a>
          </li>
          <li className="ms-3">
            <a className="text-muted" href={TELEGRAM_PROFILE_URL}>
              <FontAwesomeIcon icon={faTelegram} />
            </a>
          </li>
          <li className="ms-3">
            <a className="text-muted" href={GITHUB_PROFILE_URL}>
              <FontAwesomeIcon icon={faGithub} />
            </a>
          </li>
        </ul>
      </footer>
    </Container>
  );
}
