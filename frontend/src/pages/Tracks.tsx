import {Page} from '@interfaces/page';
import {TrackList} from '@components/TrackList';
import {useTitle} from '@hooks/useTitle';
import {AddTrack} from "@components/AddTrack";
import {ShowMore} from "@components/ShowMore";
import {useTracks} from "@hooks/useTracks";

export interface TracksProps extends Page {
}

export function Tracks({title}: TracksProps) {
  const {tracks, isFetching, error, showMore, hasMore} = useTracks();
  useTitle(title);

  return (
    <>
      <TrackList isFetching={isFetching} tracks={tracks} error={error?.message}/>
      <div className="mt-4 d-flex justify-content-center gap-3">
        <ShowMore isFetching={isFetching} showMore={showMore} hasMore={hasMore}/>
        <AddTrack/>
      </div>
    </>
  );
}
