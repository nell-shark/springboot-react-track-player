import {Page} from '@interfaces/page';
import {TrackList} from '@pages/Tracks/TrackList';
import {useTitle} from '@hooks/useTitle';
import {AddTrack} from "@pages/Tracks/AddTrack";
import {ShowMore} from "@pages/Tracks/ShowMore";
import {useTracks} from "@hooks/useTracks";

export interface TracksProps extends Page {
}

export function Tracks({title}: TracksProps) {
    const {data, isLoading, isFetching, error, fetchNextPage, hasNextPage} = useTracks();
    useTitle(title);

    return (
    <>
      <TrackList isLoading={isLoading}
                 tracks={data?.pages.flatMap(value => value.tracks) || []}
                 error={error || undefined}/>
      <div className="mt-4 d-flex justify-content-center gap-3">
        <ShowMore isFetching={isFetching} fetchNextPage={fetchNextPage} hasNextPage={hasNextPage}/>
        <AddTrack/>
      </div>
    </>
  );
}