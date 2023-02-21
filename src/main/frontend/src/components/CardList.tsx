import { CardItem, CardItemProps } from './CardItem';
import { Container, Stack } from 'react-bootstrap';
import {
  EMAIL_PROFILE_URL,
  GITHUB_PROFILE_URL,
  TELEGRAM_PROFILE_URL,
} from '../data/constants';
import { faGithub, faTelegram } from '@fortawesome/free-brands-svg-icons';

import { faAt } from '@fortawesome/free-solid-svg-icons';

export function CardList() {
  return (
    <main>
      <Container>
        <Stack
          className="flex-wrap justify-content-center"
          direction="horizontal"
          gap={3}
        >
          <CardItem
            icon={faAt}
            color="darkgoldenrod"
            title="Email"
            link={EMAIL_PROFILE_URL}
          />
          <CardItem
            icon={faTelegram}
            color="blue"
            title="Telegram"
            link={TELEGRAM_PROFILE_URL}
          />
          <CardItem
            icon={faGithub}
            color="#000000"
            title="Github"
            link={GITHUB_PROFILE_URL}
          />
        </Stack>
      </Container>
    </main>
  );
}
