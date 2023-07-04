import { Page } from '@typings/page';

import { useTitle } from '@hooks/useTitle';

import { CardList } from '@pages/About/Card';

interface AboutProps extends Page {}

export function About({ title }: AboutProps) {
  useTitle(title);

  return <CardList />;
}