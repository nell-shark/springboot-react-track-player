import Button from "react-bootstrap/Button";

interface ShowMoreProps {
  showMore: () => void;
  hide: boolean
}

export function ShowMore({showMore, hide}: ShowMoreProps) {
  return (
    <>
      {!hide && <Button variant="outline-dark" onClick={showMore} className="show-more" >
          Show more
      </Button>}
    </>
  );
}
