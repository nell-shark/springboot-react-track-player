import {CardList} from "@pages/About/Card";
import {Page} from '@interfaces/page';
import {useTitle} from '@hooks/useTitle';

interface AboutProps extends Page {
}

export function About({title}: AboutProps) {
    useTitle(title);

    return <CardList/>;
}
