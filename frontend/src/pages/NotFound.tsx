import {Button} from "react-bootstrap";
import {Page} from "@interfaces/page";
import {useTitle} from "@hooks/useTitle";
import {Link} from "react-router-dom";

interface NotFoundProps extends Page {
}

export function NotFound({title}: NotFoundProps) {
  useTitle(title);

  return (
    <div className="text-center">
      <h1>Sorry, couldn't find this page</h1>
      <Link to="/tracks">
        <Button variant="outline-primary">Go home</Button>
      </Link>
    </div>
  );
}
