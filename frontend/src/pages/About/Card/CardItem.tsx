import { CSSProperties } from 'react';
import { Card } from 'react-bootstrap';

import { IconDefinition } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface CardItemProps {
  icon: IconDefinition;
  color: CSSProperties['color'];
  title: string;
  link: string;
}

export function CardItem({ icon, color, title, link }: CardItemProps) {
  return (
    <a href={link}>
      <Card className='text-center card'>
        <FontAwesomeIcon icon={icon} color={color} className='p-2 h-100' />
        <Card.Body>{title}</Card.Body>
      </Card>
    </a>
  );
}
