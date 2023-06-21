import { useTitle } from '@hooks/useTitle';

import { Page } from '@interfaces/page';

import { CardList } from '@pages/About/Card';

interface AboutProps extends Page {
}

export function About({ title }: AboutProps) {
  useTitle(title);

  return <CardList />;
}
