import { Card } from 'react-bootstrap';
import { Color } from '@interfaces/color';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IconDefinition } from '@fortawesome/fontawesome-svg-core';

export interface CardItemProps {
  icon: IconDefinition;
  color: Color;
  title: string;
  link: string;
}

export function CardItem({ icon, color, title , link }: CardItemProps) {
  
  return (
    <a href={link} >
      <Card className="text-center card-item">
        <FontAwesomeIcon
          icon={icon} color={color} className="p-2 h-100"
        />
        <Card.Body>{title}</Card.Body>
      </Card>
    </a>
  );
}
