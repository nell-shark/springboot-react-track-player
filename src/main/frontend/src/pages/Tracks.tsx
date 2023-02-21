import { Footer } from "@components/Footer";
import { Navigation } from "@components/Navigation";
import { TrackList } from "@components/TrackList";
import { useTitle } from "@hooks/useTitle";
import { Page } from "@interfaces/page";

export interface TracksProps extends Page {}

export function Tracks({ title }: TracksProps) {
  useTitle(title);

  return (
    <>
      <Navigation />
      <TrackList />
      <Footer />
    </>
  );
}
