import { Page } from "@interfaces/page";
import { TrackList } from "@components/TrackList";
import { useTitle } from "@hooks/useTitle";

export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  useTitle(title);

  return <TrackList />;
}
