import Button from "react-bootstrap/Button";

interface ShowMoreProps {
  isFetching: boolean;
  showMore: () => void;
  hasMore: boolean
}

export function ShowMore({isFetching, showMore, hasMore}: ShowMoreProps) {
  return (
    <>
      {hasMore &&
          <Button variant="outline-dark" onClick={showMore} className="show-more" disabled={isFetching}>
              Show more
          </Button>
      }
    </>
  );
}
