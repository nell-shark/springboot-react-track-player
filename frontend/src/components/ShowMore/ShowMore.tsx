import Button from "react-bootstrap/Button";

interface ShowMoreProps {
  isFetching: boolean;
  fetchNextPage: () => void;
  hasNextPage?: boolean
}

export function ShowMore({isFetching, fetchNextPage, hasNextPage}: ShowMoreProps) {
  return (
    <>
      {hasNextPage &&
          <Button variant="outline-dark" onClick={fetchNextPage} className="show-more" disabled={isFetching}>
              Show more
          </Button>
      }
    </>
  );
}
